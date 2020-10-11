package com.future.recordadmin.sys.syssetting.controller;

import com.future.recordadmin.common.controller.CommonController;
import com.future.recordadmin.sys.syssetting.pojo.SysSetting;
import com.future.recordadmin.sys.syssetting.service.SysSettingService;
import com.future.recordadmin.sys.syssetting.vo.SysSettingVo;
import com.future.recordadmin.util.SysSettingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/sys/sysSetting/")
public class SysSettingController extends CommonController<SysSettingVo, SysSetting, String> {
    @Autowired
    private SysSettingService sysSettingService;

    @GetMapping("setting")
    public ModelAndView setting() {
        return new ModelAndView("sys/setting/setting", "sys", SysSettingUtil.getSysSetting());
    }
}
