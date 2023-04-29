package blue.fen.reflect.param.model;

import java.lang.reflect.Array;

import blue.fen.reflect.utils.ReflectArrayUtil;
import blue.fen.reflect.utils.ReflectClassUtil;

/**
 * 项目名：NotebookComponent <br/>
 * 包名：blue.fen.reflect.arena.method.filter.base <br/>
 * 创建时间：2023/2/4 22:34 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：数组代理，主要可兼容原始类型数组和包装类型数组
 */
public class ArrayProxy {
    protected final Object source;

    /**
     * 在{@link ArrayProxy#source} 为原始数据类型时，该变量将生成为对应的包装类数组，否则直接赋值为{@link ArrayProxy#source}
     */
    protected Object[] proxy;

    public ArrayProxy(Object array) {
        this.source = initArray(array);
    }

    /**
     * 设置代理并将返回值赋值给{@link ArrayProxy#source}
     *
     * @param array 构造方法中传进来的参数
     * @return 调整后的数组
     */
    protected Object initArray(Object array) {
        assert array != null && array.getClass().isArray();

        if (array.getClass().getComponentType().isPrimitive()) {
            int len = ReflectArrayUtil.getLength(array);
            proxy = new Object[len];
            ReflectArrayUtil.arraycopy(array, 0, proxy, 0, len);
        } else {
            proxy = (Object[]) array;
        }

        return array;
    }

    public void set(int count, Object element) {
        assert proxy.length > count;
        proxy[count] = element;
        ReflectArrayUtil.setElement(source, count, element);
    }

    public Object get(int count) {
        assert proxy.length > count;
        return proxy[count];
    }

    public Class<?> getType(int count) {
        assert proxy.length > count;
        return ReflectArrayUtil.getElementType(source, count);
    }

    public Object[] getAll() {
        return proxy;
    }

    public Object getSource() {
        return source;
    }

    /**
     * 返回数组长度
     *
     * @return 返回代理对象长度，若出现非正常操作导致代理对象为非数组的异常情况，会返回-1
     */
    public int length() {
        return proxy.length;
    }

    public Class<?> getComponentType() {
        return source.getClass().getComponentType();
    }


    /**
     * 尝试返回原始类型数组
     *
     * @return 如果不是原始类型或者对应的包装类数组，则返回null
     */
    public Object toPrimitives() {
        if (source != proxy) { //source和proxy不相等，说明source必须是原始类型数组，所以直接返回即可
            return source;
        } else {
            Class<?> primitiveType = ReflectClassUtil.toPrimitive(getComponentType());

            if (primitiveType != null) {
                Object primitiveArray = Array.newInstance(primitiveType, length());
                ReflectArrayUtil.arraycopy(source, 0, primitiveArray, 0, proxy.length);

                return primitiveArray;
            }
        }
        return null;
    }

    /**
     * 尝试返回原始类型对应的包装类型数组
     *
     * @return 如果不是原始类型或者对应的包装类数组，则返回null
     */
    public Object[] toWrapper() {
        if (source != proxy) { //source和proxy不相等，说明source必须是原始类型数组
            Class<?> wrapperType = ReflectClassUtil.toWrapper(getComponentType());

            if (wrapperType != null) {
                Object[] wrapperArray = (Object[]) Array.newInstance(wrapperType, length());
                ReflectArrayUtil.arraycopy(source, 0, wrapperArray, 0, proxy.length);

                return wrapperArray;
            }
        } else if (ReflectClassUtil.isWrapper(getComponentType())) {
            return (Object[]) source;
        }

        return null;
    }
}
