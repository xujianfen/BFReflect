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
 * 包名：blue.fen.reflect.arena.method <br/>
 * 创建时间：2023/2/4 21:02 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：非可变数组过滤器 <br/>
 * (并不是不处理可变参数部分， 这里分三种情况：
 * 1. 非可变参数部分会当成普通参数处理；
 * 2. 在形参和实参的长度不一致时，不处理可变参数部分，;
 * 3. 形参和实参的长度一致且符合非可变参数子节点处理场景，
 * 即类型相同、存在继承关系或者是基本类型数组和封装类型数组
 * 的组合时，将处理可变参数部分；否则交给后面的可变参数过滤
 * 器处理)
 */
public class WrongVarMatchingFilter extends MatchingFilterInternal {
    @Override
    public int matchingOutside(IParamProvider provider, int count) {
        MatchingFilterNode son = eldestSon();

        if (son == null) {
            return getId();
        }

        if (son.intercept()) {
            int result = son.matchingOutside(provider, count);

            if (MatchingSpec.interrupt(result)) {
                return result;
            }
        }

        if (provider.mustVariableHandler(count)) {
            return MatchingSpec.SKIP;
        }

        Class<?> parameter = provider.getParameter(count);
        Class<?> argument = provider.getArgumentType(count);

        if (argument != null) {
            ReflectArrayUtil.ArrayType pType = ReflectArrayUtil.getComponentType(parameter);
            ReflectArrayUtil.ArrayType aType = ReflectArrayUtil.getComponentType(argument);

            if (aType.sameDimension(pType)) {
                parameter = pType.getComponentType();
                argument = aType.getComponentType();
            } else {
                return MatchingSpec.SKIP;
            }
        }

        return matchingMiddle(parameter, argument, provider.getArgument(count));
    }

    @Override
    public int matchingMiddle(@NonNull Class<?> parameterClass, @Nullable Class<?> argumentClass, @Nullable Object argument) {
        MatchingFilterNode son = eldestSon();

        if (son == null) {
            return getId();
        }

        if (son.intercept()) {
            int result = son.matchingMiddle(parameterClass, argumentClass, argument);

            if (MatchingSpec.interrupt(result)) {
                return result;
            }
        }

        while (son.hashNext()) {
            son = son.next();
            int result = son.matchingMiddle(parameterClass, argumentClass, argument);

            if (MatchingSpec.interrupt(result)) {
                return result;
            }
        }

        return MatchingSpec.SKIP;
    }
}
