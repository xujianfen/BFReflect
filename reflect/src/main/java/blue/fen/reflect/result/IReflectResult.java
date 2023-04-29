package blue.fen.reflect.result;

import androidx.annotation.Nullable;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.reflect.interfaces <br/>
 * 创建时间：2023/4/29 01:56 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：反射结果包装接口
 */
public interface IReflectResult<T> {
    /**
     * 获取反射结果
     */
    @Nullable
    T get();

    /**
     * 尝试抛出异常
     */
    void tryThrow() throws Exception;

    /**
     * 获取反射结果或者抛出异常
     */
    @Nullable
    T getOrThrow() throws Exception;

    /**
     * 获取异常结果
     */
    @Nullable
    Exception getThrow();

    /**
     * 返回是否反射成功
     */
    boolean isSuccess();
}
