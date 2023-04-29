package blue.fen.reflect.core.impl.field;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;

import blue.fen.reflect.core.interfaces.field.IBFField;

/**
 * 项目名：BFReflect
 * 包名：blue.fen.reflect.core.impl.field
 * 创建时间：2023/1/31 23:10 （星期二｝
 * 作者： blue_fen
 * 描述：属性反射处理类
 */
public class BFReflectField implements IBFField {
    @Override
    public void set(Object object, String name, Object value) throws Exception {
        checkObject(object);
        checkName(name);

        Class<?> clazz;

        if (object instanceof Class) {
            clazz = (Class<?>) object;
            object = null; //object为Class说明调用的是静态方法
        } else {
            clazz = object.getClass();
        }

        Field field = find(clazz, name);
        field.set(object, value);
    }

    @Nullable
    @Override
    public Object get(Object object, String name) throws Exception {
        checkObject(object);
        checkName(name);

        Class<?> clazz;

        if (object instanceof Class) {
            clazz = (Class<?>) object;
            object = null; //object为Class说明调用的是静态方法
        } else {
            clazz = object.getClass();
        }

        Field field = find(clazz, name);
        return field.get(object);
    }

    @NonNull
    @Override
    public Field find(Class<?> clazz, String name) throws Exception {
        checkClass(clazz);
        checkName(name);

        Class<?> source = clazz;

        while (clazz != Object.class && clazz != null) {
            try {
                Field field = clazz.getDeclaredField(name);
                //跳过权限检查
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException ignored) {
            }

            clazz = clazz.getSuperclass();
        }

        throw new NoSuchFieldException(source.getCanonicalName() + "未找到[" + name + "]属性");
    }

    private void checkName(String name) throws IllegalArgumentException {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("待反射变量名不能为空！");
        }
    }

    private void checkObject(Object object) throws NullPointerException {
        if (object == null) {
            throw new NullPointerException("待处理对象不能为空，若要获取静态变量，请传入变量所属的Class对象");
        }
    }

    private void checkClass(Class<?> clazz) throws NullPointerException {
        if (clazz == null) {
            throw new NullPointerException("待处理类型不能为空");
        }
    }
}
