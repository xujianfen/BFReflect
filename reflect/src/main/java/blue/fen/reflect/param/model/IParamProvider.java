package blue.fen.reflect.param.model;

import androidx.annotation.Nullable;

import java.io.Closeable;

/**
 * 项目名：NotebookComponent <br/>
 * 包名：blue.fen.reflect.arena.method.filter.base <br/>
 * 创建时间：2023/2/4 21:26 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：
 */
public interface IParamProvider extends Closeable {

    // ---------------------- 形参 -------------------

    Class<?>[] getParameters();


    Class<?> getParameter(int count);

    // ---------------------- 实参 -------------------

    @Nullable
    Object[] getArguments();

    @Nullable
    Object getArgument(int count);

    Class<?> getArgumentType(int count);

    int argumentsLength();

    // ---------------------- 可变参数 -------------------

    /**
     * 当前匹配的参数是否是可变参数部分
     *
     * @param count 选择的参数位置
     * @return 当前待处理参数是否为可变参数
     */
    boolean isVariableParameter(int count);


    /**
     * 判断当前是否必须当成可变参数处理
     */
    boolean mustVariableHandler(int count);

    @Override
    void close();

    /**
     * 获取可变形参
     */
    Object getVarArgument(Object[] arguments);

    /**
     * 调整实参
     */
    Object modifyArguments(Class<?>[] parameters, Object[] arguments);
}
