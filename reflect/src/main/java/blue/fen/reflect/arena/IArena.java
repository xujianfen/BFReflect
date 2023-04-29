package blue.fen.reflect.arena;

import java.util.List;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.arena <br/>
 * 创建时间：2023/4/29 13:24 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：竞技场接口
 *
 * @param <P> 选手类型
 * @param <R> 裁判类型
 */
interface IArena<P extends Player, R> {
    /**
     * 淘汰
     *
     * @param player 选手
     */
    void dieOut(P player);

    /**
     * 完成比赛
     *
     * @param player 选手
     */
    void complete(P player);

    /**
     * 选手入场
     *
     * @param player 选手
     */
    void entry(P player);

    /**
     * 比赛
     *
     * @param players 选手集合
     * @param referee 裁判
     * @return 赢家集合
     */
    List<P> competition(List<P> players, R referee);


    /**
     * 单轮比赛
     *
     * @param player  参加该比赛的选手
     * @param referee 裁判
     * @param count   第几轮比赛
     */
    void singleCompetition(P player, R referee, int count);

    /**
     * 海选
     *
     * @param players 选手集合
     * @param referee 裁判
     */
    void audition(List<P> players, R referee);

    /**
     * 选手A是否可以胜过选手B
     *
     * @param playersA 选手A
     * @param playersB 选手B
     * @return 返回获胜者，若打平，说明没有获胜者，返回null，
     */
    P compare(P playersA, P playersB);

    /**
     * 正式比赛
     *
     * @param players 选手集合
     * @param referee 裁判
     */
    void official(List<P> players, R referee);

    /**
     * 选出赢家
     *
     * @return 因为可能存在并列获胜，所以这里是赢家集合
     */
    List<P> electWinner();

    class Result<P extends Player> {
        public final List<P> winners;

        public Result(List<P> winners) {
            this.winners = winners;
        }
    }
}

