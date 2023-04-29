package blue.fen.reflect.result;

import androidx.annotation.Nullable;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.result <br/>
 * 创建时间：2023/4/29 02:51 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：反射结果包装实例
 */
public class ReflectResult<T> implements IReflectResult<T> {
    @Nullable
    private final T result;

    @Nullable
    private final Exception exception;

    private final boolean isSuccess;

    private ReflectResult(@Nullable T result, @Nullable Exception exception, boolean isSuccess) {
        this.result = result;
        this.exception = exception;
        this.isSuccess = isSuccess;
    }

    public static <T> ReflectResult<T> success(T result) {
        return new ReflectResult<>(result, null, true);
    }

    public static <T> ReflectResult<T> error(Exception exception) {
        return new ReflectResult<>(null, exception, false);
    }

    @Nullable
    @Override
    public T get() {
        return result;
    }

    @Override
    public void tryThrow() throws Exception {
        if (exception != null) {
            throw exception;
        }
    }

    @Nullable
    @Override
    public T getOrThrow() throws Exception {
        tryThrow();
        return get();
    }

    @Nullable
    @Override
    public Exception getThrow() {
        return exception;
    }

    @Override
    public boolean isSuccess() {
        return isSuccess;
    }
}
