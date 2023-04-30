package blue.fen.reflect.arena.param;

import androidx.annotation.Nullable;

import java.lang.reflect.Method;

import blue.fen.reflect.arena.Player;
import blue.fen.reflect.param.matching.priority.PriorityProvider;
import blue.fen.reflect.param.model.ParamProvider;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.param.model <br/>
 * 创建时间：2023/4/29 19:28 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：参数选手
 */
public class ParamPlayer<T> extends Player {
    /**
     * 竞技场地点
     */
    private final T site;

    private ParamProvider paramProvider;

    public ParamProvider getParamProvider() {
        return paramProvider;
    }

    public Class<?>[] getParamTypes() {
        return paramProvider.getParameters();
    }

    public T getSite() {
        return site;
    }

    /**
     * 注意选手分数{@link Player#score}值，在该类将用 {@link PriorityProvider}来处理
     *
     * @param site       竞技场地点
     * @param paramTypes 方法的形参类型数组，该数组长度被设置为{@link Player#programNumber}
     * @param arguments  方法的实参类型数组
     */
    public ParamPlayer(T site, Class<?>[] paramTypes, @Nullable Object... arguments) {
        super(PriorityProvider.get().defaultPriority(), paramTypes.length);

        this.site = site;

        paramProvider = new ParamProvider(paramTypes, arguments);
    }

    /**
     * 是否淘汰 <br/>
     * PS: 不是最终淘汰，而是在比赛环节中，因为无法匹配，直接被淘汰；没有被淘汰的还要根据最终的分数参与筛选
     */
    public boolean isDieOut() {
        return PriorityProvider.get().isMismatch(getScore());
    }

    private Object arguments = null;

    /**
     * 为true说明调用{@link Method#invoke(Object, Object...)} 的时候，参数的形参必须为Object[]；
     * 为false说明形参应为Object
     */
    private boolean isArray;

    public boolean isArray() {
        createParamProvider();
        return isArray;
    }

    public Object getArguments() {
        createParamProvider();
        return arguments;
    }

    private void createParamProvider() {
        if (paramProvider != null) {
            isArray = getParamTypes().length == 0 || (getParamTypes().length >= 1 && !getParamTypes()[0].isArray());
            arguments = paramProvider.modifyArguments(paramProvider.getParameters(), paramProvider.getArguments());
            close();
        }
    }

    @Override
    public void close() {
        paramProvider.close();
        paramProvider = null;

        super.close();
    }
}
