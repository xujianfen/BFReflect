package blue.fen.reflect.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.utils <br/>
 * 创建时间：2023/4/29 16:40 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：Class帮助工具
 */
public class ReflectClassUtil {
    /**
     * 将原语｛@code Class｝映射到其对应的包装器｛@codeClass｝。
     */
    private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<>();

    static {
        primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
        primitiveWrapperMap.put(Byte.TYPE, Byte.class);
        primitiveWrapperMap.put(Character.TYPE, Character.class);
        primitiveWrapperMap.put(Short.TYPE, Short.class);
        primitiveWrapperMap.put(Integer.TYPE, Integer.class);
        primitiveWrapperMap.put(Long.TYPE, Long.class);
        primitiveWrapperMap.put(Double.TYPE, Double.class);
        primitiveWrapperMap.put(Float.TYPE, Float.class);
        primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
    }

    /**
     * 将包装器｛@code Class｝es映射到其相应的基元类型。
     */
    private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<>();

    static {
        for (final Map.Entry<Class<?>, Class<?>> entry : primitiveWrapperMap.entrySet()) {
            final Class<?> primitiveClass = entry.getKey();
            final Class<?> wrapperClass = entry.getValue();
            if (!primitiveClass.equals(wrapperClass)) {
                wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
            }
        }
    }

    /**
     * 获取{@code objects}对应的类型数组
     */
    public static Class<?>[] getClassList(Object... objects) {
        if (objects == null || objects.length == 0) return null;

        Class<?>[] classes = new Class[objects.length];

        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null) {
                classes[i] = objects[i].getClass();
            } else {
                return null;
            }
        }

        return classes;
    }

    /**
     * 是否类型来自Object
     *
     * @param clazz 待判断类型
     * @return 判断结果
     */
    public static boolean isSourceObject(Class<?> clazz) {
        return clazz.getSuperclass() != null;  //具有父类说明不是Object
    }

    public static boolean isWrapper(Class<?> clazz) {
        return wrapperPrimitiveMap.containsKey(clazz);
    }

    public static boolean isPrimitive(Class<?> clazz) {
        return primitiveWrapperMap.containsKey(clazz);
    }

    public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return isPrimitive(clazz) || isWrapper(clazz);
    }

    public static Class<?> toWrapper(Class<?> clazz) {
        return primitiveWrapperMap.get(clazz);
    }

    public static Class<?> toPrimitive(Class<?> clazz) {
        return wrapperPrimitiveMap.get(clazz);
    }


    public static boolean primitiveEquals(Class<?> cls1, Class<?> cls2) {
        if (cls1.equals(cls2)) {
            return true;
        } else if (cls1.isPrimitive() && !cls2.isPrimitive()) {
            return primitiveWrapperMap.get(cls1).equals(cls2);
        } else if (cls2.isPrimitive() && !cls1.isPrimitive()) {
            return primitiveWrapperMap.get(cls2).equals(cls1);
        }

        return false;
    }

    /**
     * 保持类型或者将原始类型转化为包装类
     *
     * @param clazz 待处理类型
     * @return 如果处理类型为原始类型，则返回对应包装类，否则保持不变
     */
    public static Class<?> keepOrPrimitiveToWrapper(Class<?> clazz) {
        if (clazz == null) return null;

        if (clazz.isPrimitive()) {
            return primitiveWrapperMap.get(clazz);
        }

        return clazz;
    }

    /**
     * 判断祖先间隔
     *
     * @param young 年轻类
     * @param old   老年类
     * @return 年轻类和老年类之间隔的代数，如果两者没有继承关系者返回-1
     */
    public static int ancestralInterval(Class<?> young, Class<?> old) {
        if (young == old) {
            return 0;
        }

        int count = 1;

        Class<?> superclass = young.getSuperclass();
        Class<?>[] interfaces = young.getInterfaces();

        while (superclass != null && !matching(old, superclass, interfaces)) {
            count++;

            superclass = superclass.getSuperclass();

            if (superclass != null) {
                interfaces = superclass.getInterfaces();
            }
        }

        return superclass != null ? count : -1;
    }


    /**
     * 判断形参类型是否与实参的某个父类祖先或对应的实现接口匹配
     */
    private static boolean matching(Class<?> parameter, Class<?> superclass, Class<?>[] interfaces) {
        if (superclass == parameter) {
            return true;
        }

        for (Class<?> interfaceClass : interfaces) {
            if (interfaceClass == parameter) {
                return true;
            }
        }

        return false;
    }
}
