package blue.fen.reflect.param.matching.filter.tree;

import blue.fen.reflect.param.matching.filter.node.internal.VarMatchingFilter;
import blue.fen.reflect.param.matching.filter.node.internal.WrongVarMatchingFilter;
import blue.fen.reflect.param.matching.filter.node.leaf.ArrayVariableFilter;
import blue.fen.reflect.param.matching.filter.node.leaf.ArrayWrapperFilter;
import blue.fen.reflect.param.matching.filter.node.leaf.SameMatchingFilter;
import blue.fen.reflect.param.matching.filter.node.leaf.SuperMatchingFilter;
import blue.fen.reflect.param.matching.filter.node.leaf.WrapperMatchingFilter;
import blue.fen.reflect.param.matching.filter.node.MatchingFilterInternal;
import blue.fen.reflect.param.matching.filter.node.MatchingFilterLeaf;
import blue.fen.reflect.param.matching.filter.node.MatchingFilterRoot;
import blue.fen.reflect.param.model.IParamProvider;
import blue.fen.reflect.param.matching.priority.MatchingSpec;
import blue.fen.reflect.utils.Singleton;

/**
 * 项目名：BFReflect <br/>
 * 包名：blue.fen.reflect.param.matching.filter.tree <br/>
 * 创建时间：2023/4/29 14:05 （星期六｝ <br/>
 * 作者： blue_fen
 * <p>
 * 描述：方法匹配树
 */
public class MatchingFilterTree {
    private static final Singleton<MatchingFilterTree> sInstance = new Singleton<MatchingFilterTree>() {
        @Override
        protected MatchingFilterTree create() {
            return new MatchingFilterTree();
        }
    };

    public static MatchingFilterTree getInstance() {
        return sInstance.get();
    }

    private final MatchingFilterRoot root;

    private MatchingFilterTree() {
        this.root = new MatchingFilterRoot();

        MatchingFilterInternal wrongVarMatchingFilter = new WrongVarMatchingFilter();
        MatchingFilterInternal varMatchingFilter = new VarMatchingFilter();

        MatchingFilterLeaf sameMatchingFilter = new SameMatchingFilter();
        MatchingFilterLeaf superMatchingFilter = new SuperMatchingFilter();
        MatchingFilterLeaf wrapperMatchingFilter = new WrapperMatchingFilter();
        MatchingFilterLeaf arrayVariableFilter = new ArrayVariableFilter();
        MatchingFilterLeaf arrayWrapperFilter = new ArrayWrapperFilter();

        sameMatchingFilter.setId(MatchingSpec.SAME_FILTER);
        superMatchingFilter.setId(MatchingSpec.SUPER_FILTER);
        wrapperMatchingFilter.setId(MatchingSpec.WRAPPER_FILTER);
        arrayVariableFilter.setId(MatchingSpec.ARRAY_VARIABLE_FILTER);
        arrayWrapperFilter.setId(MatchingSpec.ARRAY_WRAPPER_FILTER);

        root.add(varMatchingFilter);
        varMatchingFilter.add(arrayWrapperFilter);
        varMatchingFilter.add(arrayVariableFilter);

        root.add(wrongVarMatchingFilter);
        wrongVarMatchingFilter.add(wrapperMatchingFilter);
        wrongVarMatchingFilter.add(superMatchingFilter);
        wrongVarMatchingFilter.add(sameMatchingFilter);
    }

    public int matching(IParamProvider paramProvider, int count) {
        return root.matchingOutside(paramProvider, count);
    }
}
