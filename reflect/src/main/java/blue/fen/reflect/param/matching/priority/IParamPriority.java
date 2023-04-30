package blue.fen.reflect.param.matching.priority;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.param.matching.priority <br/>
 * 创建时间：2023/4/29 14:23 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：参数优先级获取接口
 */
public interface IParamPriority {
    /**
     * 默认优先级
     */
    int defaultPriority();

    /**
     * 淘汰优先级
     */
    int mismatchPriority();

    /**
     * 判断参数是否匹配
     *
     * @param priority 待判断参数优先级
     * @return 若 {@code priority}表示可以匹配到参数，则返回true，否则返回false
     */
    boolean isMatch(int priority);

    /**
     * 判断参数是否不匹配
     *
     * @param priority 待判断参数优先级
     * @return 若 {@code priority}表示无法匹配参数，则返回true，否则返回false
     */
    boolean isMismatch(int priority);

    /**
     * 将过滤器标识转化为分数匹配优先级
     */
    int transformPriority(@MatchingSpec.FilterPriority int filter);

    /**
     * 修改优先级
     *
     * @param original 原来的优先级
     * @param priority 注册新参数的匹配优先级<br/>
     * @param increase 增量
     * @return 返回新的优先级
     */
    int modifyPriority(int original, int priority, int increase);
}