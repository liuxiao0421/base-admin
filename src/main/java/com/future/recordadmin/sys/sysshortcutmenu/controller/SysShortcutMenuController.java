package com.future.recordadmin.sys.sysshortcutmenu.controller;

import com.future.recordadmin.common.controller.CommonController;
import com.future.recordadmin.sys.sysshortcutmenu.pojo.SysShortcutMenu;
import com.future.recordadmin.sys.sysshortcutmenu.service.SysShortcutMenuService;
import com.future.recordadmin.sys.sysshortcutmenu.vo.SysShortcutMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/sysShortcutMenu/")
public class SysShortcutMenuController extends CommonController<SysShortcutMenuVo, SysShortcutMenu, String> {
    @Autowired
    private SysShortcutMenuService sysShortcutMenuService;
}
