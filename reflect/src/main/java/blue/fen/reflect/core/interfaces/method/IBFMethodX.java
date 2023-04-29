package blue.fen.reflect.core.interfaces.method;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Method;

import blue.fen.reflect.result.IReflectResult;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.reflect <br/>
 * 创建时间：2023/4/29 01:34 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：方法反射接口（对异常信息进行了包装）
 */
public interface IBFMethodX {
    /**
     * 执行待处理对象的方法
     *
     * @param object     待处理对象，若为 {@link Class}类型，则视为调用状态方法
     * @param name       待执行方法的名称
     * @param parameters 待执行方法的参数, 若方法形参数组最后一个元素为数组类型，可视为可变数组
     * @return 执行结果
     */
    @Nullable
    IReflectResult<Object> invokeX(Object object, String name, Object... parameters);

    /**
     * 方法查找
     *
     * @param clazz          反射方法的持有类
     * @param name           方法名称
     * @param parameterTypes 参数类型
     * @return 查询结果
     */
    @NonNull
    IReflectResult<Method> findX(Class<?> clazz, String name, Class<?>... parameterTypes);
}
