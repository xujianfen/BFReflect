package blue.fen.reflect.core.expose;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Method;

import blue.fen.reflect.result.IReflectResult;
import blue.fen.reflect.core.interfaces.IReflectProxy;
import blue.fen.reflect.result.factory.IReflectResultFactory;
import blue.fen.reflect.core.interfaces.method.IBFMethod;
import blue.fen.reflect.core.interfaces.method.IBFMethodN;
import blue.fen.reflect.core.interfaces.method.IBFMethodX;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.base <br/>
 * 创建时间：2023/4/29 02:35 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：方法反射工具类
 */
public class BFMethod implements IBFMethod, IBFMethodN, IBFMethodX, IReflectProxy<IBFMethod> {
    @NonNull
    private final IBFMethod impl;

    public BFMethod(@NonNull IBFMethod impl) {
        this.impl = impl;
    }

    @NonNull
    @Override
    public IBFMethod getImpl() {
        return impl;
    }

    @Nullable
    @Override
    public Object invoke(Object object, String name, Object... parameters) throws Exception {
        return getImpl().invoke(object, name, parameters);
    }

    @NonNull
    @Override
    public Method find(Class<?> clazz, String name, Class<?>... parameterTypes) throws Exception {
        return getImpl().find(clazz, name, parameterTypes);
    }

    @Nullable
    @Override
    public Object invokeN(Object object, String name, Object... parameters) {
        try {
            return getImpl().invoke(object, name, parameters);
        } catch (Exception ignored) {
            return null;
        }
    }

    @Nullable
    @Override
    public Method findN(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return getImpl().find(clazz, name, parameterTypes);
        } catch (Exception ignored) {
            return null;
        }
    }

    @NonNull
    @Override
    public IReflectResult<Object> invokeX(Object object, String name, Object... parameters) {
        try {
            Object result = getImpl().invoke(object, name, parameters);
            return IReflectResultFactory.get().success(result);
        } catch (Exception exception) {
            return IReflectResultFactory.get().error(exception);
        }
    }

    @NonNull
    @Override
    public IReflectResult<Method> findX(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            Method method = getImpl().find(clazz, name, parameterTypes);
            return IReflectResultFactory.get().success(method);
        } catch (Exception exception) {
            return IReflectResultFactory.get().error(exception);
        }
    }
}
