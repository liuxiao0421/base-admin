package com.future.recordadmin.sys.sysusermenu.service;

import com.future.recordadmin.common.pojo.Result;
import com.future.recordadmin.common.service.CommonService;
import com.future.recordadmin.sys.sysmenu.vo.SysMenuVo;
import com.future.recordadmin.sys.sysusermenu.pojo.SysUserMenu;
import com.future.recordadmin.sys.sysusermenu.vo.SysUserMenuVo;

import java.util.List;

public interface SysUserMenuService extends CommonService<SysUserMenuVo, SysUserMenu, String> {
    Result<List<SysMenuVo>> findByUserId(String userId);

    Result<Boolean> saveAllByUserId(String userId, String menuIdList);
}
