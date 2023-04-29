package blue.fen.reflect.utils;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.utils <br/>
 * 创建时间：2023/4/29 16:39 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：
 */
public class ReflectArrayUtil {
    public static class ArrayType {
        /**
         * 最底层元素的类型，如果不是数组则该值为类型本身
         */
        private final Class<?> componentType;

        /**
         * 维度
         */
        private final int dimension;

        public ArrayType(Class<?> componentType, int dimension) {
            this.componentType = componentType;
            this.dimension = dimension;
        }

        public Class<?> getComponentType() {
            return componentType;
        }

        public int getDimension() {
            return dimension;
        }

        public boolean sameDimension(ArrayType arrayType) {
            return dimension == arrayType.dimension;
        }

        public boolean isArray() {
            return dimension > 0;
        }
    }

    /**
     * 获取最底层元素的类型和相应的维度
     */
    public static ArrayType getComponentType(Class<?> clazz) {
        Class<?> componentType = clazz.getComponentType();

        int dimension = 0;

        while (componentType != null) {
            clazz = componentType;
            componentType = clazz.getComponentType();
            dimension++;
        }

        return new ArrayType(clazz, dimension);
    }

    public static void arraycopy(Object src, Object dest, int length) {
        arraycopy(src, 0, dest, 0, length);
    }

    public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length) {
        try {
            if (src instanceof Object[]) {
                if (arrayToPrimitiveCopy((Object[]) src, srcPos, dest, destPos, length)) {
                    return;
                }
            } else if (dest instanceof Object[]) {
                if (arrayToObjectCopy(src, srcPos, (Object[]) dest, destPos, length)) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.arraycopy(src, srcPos, dest, destPos, length);
    }

    private static boolean arrayToPrimitiveCopy(Object[] src, int srcPos, Object dest, int destPos, int length) {
        if (dest instanceof boolean[]) {
            toPrimitives(src, srcPos, (boolean[]) dest, destPos, length);
            return true;
        }

        if (dest instanceof byte[]) {
            toPrimitives(src, srcPos, (byte[]) dest, destPos, length);
            return true;
        }

        if (dest instanceof char[]) {
            toPrimitives(src, srcPos, (char[]) dest, destPos, length);
            return true;
        }

        if (dest instanceof short[]) {
            toPrimitives(src, srcPos, (short[]) dest, destPos, length);
            return true;
        }

        if (dest instanceof int[]) {
            toPrimitives(src, srcPos, (int[]) dest, destPos, length);
            return true;
        }

        if (dest instanceof long[]) {
            toPrimitives(src, srcPos, (long[]) dest, destPos, length);
            return true;
        }

        if (dest instanceof double[]) {
            toPrimitives(src, srcPos, (double[]) dest, destPos, length);
            return true;
        }

        if (dest instanceof float[]) {
            toPrimitives(src, srcPos, (float[]) dest, destPos, length);
            return true;
        }

        return false;
    }

    private static void toPrimitives(Object[] src, int srcPos, byte[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = ((Byte) src[srcPos + i]).byteValue();
        }
    }

    private static void toPrimitives(Object[] src, int srcPos, boolean[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = ((Boolean) src[srcPos + i]).booleanValue();
        }
    }

    private static void toPrimitives(Object[] src, int srcPos, char[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = ((Character) src[srcPos + i]).charValue();
        }
    }

    private static void toPrimitives(Object[] src, int srcPos, short[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = ((Short) src[srcPos + i]).shortValue();
        }
    }

    private static void toPrimitives(Object[] src, int srcPos, int[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = ((Integer) src[srcPos + i]).intValue();
        }
    }

    private static void toPrimitives(Object[] src, int srcPos, long[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = ((Long) src[srcPos + i]).longValue();
        }
    }

    private static void toPrimitives(Object[] src, int srcPos, double[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = ((Double) src[srcPos + i]).doubleValue();
        }
    }

    private static void toPrimitives(Object[] src, int srcPos, float[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = ((Float) src[srcPos + i]).floatValue();
        }
    }

    public static Class<?> getElementType(Object array, int count) {
        if (array instanceof boolean[]) {
            return boolean.class;
        } else if (array instanceof byte[]) {
            return byte.class;
        } else if (array instanceof char[]) {
            return char.class;
        } else if (array instanceof short[]) {
            return short.class;
        } else if (array instanceof int[]) {
            return int.class;
        } else if (array instanceof long[]) {
            return long.class;
        } else if (array instanceof double[]) {
            return double.class;
        } else if (array instanceof float[]) {
            return float.class;
        } else if (array.getClass().isArray()) {
            return array.getClass().getComponentType();
        }

        return null;
    }

    public static void setElement(Object array, int count, Object element) {
        if (!array.getClass().isArray() || getLength(array) <= count) {
            return;
        }

        if (array instanceof boolean[]) {
            ((boolean[]) array)[count] = ((Boolean) element).booleanValue();
        } else if (array instanceof byte[]) {
            ((byte[]) array)[count] = ((Byte) element).byteValue();
        } else if (array instanceof char[]) {
            ((char[]) array)[count] = ((Character) element).charValue();
        } else if (array instanceof short[]) {
            ((short[]) array)[count] = ((Short) element).shortValue();
        } else if (array instanceof int[]) {
            ((int[]) array)[count] = ((Integer) element).intValue();
        } else if (array instanceof long[]) {
            ((long[]) array)[count] = ((Long) element).longValue();
        } else if (array instanceof double[]) {
            ((double[]) array)[count] = ((Double) element).doubleValue();
        } else if (array instanceof float[]) {
            ((float[]) array)[count] = ((Float) element).floatValue();
        } else if (array instanceof Object[]) {
            ((Object[]) array)[count] = element;
        }
    }


    private static boolean arrayToObjectCopy(Object src, int srcPos, Object[] dest, int destPos, int length) {
        if (dest instanceof Boolean[]) {
            toObject((boolean[]) src, srcPos, (Boolean[]) dest, destPos, length);
            return true;
        }

        if (src instanceof byte[]) {
            toObject((byte[]) src, srcPos, (Byte[]) dest, destPos, length);
            return true;
        }

        if (src instanceof char[]) {
            toObject((char[]) src, srcPos, (Character[]) dest, destPos, length);
            return true;
        }

        if (src instanceof short[]) {
            toObject((short[]) src, srcPos, (Short[]) dest, destPos, length);
            return true;
        }

        if (src instanceof int[]) {
            toObject((int[]) src, srcPos, (Integer[]) dest, destPos, length);
            return true;
        }

        if (src instanceof long[]) {
            toObject((long[]) src, srcPos, (Long[]) dest, destPos, length);
            return true;
        }

        if (src instanceof double[]) {
            toObject((double[]) src, srcPos, (Double[]) dest, destPos, length);
            return true;
        }

        if (src instanceof float[]) {
            toObject((float[]) src, srcPos, (Float[]) dest, destPos, length);
            return true;
        }

        return false;
    }

    private static void toObject(byte[] src, int srcPos, Byte[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = Byte.valueOf(src[srcPos + i]);
        }
    }

    private static void toObject(boolean[] src, int srcPos, Boolean[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = (src[srcPos + i] ? Boolean.TRUE : Boolean.FALSE);
        }
    }

    private static void toObject(char[] src, int srcPos, Character[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = Character.valueOf(src[srcPos + i]);
        }
    }

    private static void toObject(short[] src, int srcPos, Short[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = Short.valueOf(src[srcPos + i]);
        }
    }

    private static void toObject(int[] src, int srcPos, Integer[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = Integer.valueOf(src[srcPos + i]);
        }
    }

    private static void toObject(long[] src, int srcPos, Long[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = Long.valueOf(src[srcPos + i]);
        }
    }

    private static void toObject(double[] src, int srcPos, Double[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = Double.valueOf(src[srcPos + i]);
        }
    }

    private static void toObject(float[] src, int srcPos, Float[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = Float.valueOf(src[srcPos + i]);
        }
    }

    public static int getLength(Object array) {
        if (array instanceof boolean[]) {
            return ((boolean[]) array).length;
        }

        if (array instanceof byte[]) {
            return ((byte[]) array).length;
        }

        if (array instanceof char[]) {
            return ((char[]) array).length;
        }

        if (array instanceof short[]) {
            return ((short[]) array).length;
        }

        if (array instanceof int[]) {
            return ((int[]) array).length;
        }

        if (array instanceof long[]) {
            return ((long[]) array).length;
        }

        if (array instanceof double[]) {
            return ((double[]) array).length;
        }

        if (array instanceof float[]) {
            return ((float[]) array).length;
        }

        if (array instanceof Object[]) {
            return ((Object[]) array).length;
        }

        return -1;
    }
}
