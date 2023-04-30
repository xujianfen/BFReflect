package blue.fen.reflect.param.matching.priority;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.param.matching.priority <br/>
 * 创建时间：2023/4/30 12:51 （星期日｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：将{@linkplain MatchingPriority}包装成${@linkplain IParamPriority}
 */
public class MatchingParamPriority implements IParamPriority {
    @Override
    public int defaultPriority() {
        return MatchingPriority.INIT;
    }

    @Override
    public int mismatchPriority() {
        return MatchingPriority.MISMATCH;
    }

    @Override
    public boolean isMatch(int priority) {
        return priority >= 0;
    }

    @Override
    public boolean isMismatch(int priority) {
        return priority == MatchingPriority.MISMATCH;
    }

    @Override
    public int transformPriority(int filter) {
        return MatchingPriority.transformPriority(filter);
    }

    @Override
    public int modifyPriority(int original, int priority, int increase) {
        return MatchingPriority.modifyPriority(original, priority, increase);
    }
}
