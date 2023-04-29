package blue.fen.reflect.param.matching.filter.node.leaf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import blue.fen.reflect.param.matching.filter.node.MatchingFilterLeaf;
import blue.fen.reflect.param.matching.priority.MatchingSpec;
import blue.fen.reflect.utils.ReflectClassUtil;


/**
 * 项目名：NotebookComponent <br/>
 * 包名：blue.fen.reflect.arena.method.scoring <br/>
 * 创建时间：2023/2/4 00:04 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：继承关系匹配过滤器
 */
public class SuperMatchingFilter extends MatchingFilterLeaf {
    @Override
    public int matching(@NonNull Class<?> parameter, @Nullable Class<?> argument) {
        if (argument == null) {
            return MatchingSpec.SKIP;
        }

        if (ReflectClassUtil.isPrimitive(parameter) ||
                ReflectClassUtil.isPrimitive(argument)) {
            //若两者皆为原始类型，则应该由SameMatchingFilter负责匹配，这里不用管
            return MatchingSpec.SKIP;
        }


        return MatchingSpec.make(getId(), ReflectClassUtil.ancestralInterval(argument, parameter));
    }
}
