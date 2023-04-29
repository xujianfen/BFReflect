package blue.fen.reflect.result.factory;

import androidx.annotation.NonNull;

import blue.fen.reflect.result.IReflectResult;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.reflect.interfaces <br/>
 * 创建时间：2023/4/29 02:21 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：反射结果工厂接口
 */
public interface IReflectResultFactory {
    @NonNull
    <T> IReflectResult<T> success(T result);

    @NonNull
    <T> IReflectResult<T> error(Exception err);

    @NonNull
    static IReflectResultFactory get() {
        return new ReflectResultFactory();
    }
}
