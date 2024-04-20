package com.redfish.components.common.model.query;

/**
 * 列表查询
 *
 * @param <S>
 * @param <C>
 */
public class ListQuery<S extends SortInfo,C extends ConditionInfo> extends Query{

    /**
     * 排序信息
     */
    private S sortInfo;


    /**
     * 条件
     */
    private C conditionInfo;

    public S getSortInfo() {
        return sortInfo;
    }

    public ListQuery setSortInfo(S sortInfo) {
        this.sortInfo = sortInfo;
        return this;
    }

    public C getConditionInfo() {
        return conditionInfo;
    }

    public ListQuery setConditionInfo(C conditionInfo) {
        this.conditionInfo = conditionInfo;
        return this;
    }

}
