package blue.fen.reflect.param.matching.filter.node;

import blue.fen.reflect.param.model.IParamProvider;
import blue.fen.reflect.param.matching.priority.MatchingSpec;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.param.matching.base <br/>
 * 创建时间：2023/4/29 15:41 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：参数匹配节点 (根节点)
 */
public class MatchingFilterRoot extends MatchingFilterNode {
    @Override
    public int matchingOutside(IParamProvider provider, int count) {
        MatchingFilterNode son = eldestSon();

        if (son == null) {
            return MatchingSpec.MISMATCH;
        }

        do {
            int result = son.matchingOutside(provider, count);

            if (MatchingSpec.interrupt(result)) {
                return result;
            }
        } while ((son = son.next()) != null);

        return MatchingSpec.MISMATCH;
    }
}
