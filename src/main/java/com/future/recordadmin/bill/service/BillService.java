package com.future.recordadmin.bill.service;

import com.future.recordadmin.bill.pojo.BillRecord;
import com.future.recordadmin.bill.vo.BillRecordVo;
import com.future.recordadmin.common.pojo.PageInfo;
import com.future.recordadmin.common.pojo.Result;
import com.future.recordadmin.common.service.CommonService;
import com.future.recordadmin.sys.sysmenu.pojo.SysMenu;
import com.future.recordadmin.sys.sysmenu.vo.SysMenuVo;

import java.util.List;

public interface BillService extends CommonService<BillRecordVo, BillRecord, String> {
    /**根据条件统计账单*/
    Result conditionBillRecords(BillRecordVo billRecordVo);

    Result billStatistics(BillRecordVo billRecordVo);

    Result<PageInfo<BillRecordVo>> pageByCondition(BillRecordVo entityVo);
}
