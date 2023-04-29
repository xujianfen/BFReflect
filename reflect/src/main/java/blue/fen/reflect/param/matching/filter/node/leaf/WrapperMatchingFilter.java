package blue.fen.reflect.param.matching.filter.node.leaf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import blue.fen.reflect.param.matching.filter.node.MatchingFilterLeaf;
import blue.fen.reflect.param.matching.priority.MatchingSpec;
import blue.fen.reflect.utils.ReflectClassUtil;

/**
 * 项目名：NotebookComponent <br/>
 * 包名：blue.fen.reflect.arena.method.scoring <br/>
 * 创建时间：2023/2/3 23:31 （星期五｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：原始类型与包装类匹配的过滤器
 */
public class WrapperMatchingFilter extends MatchingFilterLeaf {
    @Override
    public int matching(@NonNull Class<?> parameter, @Nullable Class<?> argument) {
        if (argument == null) {
            return MatchingSpec.SKIP;
        }

        if (parameter.isPrimitive() || argument.isPrimitive()) {
            return ReflectClassUtil.primitiveEquals(parameter, argument) ? MatchingSpec.make(getId()) : MatchingSpec.MISMATCH;
        }

        return MatchingSpec.SKIP;
    }
}
