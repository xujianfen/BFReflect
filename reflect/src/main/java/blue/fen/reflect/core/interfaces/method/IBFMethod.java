package blue.fen.reflect.core.interfaces.method;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Method;

import blue.fen.reflect.core.interfaces.IReflect;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.reflect.interfaces.method <br/>
 * 创建时间：2023/4/29 01:54 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：方法反射接口（抛出异常结果）
 */
public interface IBFMethod extends IReflect {
    /**
     * 执行待处理对象的方法
     *
     * @param object     待处理对象，若为 {@link Class}类型，则视为调用状态方法
     * @param name       待执行方法的名称
     * @param parameters 待执行方法的参数, 若方法形参数组最后一个元素为数组类型，可视为可变数组
     * @return 若执行成功，则返回方法执行结果；如果方法未找到或者方法调用失败则抛出异常
     */
    @Nullable
    Object invoke(Object object, String name, Object... parameters) throws Exception;

    /**
     * 方法查找
     *
     * @param clazz          反射方法的持有类
     * @param name           方法名称
     * @param parameterTypes 参数类型
     * @return 若查找成功，则返回反射方法，如果方法未找到则抛出异常
     */
    @NonNull
    Method find(Class<?> clazz, String name, Class<?>... parameterTypes) throws Exception;
}
