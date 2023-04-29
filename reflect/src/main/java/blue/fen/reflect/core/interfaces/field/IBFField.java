package blue.fen.reflect.core.interfaces.field;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;

import blue.fen.reflect.core.interfaces.IReflect;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.reflect <br/>
 * 创建时间：2023/4/29 01:29 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：属性反射接口 (抛出异常结果)
 */
public interface IBFField extends IReflect {
    /**
     * 修改待处理对象的属性内容
     *
     * @param object 待处理对象，若为 {@link Class}类型，则视为调用状态字段
     * @param name   待修改字段的名称
     * @param value  要修改新值
     * @throws Exception 修改失败时抛出异常
     */
    void set(Object object, String name, Object value) throws Exception;

    /**
     * 获取待处理对象的属性内容
     *
     * @param object 待处理对象，若为 {@link Class}类型，则视为调用状态字段
     * @param name   待获取字段的名称
     * @return 若获取成功，则返回字段的内容；如果字段未找到或者字段获取失败则抛出异常
     */
    @Nullable
    Object get(Object object, String name) throws Exception;

    /**
     * 属性查找
     *
     * @param clazz 待反射属性的持有类
     * @param name  查找字段的名称
     * @return 查找成功返回字段类型，否则抛出异常
     */
    @NonNull
    Field find(Class<?> clazz, String name) throws Exception;
}
