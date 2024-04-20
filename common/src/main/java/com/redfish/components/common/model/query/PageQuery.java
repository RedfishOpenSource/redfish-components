package com.redfish.components.common.model.query;


/**
 * 分页查询
 *
 * @param <S>
 * @param <C>
 */
public class PageQuery<S extends SortInfo,C extends ConditionInfo> extends Query {

    /**
     * 分页信息
     */
    private PageInfo pageInfo;

    private boolean needTotalCount = true;


    /**
     * 排序信息
     */
    private S sortInfo;


    /**
     * 条件
     */
    private C conditionInfo;


    public PageInfo gePageInfo() {
        return pageInfo;
    }

    public PageQuery sePageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
        return this;
    }

    public boolean isNeedTotalCount() {
        return needTotalCount;
    }

    public PageQuery setNeedTotalCount(boolean needTotalCount) {
        this.needTotalCount = needTotalCount;
        return this;
    }

    public S getSortInfo() {
        return sortInfo;
    }

    public PageQuery setSortInfo(S sortInfo) {
        this.sortInfo = sortInfo;
        return this;
    }

    public C getConditionInfo() {
        return conditionInfo;
    }

    public PageQuery setConditionInfo(C conditionInfo) {
        this.conditionInfo = conditionInfo;
        return this;
    }

}
