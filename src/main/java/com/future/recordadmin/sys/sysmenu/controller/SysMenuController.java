package com.future.recordadmin.sys.sysmenu.controller;

import com.future.recordadmin.annotation.Decrypt;
import com.future.recordadmin.annotation.Encrypt;
import com.future.recordadmin.common.controller.CommonController;
import com.future.recordadmin.common.pojo.Result;
import com.future.recordadmin.sys.sysmenu.pojo.SysMenu;
import com.future.recordadmin.sys.sysmenu.service.SysMenuService;
import com.future.recordadmin.sys.sysmenu.vo.SysMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/sys/sysMenu/")
public class SysMenuController extends CommonController<SysMenuVo, SysMenu, String> {
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("menu")
    public ModelAndView menu(){
        return new ModelAndView("sys/menu/menu");
    }

    /**
     * 分层级
     */
    @PostMapping("listByTier")
    @Decrypt
    @Encrypt
    public Result<List<SysMenuVo>> listByTier(SysMenuVo entityVo) {
        return sysMenuService.listByTier(entityVo);
    }
}
