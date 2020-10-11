package com.future.recordadmin.sys.sysshortcutmenu.repository;

import com.future.recordadmin.common.repository.CommonRepository;
import com.future.recordadmin.sys.sysshortcutmenu.pojo.SysShortcutMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysShortcutMenuRepository extends CommonRepository<SysShortcutMenu, String> {
    List<SysShortcutMenu> findByUserId(String userId);
}
