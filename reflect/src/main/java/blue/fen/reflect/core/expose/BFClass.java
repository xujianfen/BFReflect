package blue.fen.reflect.core.expose;

import androidx.annotation.NonNull;

import blue.fen.reflect.core.interfaces.IReflectProxy;
import blue.fen.reflect.core.interfaces.clazz.IBFClass;
import blue.fen.reflect.core.interfaces.clazz.IBFClassN;
import blue.fen.reflect.core.interfaces.clazz.IBFClassX;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.base <br/>
 * 创建时间：2023/4/29 01:43 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：类反射工具类
 */
public class BFClass implements IBFClass, IBFClassN, IBFClassX, IReflectProxy<IBFClass> {
    private final IBFClass impl;

    public BFClass(IBFClass impl) {
        this.impl = impl;
    }

    @NonNull
    @Override
    public IBFClass getImpl() {
        return impl;
    }
}
