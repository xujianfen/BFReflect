package blue.fen.reflect.param.matching.filter.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import blue.fen.reflect.param.model.IParamProvider;


/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.param.matching.model <br/>
 * 创建时间：2023/2/3 23:30 （星期五｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：参数匹配过滤器的接口
 */
public interface IMatchingFilter {
    /**
     * 判断参数是否匹配 （最外层）
     */
    int matchingOutside(IParamProvider provider, int count);

    /**
     * 判断参数是否匹配 （中间层）
     */
    int matchingMiddle(@NonNull Class<?> parameterClass, @Nullable Class<?> argumentClass, @Nullable Object argument);

    /**
     * 判断参数是否匹配 （底层）
     *
     * @param parameter 形参类型
     * @param argument  实参类型
     */
    int matching(@NonNull Class<?> parameter, @Nullable Class<?> argument);
}
