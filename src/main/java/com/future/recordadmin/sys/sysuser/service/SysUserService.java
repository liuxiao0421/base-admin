package com.future.recordadmin.sys.sysuser.service;

import com.future.recordadmin.common.pojo.Result;
import com.future.recordadmin.common.service.CommonService;
import com.future.recordadmin.sys.sysuser.pojo.SysUser;
import com.future.recordadmin.sys.sysuser.vo.SysUserVo;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public interface SysUserService extends CommonService<SysUserVo, SysUser, String> {
    Result<SysUserVo> findByLoginName(String username);
    Result<SysUserVo> resetPassword(String userId);
    PersistentTokenRepository getPersistentTokenRepository2();
}
