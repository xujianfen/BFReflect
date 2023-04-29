package blue.fen.reflect.arena;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.arena <br/>
 * 创建时间：2023/4/29 13:24 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：竞技场
 *
 * @param <P> 选手类型
 * @param <R> 裁判类型
 */
public abstract class Arena<P extends Player, R> implements IArena<P, R> {
    /**
     * 裁判
     */
    private final R referee;

    /**
     * 等待队列
     */
    private final List<P> preparePlayers;

    /**
     * 完成队列
     */
    private final List<P> completePlayers;

    /**
     * 选手演出环节是否结束
     */
    private boolean programFinish = false;

    protected Arena(R referee) {
        this.preparePlayers = new ArrayList<>();
        this.completePlayers = new ArrayList<>();
        this.referee = referee;
    }

    protected List<P> getPreparePlayers() {
        return preparePlayers;
    }

    protected List<P> getCompletePlayers() {
        return completePlayers;
    }

    protected R getReferee() {
        return referee;
    }

    public List<P> competition() {
        return competition(getPreparePlayers(), getReferee());
    }

    @Override
    public final List<P> competition(List<P> players, R referee) {
        audition(players, referee); //海选

        official(players, referee); //正式比赛，由评委负责评分

        programFinish = true; //选手表演环节结束

        return electWinner(); //统计评分结果，选出赢家
    }

    @Override
    public void dieOut(P player) {
        player.close();

        if (programFinish) {
            getCompletePlayers().remove(player);
        } else {
            getPreparePlayers().remove(player);
        }
    }

    @Override
    public void complete(P player) {
        getPreparePlayers().remove(player);
        getCompletePlayers().add(player);
    }

    @Override
    public void entry(P player) {
        getPreparePlayers().add(player);
        player.attach();
    }


    @Override
    public final void official(List<P> players, R referee) {
        int i = 0;

        int len = getPreparePlayers().size();
        while (len > 0) {
            for (int j = 0; j < len; j++) {
                P player = getPreparePlayers().get(j);
                singleCompetition(player, referee, i);

                int newLen = getPreparePlayers().size();
                if (len > newLen) {
                    len = newLen;
                    j--;
                }
            }

            i++;
        }
    }

    @Override
    public List<P> electWinner() {
        List<P> preWinners = new ArrayList<>(); //上轮评选获胜的选手

        int size = getCompletePlayers().size();
        for (int i = 0; i < size; i++) {
            P player = getCompletePlayers().get(i);

            if (preWinners.size() == 0) {
                preWinners.add(player);
            } else {
                P representWinner = preWinners.get(0);
                P winner = compare(representWinner, player);

                if (winner == null) {
                    preWinners.add(player);
                } else if (winner == representWinner) {
                    dieOut(player);
                    size--;
                    i--;
                } else {
                    for (P preWinner : preWinners) {
                        dieOut(preWinner);
                    }
                    size -= preWinners.size();
                    i -= preWinners.size();

                    preWinners.clear();
                    preWinners.add(player);
                }
            }
        }

        return preWinners;
    }
}

