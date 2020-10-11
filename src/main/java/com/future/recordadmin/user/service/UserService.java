package com.future.recordadmin.user.service;

import com.future.recordadmin.common.pojo.Result;
import com.future.recordadmin.sys.sysuser.vo.SysUserVo;

public interface UserService {
    Result<SysUserVo> updatePassword(String oldPassword, String newPassword);

    Result<SysUserVo> updateUser(SysUserVo sysUserVo);
}
