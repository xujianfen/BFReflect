package blue.fen.reflect.param.matching.filter.node.leaf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import blue.fen.reflect.param.matching.filter.node.MatchingFilterLeaf;
import blue.fen.reflect.param.model.IParamProvider;
import blue.fen.reflect.param.matching.priority.MatchingSpec;


/**
 * 项目名：NotebookComponent <br/>
 * 包名：blue.fen.reflect.arena.method.scoring <br/>
 * 创建时间：2023/2/3 23:55 （星期五｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：无参方法、实参为null或者形参和实参类型相同时匹配的过滤器
 */
public class SameMatchingFilter extends MatchingFilterLeaf {
    @Override
    public boolean intercept() {
        return true;
    }

    @Override
    public int matchingOutside(IParamProvider provider, int count) {
        Class<?>[] parameters = provider.getParameters();
        Object[] arguments = provider.getArguments();

        if (parameters.length == 0) {
            if (arguments != null && arguments.getClass() == Object[].class) {
                //当方法无参时，只支持参数为Object[0]的情况
                return arguments.length == 0 ? MatchingSpec.make(getId()) : MatchingSpec.MISMATCH;
            } else {
                return MatchingSpec.MISMATCH; //无参方法不匹配Object[]外的类型
            }
        }

        return MatchingSpec.SKIP;
    }

    @Override
    public int matchingMiddle(@NonNull Class<?> parameterClass, @Nullable Class<?> argumentClass, @Nullable Object argument) {
        if (argument == null && parameterClass.isPrimitive()) {
            return MatchingSpec.MISMATCH;
        }

        return matching(parameterClass, argumentClass);
    }

    @Override
    public int matching(@NonNull Class<?> parameter, @Nullable Class<?> argument) {
        if (argument == null) {
            return parameter.isPrimitive() ? MatchingSpec.MISMATCH : MatchingSpec.make(getId());
        }

        if (parameter == argument) {
            return MatchingSpec.make(getId());
        }

        if (parameter.isPrimitive() && argument.isPrimitive()) {
            return MatchingSpec.MISMATCH;
        }

        return MatchingSpec.SKIP;
    }
}
