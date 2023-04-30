package blue.fen.reflect.param.matching.priority;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.param.model <br/>
 * 创建时间：2023/4/29 16:51 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：参数类型匹配优先级
 */
public class MatchingPriority {
    /**
     * 初始优先级
     */
    public static final int INIT = -2;
    /**
     * 不匹配，形参为原始类型且实参为null时，则直接不匹配
     */
    public static final int MISMATCH = -1;

    /**
     * 完全相同时优先级最高，形参不为原始类型且实参为null时，则直接匹配为相同
     */
    private static final int SAME = 0;


    /**
     * 形参为实参的父类（如果是数组就对比元素类型），同为父类时，以距离的远近来判断优先级 若距离相同，则优先级相同
     */
    private static final int SUPER = 1;//范围 0x00000001 ~ 0x000000FF

    /**
     * 原始类型和包装类之间的匹配，原始类型匹配的最低优先级
     */
    private static final int WRAPPER = 1 << 8;//范围 0x00000100 ~ 0x0000FF00


    //--------------------- Array -------------------------
    /**
     * 可变参数匹配
     */
    private static final int ARRAY_VARIABLE = 1 << 16; //0x00010000 ~ 0x00FF0000 255

    /**
     * 原始类型与包装类型数组的匹配
     */
    private static final int ARRAY_WRAPPER = 1 << 24; //0x01000000 ~ 0x7F000000 127

    private static final int PRIORITY_MASK = 0x7FFFFFFF;

    /**
     * 将过滤器标识转化为分数匹配优先级
     */
    public static int transformPriority(@MatchingSpec.FilterPriority int filter) {
        switch (filter) {
            case MatchingSpec.SAME_FILTER:
                return SAME;
            case MatchingSpec.SUPER_FILTER:
                return SUPER;
            case MatchingSpec.WRAPPER_FILTER:
                return WRAPPER;
            case MatchingSpec.ARRAY_VARIABLE_FILTER:
                return ARRAY_VARIABLE;
            case MatchingSpec.ARRAY_WRAPPER_FILTER:
                return ARRAY_WRAPPER;
            default:
                throw new IllegalArgumentException("参数过滤失败，不合法的过滤器: " + filter);
        }
    }

    /**
     * 修改优先级
     *
     * @param original 原来的优先级
     * @param priority 注册新参数的匹配优先级<br/>
     *                 需要注意，一次匹配中，相同优先级出现的次数有数量限制：
     *                 {@link MatchingPriority#SAME}、
     *                 {@link MatchingPriority#INIT}和
     *                 {@link MatchingPriority#MISMATCH}没有数量限制，
     *                 {@link MatchingPriority#ARRAY_WRAPPER}最大匹配数量为127，
     *                 其余为255
     * @param increase 增量
     * @return 返回新的优先级
     */
    public static int modifyPriority(int original, int priority, int increase) {
        if (priority == MISMATCH || original == MISMATCH) return MISMATCH;

        if (priority == 0) return original == INIT ? 0 : original;

        if (original == INIT) original = 0;

        int cursor = 0x000000FF;

        int target = 1;

        while (cursor != 0 && (priority & cursor) != target) {
            cursor <<= 8;
            target = cursor >>> 7 & cursor; //cursor可能为负数，所以要使用无符号右移
            // cursor < 0 ? ARRAY_WRAPPER : cursor >> 7 & cursor;
        }

        if (cursor == 0) {
            throw new IllegalArgumentException("传入的参数匹配优先级不合法: 0x" + Integer.toHexString(priority));
        }

        cursor = cursor & PRIORITY_MASK;

        if ((original & cursor) == cursor) {
            return original;
        }

        if (increase == 1) {
            //这里添加target，而不是increase，因为increase允许0x0101这样的格式存在，这时候应该把他视为0x01
            return original + target;
        } else {
            int cost = original & cursor;
            int realIncrease = ((cost + target * increase) & cursor) - cost;
            return original + realIncrease;
        }
    }
}
