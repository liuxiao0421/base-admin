package com.future.recordadmin.bill.vo;

import com.future.recordadmin.common.pojo.PageCondition;
import lombok.Data;

import java.io.Serializable;

@Data
public class BillRecordVo extends PageCondition implements Serializable {
    private String id;
    /**创建时间*/
    private String gmtCreate;
    /**修改时间*/
    private String gmtModify;
    /**应收金额*/
    private String receivableAmt;
    /**实收金额*/
    private String netReceiptsAmt;
    /**是否结束*/
    private String isEnd;
    /**用户标识*/
    private String userId;
    /**账单说明*/
    private String billDesc;
    /**付款人*/
    private String payer;
    /**项目性质*/
    private String projectNature;
}
