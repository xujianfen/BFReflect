package blue.fen.reflect;

import android.os.Build;

import java.lang.reflect.Method;

import blue.fen.reflect.core.expose.BFClass;
import blue.fen.reflect.core.expose.BFField;
import blue.fen.reflect.core.expose.BFMethod;
import blue.fen.reflect.core.impl.clazz.BFReflectClass;
import blue.fen.reflect.core.impl.field.BFReflectField;
import blue.fen.reflect.core.impl.method.BFReflectMethod;

/**
 * 项目名：BFReflect
 * 包名：blue.fen.reflect
 * 创建时间：2023/1/21 23:47 （星期六｝
 * 作者： blue_fen
 * 描述：用于反射的工具类
 */
public class BFReflect {
    static {
        //在高版本SDK中，很多方法都被hidden，所以需要绕过hidden对系统代码的屏蔽
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                Method forName = Class.class.getDeclaredMethod("forName", String.class);
                Method getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod",
                        String.class, Class[].class);

                Class<?> vmRuntimeClass = (Class<?>) forName.invoke(null, "dalvik.system.VMRuntime");

                Method getRuntime = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "getRuntime", null);
                Method setHiddenApiExemptions = (Method) getDeclaredMethod.invoke(vmRuntimeClass,
                        "setHiddenApiExemptions", new Class[]{String[].class});

                if (getRuntime != null && setHiddenApiExemptions != null) {
                    Object sVmRuntime = getRuntime.invoke(null);
                    setHiddenApiExemptions.invoke(sVmRuntime, new Object[]{new String[]{"L"}});
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public final static BFField FIELD;

    public final static BFMethod METHOD;

    public final static BFClass CLASS;

    static {
        FIELD = new BFField(new BFReflectField());
        METHOD = new BFMethod(new BFReflectMethod());
        CLASS = new BFClass(new BFReflectClass());
    }
}
