package blue.fen.reflect.core.interfaces;

import androidx.annotation.NonNull;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.reflect.interfaces <br/>
 * 创建时间：2023/4/29 02:12 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：反射操作代理的接口
 */
public interface IReflectProxy<T extends IReflect> {
    @NonNull
    T getImpl();
}
