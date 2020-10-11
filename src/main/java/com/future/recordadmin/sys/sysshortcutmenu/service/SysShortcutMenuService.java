package com.future.recordadmin.sys.sysshortcutmenu.service;

import com.future.recordadmin.common.pojo.Result;
import com.future.recordadmin.common.service.CommonService;
import com.future.recordadmin.sys.sysshortcutmenu.pojo.SysShortcutMenu;
import com.future.recordadmin.sys.sysshortcutmenu.vo.SysShortcutMenuVo;

import java.util.List;

public interface SysShortcutMenuService extends CommonService<SysShortcutMenuVo, SysShortcutMenu, String> {
    Result<List<SysShortcutMenuVo>> findByUserId(String userId);
}
