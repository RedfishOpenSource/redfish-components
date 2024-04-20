package com.redfish.components.common.model.query;

/**
 * 明细查询
 */
public class SingleQuery<C extends ConditionInfo> extends Query{


    /**
     * 条件
     */
    private C conditionInfo;

    public C getConditionInfo() {
        return conditionInfo;
    }

    public SingleQuery setConditionInfo(C conditionInfo) {
        this.conditionInfo = conditionInfo;
        return this;
    }

}
