package blue.fen.reflect;

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
    public final static BFField FIELD;

    public final static BFMethod METHOD;

    public final static BFClass CLASS;

    static {
        FIELD = new BFField(new BFReflectField());
        METHOD = new BFMethod(new BFReflectMethod());
        CLASS = new BFClass(new BFReflectClass());
    }
}
