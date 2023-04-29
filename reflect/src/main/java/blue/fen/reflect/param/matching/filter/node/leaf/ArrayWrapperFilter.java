package blue.fen.reflect.param.matching.filter.node.leaf;

import androidx.annotation.NonNull;

import blue.fen.reflect.param.matching.filter.node.MatchingFilterLeaf;
import blue.fen.reflect.param.matching.priority.MatchingSpec;
import blue.fen.reflect.utils.ReflectClassUtil;

/**
 * 项目名：NotebookComponent <br/>
 * 包名：blue.fen.reflect.arena.method.filter.custom <br/>
 * 创建时间：2023/2/5 18:17 （星期日｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：可变参数部分 原始类型与包装类匹配的过滤器
 */
public class ArrayWrapperFilter extends MatchingFilterLeaf {
    @Override
    public int matching(@NonNull Class<?> parameter, @NonNull Class<?> argument) {
        if (ReflectClassUtil.isPrimitive(parameter) ||
                ReflectClassUtil.isPrimitive(argument)) {
            //若两者皆为原始类型，则应该由SameMatchingFilter负责匹配，这里不用管
            return ReflectClassUtil.primitiveEquals(parameter, argument) ? MatchingSpec.make(getId()) : MatchingSpec.MISMATCH;
        }

        return MatchingSpec.MISMATCH;
    }
}