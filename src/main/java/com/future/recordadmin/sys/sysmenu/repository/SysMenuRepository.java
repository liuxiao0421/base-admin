package com.future.recordadmin.sys.sysmenu.repository;

import com.future.recordadmin.common.repository.CommonRepository;
import com.future.recordadmin.sys.sysmenu.pojo.SysMenu;
import org.springframework.stereotype.Repository;

@Repository
public interface SysMenuRepository extends CommonRepository<SysMenu, String> {
}
