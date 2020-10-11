package com.future.recordadmin.sys.sysuser.repository;

import com.future.recordadmin.common.repository.CommonRepository;
import com.future.recordadmin.sys.sysuser.pojo.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends CommonRepository<SysUser, String> {
    SysUser findByLoginName(String username);
}
