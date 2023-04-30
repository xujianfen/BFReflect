package blue.fen.reflect.param.matching.priority;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import blue.fen.reflect.utils.Singleton;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.param.matching.priority <br/>
 * 创建时间：2023/4/30 12:41 （星期日｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：参数优先级供应商
 */
public class PriorityProvider implements IParamPriority {
    private static final Singleton<PriorityProvider> sInstance = new Singleton<PriorityProvider>() {
        @Override
        protected PriorityProvider create() {
            return new PriorityProvider();
        }
    };

    public static PriorityProvider get() {
        return sInstance.get();
    }

    private PriorityProvider() {
        defaultPriority = new MatchingParamPriority();
    }

    @NonNull
    private final IParamPriority defaultPriority;

    @Nullable
    private IParamPriority priority;

    public IParamPriority getPriority() {
        return priority != null ? priority : defaultPriority;
    }

    public void setPriority(@Nullable IParamPriority priority) {
        this.priority = priority;
    }

    public void recoveryPriority() {
        setPriority(null);
    }

    @Override
    public int defaultPriority() {
        return getPriority().defaultPriority();
    }

    @Override
    public int mismatchPriority() {
        return getPriority().mismatchPriority();
    }

    @Override
    public boolean isMatch(int priority) {
        return getPriority().isMatch(priority);
    }

    @Override
    public boolean isMismatch(int priority) {
        return getPriority().isMismatch(priority);
    }

    @Override
    public int transformPriority(int filter) {
        return getPriority().transformPriority(filter);
    }

    @Override
    public int modifyPriority(int original, int priority, int increase) {
        return getPriority().modifyPriority(original, priority, increase);
    }
}
