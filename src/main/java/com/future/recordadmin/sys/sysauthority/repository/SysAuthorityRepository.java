package com.future.recordadmin.sys.sysauthority.repository;

import com.future.recordadmin.sys.sysauthority.pojo.SysAuthority;
import com.future.recordadmin.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysAuthorityRepository extends CommonRepository<SysAuthority, String> {
}
