package com.future.recordadmin.bill.service;

import com.future.recordadmin.bill.pojo.BillRecord;
import com.future.recordadmin.bill.vo.BillRecordVo;
import com.future.recordadmin.common.pojo.Result;
import com.future.recordadmin.common.service.CommonService;
import com.future.recordadmin.sys.sysmenu.pojo.SysMenu;
import com.future.recordadmin.sys.sysmenu.vo.SysMenuVo;

import java.util.List;

public interface BillService extends CommonService<BillRecordVo, BillRecord, String> {
}
