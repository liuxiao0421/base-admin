package com.future.recordadmin.bill.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "t_record_bill")
@Data
public class BillRecord implements Serializable {
    @Id
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
