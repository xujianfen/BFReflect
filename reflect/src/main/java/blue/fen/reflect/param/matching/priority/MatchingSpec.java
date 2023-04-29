package blue.fen.reflect.param.matching.priority;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.param.matching.priority <br/>
 * 创建时间：2023/4/29 14:26 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：参数匹配规则
 */
public class MatchingSpec {

    private static final int FILTER_SHIFT = 28;

    /**
     * 过滤器标识掩码
     */
    private static final int FILTER_MASK = 0x7 << FILTER_SHIFT;

    /**
     * 匹配分数掩码
     */
    private static final int MATCH_SCORE_MASK = ~FILTER_MASK;

    public static final int SAME_FILTER = 0;

    public static final int SUPER_FILTER = 1;

    public static final int WRAPPER_FILTER = 2;

    public static final int ARRAY_VARIABLE_FILTER = 3;

    public static final int ARRAY_WRAPPER_FILTER = 4;


    /**
     * 不匹配，形参为原始类型且实参为null时，则直接不匹配
     */
    public static final int MISMATCH = -1;

    /**
     * 表示当前不匹配，跳过当前匹配节点，执行下一个匹配节点
     */
    public static final int SKIP = 0;

    @Retention(SOURCE)
    @Target({PARAMETER, FIELD, METHOD})
    @IntDef({SAME_FILTER, SUPER_FILTER, WRAPPER_FILTER, ARRAY_VARIABLE_FILTER, ARRAY_WRAPPER_FILTER})
    @interface FilterPriority {
    }

    /**
     * 判断参数是否匹配
     */
    public static boolean isMatch(@FilterPriority int match) {
        return getScore(match) > 0;
    }

    /**
     * 判断参数是否不匹配
     */
    public static boolean isMisMatch(@FilterPriority int match) {
        return getScore(match) < 0;
    }

    /**
     * 判断参数是否不匹配
     */
    public static boolean interrupt(@FilterPriority int match) {
        return isMatch(match) || isMisMatch(match);
    }

    /**
     * 判断是否需要跳过
     */
    public static boolean isSkip(@FilterPriority int match) {
        return getScore(match) == SKIP;
    }

    public static int make(int priority) {
        return make(priority, 1);
    }

    public static int make(int priority, int match) {
        return ((priority << FILTER_SHIFT) & FILTER_MASK) | (match & MATCH_SCORE_MASK);
    }

    public static int getFilter(int match) {
        return (match & FILTER_MASK) >> FILTER_SHIFT;
    }

    public static int getScore(int match) {
        return (match & MATCH_SCORE_MASK);
    }
}
