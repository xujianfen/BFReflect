package blue.fen.reflect.param.matching.filter.node;

import androidx.annotation.NonNull;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.param.matching.base <br/>
 * 创建时间：2023/4/29 15:57 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：参数匹配节点 (叶子节点)
 */
public abstract class MatchingFilterLeaf extends MatchingFilterNode {
    public abstract int matching(@NonNull Class<?> parameter, Class<?> argument);
}
