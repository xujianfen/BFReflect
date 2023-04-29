package blue.fen.reflect.param.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;

import blue.fen.reflect.utils.ReflectArrayUtil;
import blue.fen.reflect.utils.ReflectClassUtil;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.param.model <br/>
 * 创建时间：2023/4/29 19:30 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：参数供应商
 * <p>
 * 转化到可变参数方法内时，需要清楚下面几种参数的转化   <br/>
 * 不传 -> Obj[0] <br/>
 * null --> null <br/>
 * 1 --> Obj[1]{ Int(1)} <br/>
 * A --> Obj[1]{ A} <br/>
 * Obj -->Obj[1]{ Obj} <br/>
 * null, null --> Obj[2]{null, null} <br/>
 * 1, 1 --> Obj[2]{Int(1),Int(1)} <br/>
 * A, A --> Obj[2]{A,A} <br/>
 * Obj, Obj --> Obj[2]{Obj,Obj} <br/>
 * A[null] --> A[]{null} <br/>
 * Obj[null] --> Obj[]{null} <br/>
 * 1[] --> Obj[] { int[] } <br/>
 * A[] --> A[] {A} <br/>
 * Obj[] -->  Obj[] {obj} <br/>
 * Obj[o, a]  --》  Obj {obj , A} <br/>
 */
public class ParamProvider implements IParamProvider {
    @NonNull
    private Class<?>[] parameters;

    @NonNull
    private Object[] arguments;

    private final boolean isExistVariable;

    public boolean isExistVariable() {
        return isExistVariable;
    }

    public ParamProvider(@NonNull Class<?>[] parameters, @Nullable Object[] arguments) {
        this.parameters = parameters;

        if (arguments == null || arguments.getClass().getComponentType() != Object.class) {
            //这里有两个场景：
            // 1. 实参为null时，为了后续方便处理，我们对他进行统一，所以给他加了一个维度
            // 2. 若componentType的类型不为Obj，只有在传入单个非基础类型且非Object类型的参数数组的情况下。
            // 此时形参若只有单个数组参数，那么就会出现形参和实参分别为Class{[A.class}和[A，维度会出现偏差，
            // 所以这里需要给实参加一个维度，平衡偏差
            // PS: 传入单个Obj[]数组也会出现类似的情况，但是我们一般会把这个Obj[]当成参数本身，所以不应该在这里考虑
            // 该情况，这是调用方应该注意的地方
            arguments = new Object[]{arguments};
        }

        this.arguments = arguments;

        isExistVariable = parameters.length > 0 && parameters[parameters.length - 1].isArray();
    }

    // ---------------------- 形参 -------------------
    @NonNull
    public Class<?>[] getParameters() {
        return parameters;
    }

    @NonNull
    public Class<?> getParameter(int count) {
        assert parameters.length > count;
        return parameters[count];
    }

    // ---------------------- 实参 -------------------

    @Nullable
    public Object[] getArguments() {
        return arguments;
    }

    @Nullable
    public Object getArgument(int count) {
        return arguments.length > count ? arguments[count] : null;
    }

    public Class<?> getArgumentType(int count) {
        return arguments.length > count ? arguments[count] == null ? null : arguments[count].getClass() : null;
    }

    public int argumentsLength() {
        return arguments.length;
    }

    // ---------------------- 可变参数 -------------------

    /**
     * 当前匹配的参数是否是可变参数部分
     *
     * @param count 选择的参数位置
     * @return 当前待处理参数是否为可变参数
     */
    public boolean isVariableParameter(int count) {
        return count >= parameters.length - 1 && parameters[parameters.length - 1].isArray();
    }

    /**
     * 判断当前是否必须当成可变参数处理
     */
    public boolean mustVariableHandler(int count) {
        return parameters.length != argumentsLength() && isVariableParameter(count);
    }

    @Override
    public void close() {
        arguments = null;
        parameters = null;
    }

    /**
     * 获取可变形参
     */
    public Object getVarArgument(Object[] arguments) {
        if (!isExistVariable) {
            return null;
        }

        Object varArg;

        int varParamPosition = parameters.length - 1; //可变参数的位置

        Class<?> varParamClass = parameters[varParamPosition].getComponentType();

        if (parameters.length == arguments.length) {
            Class<?> varArgClass = arguments[varParamPosition].getClass();

            if (!varArgClass.isArray()) {
                varArg = Array.newInstance(varParamClass, 1);

                ReflectArrayUtil.setElement(varArg, 0, arguments[varParamPosition]);
            } else {
                varArg = arguments[varParamPosition];
                varArgClass = varArg.getClass().getComponentType();

                if (varArgClass != varParamClass &&
                        ReflectClassUtil.primitiveEquals(varParamClass, varArgClass)) {
                    Object newVarArg = Array.newInstance(varParamClass,
                            ReflectArrayUtil.getLength(varArg));
                    ReflectArrayUtil.arraycopy(varArg, 0, newVarArg, 0,
                            ReflectArrayUtil.getLength(varArg));
                    varArg = newVarArg;
                }
            }
        } else if (arguments.length < parameters.length) {
            varArg = Array.newInstance(varParamClass, 0);
        } else {
            //将实参中可变参数的位置及其后面部分的所有参数组装成可变参数对应的数组
            varArg = Array.newInstance(varParamClass,
                    arguments.length - varParamPosition);

            ReflectArrayUtil.arraycopy(arguments, varParamPosition, varArg, 0,
                    arguments.length - varParamPosition);
        }

        return varArg;
    }

    /**
     * 调整实参
     */
    public Object modifyArguments(Class<?>[] parameters, Object[] arguments) {
        //形参长度为1且实参为空或空数组时，实参类型未知或为Object类型，与形参不符，这里对该情况进行类型调整
        boolean isNull = arguments.length == 1 && arguments[0] == null;
        if (parameters.length == 1 && (isNull || arguments.length == 0)) {
            Class<?> parameter = parameters[0];

            if (!parameter.isPrimitive()) {
                if (parameter.isArray() && arguments.length == 0) {
                    return Array.newInstance(parameter.getComponentType(), 0);
                } else if (isNull) {
                    return ((Object[]) Array.newInstance(parameter, 1))[0];
                }
            }
        }

        //对可变参数进行调整
        Object varArg = getVarArgument(arguments);
        if (varArg != null) {
            int varParamPosition = parameters.length - 1; //可变参数的位置

            if (varParamPosition == 0) {
                return varArg;
            } else {
                Class<?> type = parameters.length == 1 ? parameters[0] : Object.class;
                Object newArguments = Array.newInstance(type, parameters.length);
                ReflectArrayUtil.arraycopy(arguments, 0, newArguments, 0, varParamPosition);
                ReflectArrayUtil.setElement(newArguments, varParamPosition, varArg); //将可变参数组装到实参上
                return newArguments;
            }
        }

        return arguments;
    }
}
