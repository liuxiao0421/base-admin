package com.future.recordadmin.sys.sysusermenu.repository;

import com.future.recordadmin.common.repository.CommonRepository;
import com.future.recordadmin.sys.sysusermenu.pojo.SysUserMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserMenuRepository extends CommonRepository<SysUserMenu, String> {
    List<SysUserMenu> findByUserId(String userId);
}
