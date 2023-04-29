package blue.fen.reflect.param.matching.filter.node;

import androidx.annotation.NonNull;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.param.matching.base <br/>
 * 创建时间：2023/4/29 15:58 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：参数匹配节点 (内部节点/非叶子节点)
 */
public class MatchingFilterInternal extends MatchingFilterNode {
    @Override
    public int getId() {
        return eldestSon().getId();
    }

    @NonNull
    public MatchingFilterNode eldestSon() {
        assert super.eldestSon() != null;
        return super.eldestSon();
    }
}
