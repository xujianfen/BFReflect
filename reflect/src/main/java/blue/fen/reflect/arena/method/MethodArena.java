package blue.fen.reflect.arena.method;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import blue.fen.reflect.arena.param.ParamArena;
import blue.fen.reflect.exceptions.AmbiguousException;
import blue.fen.reflect.arena.param.ParamPlayer;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.arena <br/>
 * 创建时间：2023/4/29 19:47 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：方法竞技场
 */
public class MethodArena extends ParamArena<Method> {
    private final String name;

    protected String getName() {
        return name;
    }

    /**
     * @param clazz     调用方类型
     * @param name      方法名称
     * @param arguments 实参数组，作为竞技场的裁判传入
     */
    public MethodArena(@NonNull Class<?> clazz, @NonNull String name, @Nullable Object... arguments) {
        super(arguments);
        this.name = name;

        entryOfPlayers(clazz, name, arguments);
    }

    @Override
    protected void entryOfPlayers(Class<?> clazz, String name, @Nullable Object... arguments) {
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().equals(name)) {
                Class<?>[] getTypeParameters = method.getParameterTypes();
                entry(new ParamPlayer<>(method, getTypeParameters, arguments));
            }
        }
    }

    @NonNull
    public ParamPlayer<Method> start() throws AmbiguousException, NoSuchMethodException {
        List<ParamPlayer<Method>> winners = competition();

        if (winners.size() == 1) {
            ParamPlayer<Method> winner = winners.get(0);
            winner.getSite().setAccessible(true);

            return winner;
        } else if (winners.size() > 1) {
            StringBuilder builder = new StringBuilder();

            builder.append("方法调用不明确，参数[")
                    .append(Arrays.toString(getReferee()))
                    .append("]出现了以下匹配优先级相同的情况：\n");

            for (ParamPlayer<Method> winner : winners) {
                builder.append("匹配优先级：0x")
                        .append(Integer.toHexString(winner.getScore()))
                        .append(" - ")
                        .append(winner.getSite())
                        .append("\n");

                winner.close();
            }

            throw new AmbiguousException(builder.toString());
        } else {
            throw new NoSuchMethodException("无法解析方法" + getName() + "(" + Arrays.toString(getReferee()) + ")");
        }
    }
}
