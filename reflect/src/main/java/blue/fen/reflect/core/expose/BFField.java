package blue.fen.reflect.core.expose;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;

import blue.fen.reflect.BusMessage;
import blue.fen.reflect.result.IReflectResult;
import blue.fen.reflect.core.interfaces.IReflectProxy;
import blue.fen.reflect.result.factory.IReflectResultFactory;
import blue.fen.reflect.core.interfaces.field.IBFField;
import blue.fen.reflect.core.interfaces.field.IBFFieldN;
import blue.fen.reflect.core.interfaces.field.IBFFieldX;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.base <br/>
 * 创建时间：2023/4/29 01:44 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：属性反射工具类
 */
public class BFField implements IBFField, IBFFieldN, IBFFieldX, IReflectProxy<IBFField> {
    @NonNull
    private final IBFField impl;

    public BFField(@NonNull IBFField impl) {
        this.impl = impl;
    }

    @Override
    @NonNull
    public IBFField getImpl() {
        return impl;
    }

    @Override
    public void set(Object object, String name, Object value) throws Exception {
        getImpl().set(object, name, value);
    }

    @Nullable
    @Override
    public Object get(Object object, String name) throws Exception {
        return getImpl().get(object, name);
    }

    @NonNull
    @Override
    public Field find(Class<?> clazz, String name) throws Exception {
        return getImpl().find(clazz, name);
    }

    @Override
    public boolean setN(Object object, String name, Object value) {
        try {
            getImpl().set(object, name, value);
            return true;
        } catch (Exception exception) {
            BusMessage.getInstance().sendMessage(BusMessage.N_EXCEPTION, exception);
            return false;
        }
    }

    @Nullable
    @Override
    public Object getN(Object object, String name) {
        try {
            return getImpl().get(object, name);
        } catch (Exception exception) {
            BusMessage.getInstance().sendMessage(BusMessage.N_EXCEPTION, exception);
            return null;
        }
    }

    @Nullable
    @Override
    public Field findN(Class<?> clazz, String name) {
        try {
            return getImpl().find(clazz, name);
        } catch (Exception exception) {
            BusMessage.getInstance().sendMessage(BusMessage.N_EXCEPTION, exception);
            return null;
        }
    }

    @NonNull
    @Override
    public IReflectResult<Void> setX(Object object, String name, Object value) {
        try {
            getImpl().set(object, name, value);
            return IReflectResultFactory.get().success(null);
        } catch (Exception exception) {
            return IReflectResultFactory.get().error(exception);
        }
    }

    @NonNull
    @Override
    public IReflectResult<Object> getX(Object object, String name) {
        try {
            Object result = getImpl().get(object, name);
            return IReflectResultFactory.get().success(result);
        } catch (Exception exception) {
            return IReflectResultFactory.get().error(exception);
        }
    }

    @NonNull
    @Override
    public IReflectResult<Field> findX(Class<?> clazz, String name) {
        try {
            Field field = getImpl().find(clazz, name);
            return IReflectResultFactory.get().success(field);
        } catch (Exception exception) {
            return IReflectResultFactory.get().error(exception);
        }
    }
}
