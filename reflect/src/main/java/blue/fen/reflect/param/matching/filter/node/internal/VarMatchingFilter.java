package blue.fen.reflect.param.matching.filter.node.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import blue.fen.reflect.param.matching.filter.node.MatchingFilterInternal;
import blue.fen.reflect.param.matching.filter.node.MatchingFilterNode;
import blue.fen.reflect.param.model.IParamProvider;
import blue.fen.reflect.param.matching.priority.MatchingSpec;
import blue.fen.reflect.utils.ReflectArrayUtil;

/**
 * 项目名：NotebookComponent <br/>
 * 包名：blue.fen.reflect.arena.method.filter.base <br/>
 * 创建时间：2023/2/5 06:38 （星期日｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：可变参数过滤器
 */
public class VarMatchingFilter extends MatchingFilterInternal {
    @Override
    public int matchingOutside(IParamProvider provider, int count) {
        if (!provider.isVariableParameter(count)) {
            return MatchingSpec.SKIP;
        }

        Class<?>[] parameters = provider.getParameters();
        Object[] arguments = provider.getArguments();

        assert arguments != null;

        int varParamPosition = parameters.length - 1; //可变参数的位置

        Class<?> varParamClass = provider.getParameter(varParamPosition);

        ReflectArrayUtil.ArrayType pType = ReflectArrayUtil.getComponentType(varParamClass);
        ReflectArrayUtil.ArrayType aType;

        int result = MatchingSpec.SKIP;

        Class<?> argComponentType = null;
        Class<?> paramComponentType = null;

        boolean argumentFullNull = false;

        try {
            if (parameters.length == arguments.length) {
                Class<?> varArgClass = arguments[varParamPosition].getClass();

                aType = ReflectArrayUtil.getComponentType(varArgClass);

                //当为基础数据数组和封装数组的可变参数组合时，需要匹配相同维度的数组
                //当为数组和元素的可变参数组合时，形参的维度必须比实参大1
                if (!aType.sameDimension(pType) && aType.getDimension() + 1 != pType.getDimension()) {
                    result = MatchingSpec.MISMATCH;
                } else {
                    paramComponentType = pType.getComponentType();
                    argComponentType = aType.getComponentType();
                    result = matching(paramComponentType, argComponentType);
                }
            } else if (arguments.length < parameters.length) {
                //这里其实在海选阶段已经淘汰了长度不合适的选手，所以这里必然是arguments.length + 1= parameters.length
                aType = ReflectArrayUtil.getComponentType(arguments.getClass());

                if (aType.sameDimension(pType)) {
                    if (aType.getComponentType() == Object.class) {
                        //Object类型，说明当前实参是混合数据，这时候可以直接放数据
                        result = MatchingSpec.make(getId());
                    } else {
                        //这里需要判断类型是否符合封装匹配或者继承匹配
                        result = matching(pType.getComponentType(), aType.getComponentType());
                    }
                } else {
                    result = MatchingSpec.MISMATCH;
                }
            } else {
                //将实参中可变参数的位置及其后面部分的所有参数组装成可变参数对应的数组
                int minInterval = Integer.MAX_VALUE;
                argumentFullNull = true;

                for (int i = varParamPosition; i < arguments.length; i++) {
                    Class<?> argument = provider.getArgumentType(i);

                    if (argument == null) {
                        continue;
                    }

                    argumentFullNull = false;

                    aType = ReflectArrayUtil.getComponentType(argument);

                    if (aType.getDimension() + 1 != pType.getDimension()) {
                        result = MatchingSpec.MISMATCH;
                        break;
                    } else {
                        result = matching(pType.getComponentType(), aType.getComponentType());

                        if (!MatchingSpec.isMatch(result)) {
                            break;
                        } else if (result < minInterval) {
                            //这里是获取最底层的类型
                            paramComponentType = pType.getComponentType();
                            argComponentType = aType.getComponentType();
                            minInterval = result;
                        }
                    }
                }
            }

            if (paramComponentType != null && (argumentFullNull || (result > 0 && argComponentType != null))) {
                //检查可变参数的元素是否正常
                for (int i = varParamPosition; i < provider.argumentsLength(); i++) {
                    int check = this.matchingMiddle(paramComponentType, argComponentType, provider.getArgument(i));

                    if (!MatchingSpec.isMatch(check)) {
                        return check;
                    }
                }
            }

            //可变实参部分，全部为NULL且形参可变参数类型不为基本类型，可直接匹配
            if (argumentFullNull && MatchingSpec.isSkip(result)) {
                result = MatchingSpec.make(getId());
            }

            return result;
        } catch (Exception ignored) {
            return MatchingSpec.SKIP;
        }
    }

    @Override
    public int matchingMiddle(@NonNull Class<?> parameterClass, @Nullable Class<?> argumentClass, @Nullable Object argument) {
        if (argument == null && parameterClass.isPrimitive()) {
            return MatchingSpec.MISMATCH;
        }

        return MatchingSpec.make(getId());
    }

    @Override
    public int matching(@NonNull Class<?> parameter, Class<?> argument) {
        MatchingFilterNode son = eldestSon();

        if (son == null) {
            return getId();
        }

        do {
            int result = son.matching(parameter, argument);

            if (MatchingSpec.interrupt(result)) {
                return result;
            }
        } while ((son = son.next()) != null);

        return MatchingSpec.SKIP;
    }
}
