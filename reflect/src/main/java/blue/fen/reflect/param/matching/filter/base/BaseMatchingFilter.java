package blue.fen.reflect.param.matching.filter.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import blue.fen.reflect.param.model.IParamProvider;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.param.matching.base <br/>
 * 创建时间：2023/4/29 14:35 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：
 */
public abstract class BaseMatchingFilter implements IMatchingFilter {
    @Override
    public int matchingOutside(IParamProvider provider, int count) {
        return matchingMiddle(provider.getParameter(count), provider.getArgumentType(count), provider.getArgument(count));
    }

    @Override
    public int matchingMiddle(@NonNull Class<?> parameterClass, @Nullable Class<?> argumentClass, @Nullable Object argument) {
        return matching(parameterClass, argumentClass);
    }

    @Override
    public int matching(@NonNull Class<?> parameter, @Nullable Class<?> argument) {
        throw new RuntimeException("MatchingFilterLeaf#matching(Class, Class)方法未实现!");
    }
}
