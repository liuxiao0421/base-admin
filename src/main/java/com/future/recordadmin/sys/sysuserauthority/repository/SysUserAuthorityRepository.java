package com.future.recordadmin.sys.sysuserauthority.repository;

import com.future.recordadmin.common.repository.CommonRepository;
import com.future.recordadmin.sys.sysuserauthority.pojo.SysUserAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserAuthorityRepository extends CommonRepository<SysUserAuthority, String> {
    List<SysUserAuthority> findByUserId(String userId);
}
