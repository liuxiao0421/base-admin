package com.future.recordadmin.bill.service;

import com.future.recordadmin.bill.pojo.BillRecord;
import com.future.recordadmin.bill.repository.BillRecordRepository;
import com.future.recordadmin.bill.vo.BillRecordVo;
import com.future.recordadmin.common.pojo.Result;
import com.future.recordadmin.common.service.CommonServiceImpl;
import com.future.recordadmin.sys.sysmenu.pojo.SysMenu;
import com.future.recordadmin.sys.sysmenu.repository.SysMenuRepository;
import com.future.recordadmin.sys.sysmenu.vo.SysMenuVo;
import com.future.recordadmin.sys.sysusermenu.service.SysUserMenuService;
import com.future.recordadmin.sys.sysusermenu.vo.SysUserMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BillServiceImpl extends CommonServiceImpl<BillRecordVo, BillRecord, String> implements BillService {
}
