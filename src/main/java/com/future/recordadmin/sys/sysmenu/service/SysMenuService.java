package com.future.recordadmin.sys.sysmenu.service;

import com.future.recordadmin.common.pojo.Result;
import com.future.recordadmin.common.service.CommonService;
import com.future.recordadmin.sys.sysmenu.pojo.SysMenu;
import com.future.recordadmin.sys.sysmenu.vo.SysMenuVo;

import java.util.List;

public interface SysMenuService extends CommonService<SysMenuVo, SysMenu, String> {
    Result<List<SysMenuVo>> listByTier(SysMenuVo entityVo);
}
