package blue.fen.reflect.param.matching;

import androidx.annotation.NonNull;

import blue.fen.reflect.param.matching.filter.tree.MatchingFilterTree;
import blue.fen.reflect.param.matching.priority.MatchingPriority;
import blue.fen.reflect.param.matching.priority.MatchingSpec;
import blue.fen.reflect.arena.param.ParamPlayer;

/**
 * 项目名：NotebookComponent <br/>
 * 包名：blue.fen.reflect.arena.method.scoring <br/>
 * 创建时间：2023/2/3 23:44 （星期五｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：方法的参数匹配中心
 */
public class ParameterMatchingCenter {

    /**
     * 根据形参和实参，将选手分发到相应的过滤器进行处理，根据处理结果进行评分
     *
     * @param player 方法选手
     * @param count  选择实参数组的位置
     */
    public static void distributeWithScore(@NonNull ParamPlayer<?> player, int count) {
        boolean isMatching = false;

        int result = MatchingFilterTree.getInstance().matching(player.getParamProvider(), count);

        if (MatchingSpec.isMatch(result)) {
            int filter = MatchingSpec.getFilter(result);
            int score = MatchingSpec.getScore(result);

            int priority = MatchingPriority.transformPriority(filter);

            if (priority < 0) {
                throw new IllegalArgumentException("参数过滤失败，不合法的过滤器: " + priority);
            }

            int newPriority = MatchingPriority.modifyPriority(player.getScore(), priority, score);
            player.setScore(newPriority);
            isMatching = true;
        }

        if (!isMatching) {
            player.setScore(MatchingPriority.MISMATCH);
        }
    }
}
