package blue.fen.reflect.param.matching.filter.node.leaf;

import androidx.annotation.NonNull;

import blue.fen.reflect.param.matching.filter.node.MatchingFilterLeaf;
import blue.fen.reflect.param.matching.priority.MatchingSpec;
import blue.fen.reflect.utils.ReflectClassUtil;

/**
 * 项目名：NotebookComponent <br/>
 * 包名：blue.fen.reflect.arena.method.filter <br/>
 * 创建时间：2023/2/4 01:50 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：可变参数部分的继承关系匹配过滤器
 */
public class ArrayVariableFilter extends MatchingFilterLeaf {
    @Override
    public int matching(@NonNull Class<?> parameter, @NonNull Class<?> argument) {
        int result = ReflectClassUtil.ancestralInterval(argument, parameter);

        if (result >= 0) {
            return MatchingSpec.make(getId(), result + 1); //因为时可变数组，所以作为相同类型时为1，后续类型也相应要+1
        }

        if (ReflectClassUtil.isPrimitive(parameter) ||
                ReflectClassUtil.isPrimitive(argument)) {
            //若两者皆为原始类型，则应该由SameMatchingFilter负责匹配，这里不用管
            return MatchingSpec.SKIP;
        }

        return MatchingSpec.MISMATCH;
    }
}
