package blue.fen.reflect.core.impl.method;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Method;

import blue.fen.reflect.arena.method.MethodArena;
import blue.fen.reflect.arena.param.ParamPlayer;
import blue.fen.reflect.core.interfaces.method.IBFMethod;

/**
 * 项目名：BFReflect
 * 包名：blue.fen.reflect.core.impl.method
 * 创建时间：2023/1/31 23:08 （星期二｝
 * 作者： blue_fen
 * 描述：方法反射处理类
 */
public class BFReflectMethod implements IBFMethod {
    @Nullable
    @Override
    public Object invoke(Object object, String name, Object... parameters) throws Exception {
        checkObject(object);
        checkName(name);

        Class<?> clazz;

        if (object instanceof Class) {
            clazz = (Class<?>) object;
            object = null; //object为Class说明调用的是静态方法
        } else {
            clazz = object.getClass();
        }

        ParamPlayer<Method> player = new MethodArena(clazz, name, parameters).start();

        Method method = player.getSite();

        if (player.isArray()) {
            return method.invoke(object, (Object[]) player.getArguments());
        } else {
            return method.invoke(object, player.getArguments());
        }
    }

    @NonNull
    @Override
    public Method find(Class<?> clazz, String name, Class<?>... parameterTypes) throws Exception {
        checkClass(clazz);
        checkName(name);

        return clazz.getMethod(name, parameterTypes);
    }

    private void checkName(String name) throws IllegalArgumentException {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("待反射方法名不能为空！");
        }
    }

    private void checkObject(Object object) throws NullPointerException {
        if (object == null) {
            throw new NullPointerException("待处理对象不能为空，若要调用静态方法，请传入方法所属的Class对象");
        }
    }

    private void checkClass(Class<?> clazz) throws NullPointerException {
        if (clazz == null) {
            throw new NullPointerException("待处理类型不能为空");
        }
    }
}
