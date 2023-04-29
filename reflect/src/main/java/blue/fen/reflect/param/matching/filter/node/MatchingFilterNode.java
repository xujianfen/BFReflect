package blue.fen.reflect.param.matching.filter.node;

import androidx.annotation.Nullable;

import blue.fen.reflect.param.matching.filter.base.BaseMatchingFilter;
import blue.fen.reflect.param.matching.priority.MatchingSpec;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.param.matching.base <br/>
 * 创建时间：2023/4/29 14:03 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：参数匹配节点
 */
public class MatchingFilterNode extends BaseMatchingFilter {
    private int id = MatchingSpec.SKIP;

    @Nullable
    private MatchingFilterNode children;

    @Nullable
    private MatchingFilterNode brother;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void add(MatchingFilterNode node) {
        if (children == null) {
            children = node;
        } else {
            node.brother = children;
            children = node;
        }
    }

    public boolean intercept() {
        return false;
    }

    public boolean hashNext() {
        return brother != null;
    }

    public MatchingFilterNode next() {
        return brother;
    }

    @Nullable
    public MatchingFilterNode eldestSon() {
        return children;
    }
}
