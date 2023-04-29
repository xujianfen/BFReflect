package blue.fen.reflect.utils;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.utils <br/>
 * 创建时间：2023/4/29 19:09 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：
 * 用于延迟初始化的单一实例帮助程序类。
 * <p>
 * 以 frameworks/base/include/utils/Singleton.h 为模型
 */
public abstract class Singleton<T> {
    public Singleton() {
    }

    private T mInstance;

    protected abstract T create();

    public final T get() {
        synchronized (this) {
            if (mInstance == null) {
                mInstance = create();
            }
            return mInstance;
        }
    }
}
