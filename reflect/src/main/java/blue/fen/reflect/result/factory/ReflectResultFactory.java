package blue.fen.reflect.result.factory;

import androidx.annotation.NonNull;

import blue.fen.reflect.result.IReflectResult;
import blue.fen.reflect.result.ReflectResult;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.facotry <br/>
 * 创建时间：2023/4/29 02:48 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：反射结果工厂实例
 */
class ReflectResultFactory implements IReflectResultFactory {
    @NonNull
    @Override
    public <T> IReflectResult<T> success(T result) {
        return obtain(result, true);
    }

    @NonNull
    @Override
    public <T> IReflectResult<T> error(Exception err) {
        return obtain(err, false);
    }

    private <T> IReflectResult<T> obtain(Object result, boolean isSuccess) {
        return make(result, isSuccess);
    }

    private <T> IReflectResult<T> make(Object result, boolean isSuccess) {
        if (isSuccess) {
            return ReflectResult.success((T) result);
        } else {
            return ReflectResult.error((Exception) result);
        }
    }
}
