package blue.fen.reflect.core.interfaces.field;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;

import blue.fen.reflect.result.IReflectResult;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.reflect.interfaces.field <br/>
 * 创建时间：2023/4/29 02:06 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：属性反射接口（对异常信息进行了包装）
 */
public interface IBFFieldX {
    /**
     * 修改待处理对象的属性内容
     *
     * @param object 待处理对象，若为 {@link Class}类型，则视为调用状态字段
     * @param name   待修改字段的名称
     * @param value  要修改新值
     * @return 修改结果
     */
    IReflectResult<Void> setX(Object object, String name, Object value);

    /**
     * 获取待处理对象的属性内容
     *
     * @param object 待处理对象，若为 {@link Class}类型，则视为调用状态字段
     * @param name   待获取字段的名称
     * @return 获取结果
     */
    @Nullable
    IReflectResult<Object> getX(Object object, String name);

    /**
     * 查找待处理对象的属性
     *
     * @param clazz 待反射属性的持有类
     * @param name  查找字段的名称
     * @return 查询结果
     */
    @NonNull
    IReflectResult<Field> findX(Class<?> clazz, String name);
}
