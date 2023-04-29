package blue.fen.reflect.arena.param;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import blue.fen.reflect.arena.Arena;
import blue.fen.reflect.param.matching.ParameterMatchingCenter;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.arena.param <br/>
 * 创建时间：2023/4/29 19:54 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：参数竞技场
 *
 * @T 竞技场的场地
 */
public abstract class ParamArena<T> extends Arena<ParamPlayer<T>, Object[]> {
    protected ParamArena(Object[] referee) {
        super(referee);
    }

    /**
     * 选手入场
     */
    protected abstract void entryOfPlayers(Class<?> clazz, String name, @Nullable Object... arguments);

    @Override
    public void singleCompetition(@NonNull ParamPlayer<T> player, @Nullable Object[] referee, int count) {
        player.perform();

        ParameterMatchingCenter.distributeWithScore(player, count);

        if (player.isDieOut()) {
            dieOut(player);
        } else if (player.isComplete()) {
            complete(player);
        }
    }

    @Override
    public void audition(List<ParamPlayer<T>> players, Object[] referee) {
        int refereeLength = referee == null ? 1 : referee.length;

        //第一轮海选，初步筛选出参数长度不合适的方法
        int size = getPreparePlayers().size();
        for (int i = 0; i < size; i++) {
            ParamPlayer<T> player = getPreparePlayers().get(i);
            int paramLength = player.getParamLength();


            if (paramLength > refereeLength + 1) {
                dieOut(player);
                size--;
                i--;
            }
        }

        //第二轮海选，淘汰非可变形参中，长度不合适的选手
        for (int i = 0; i < size; i++) {
            ParamPlayer<T> player = getPreparePlayers().get(i);

            if (player.getParamLength() != refereeLength && !player.getParamProvider().isExistVariable()) {
                //只有形参存在可变参数时才可与实参有不同长度
                dieOut(player);
                size--;
                i--;
            }
        }
    }

    @Override
    public ParamPlayer<T> compare(ParamPlayer<T> playersA, ParamPlayer<T> playersB) {
        int result = playersA.getScore() - playersB.getScore();
        return result == 0 ? null : result < 0 ? playersA : playersB;
    }

    /**
     * 在{@linkplain Arena#electWinner()}方法的基础上，把参数长度短的方法淘汰
     */
    @Override
    public List<ParamPlayer<T>> electWinner() {
        List<ParamPlayer<T>> winners = super.electWinner();

        int size = winners.size();
        int pre = -1;

        for (int i = size - 1; i >= 0; i--) {
            int len = winners.get(i).getParamProvider().getParameters().length;

            if (len < pre) {
                winners.remove(i);
            } else if (len > pre) {
                if (pre >= 0) {
                    winners.remove(i + 1);
                }

                pre = len;
            }
        }

        return winners;
    }
}
