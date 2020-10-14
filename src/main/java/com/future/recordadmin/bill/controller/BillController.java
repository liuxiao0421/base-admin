package com.future.recordadmin.bill.controller;

import com.future.recordadmin.annotation.Decrypt;
import com.future.recordadmin.annotation.Encrypt;
import com.future.recordadmin.bill.pojo.BillRecord;
import com.future.recordadmin.bill.service.BillService;
import com.future.recordadmin.bill.vo.BillRecordVo;
import com.future.recordadmin.common.controller.CommonController;
import com.future.recordadmin.common.pojo.Result;
import com.future.recordadmin.config.interceptor.LoginHandlerInterceptor;
import com.future.recordadmin.sys.sysuser.pojo.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill/")
@Slf4j
public class BillController extends CommonController<BillRecordVo, BillRecord, String> {
    @Autowired
    private BillService billService;

//    @RequestMapping("getAllBills")
//    public Result getAllBills(String userId){
//        BillRecordVo billRecord = new BillRecordVo();
//        billRecord.setUserId(userId);
//        return billService.page(billRecord);
//    }

    @GetMapping("getBillDetail")
    public Result getBillDetail(String billId){
        log.info("请求修改id标识为：{}",billId);
        return billService.get(billId);
    }

    @PostMapping("modifyBill")
    public Result modifyBill(@RequestBody BillRecordVo billRecord){
        return this.billService.save(billRecord);
    }

    @PostMapping("addBill")
    @Decrypt
    @Encrypt
    public Result addBill(BillRecordVo billRecord){
        SysUser sysUser = LoginHandlerInterceptor.getSysUser();
        billRecord.setUserId(sysUser.getUserId());
        return this.billService.save(billRecord);
    }

    @PostMapping("removeBill")
    public Result removeBill(@RequestBody BillRecordVo billRecord){
        log.info("删除账单请求参数：{}",billRecord);
        return this.billService.delete(billRecord.getId());
    }



}
