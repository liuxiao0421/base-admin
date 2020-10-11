package com.future.recordadmin.sys.syssetting.repository;

import com.future.recordadmin.sys.syssetting.pojo.SysSetting;
import com.future.recordadmin.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysSettingRepository extends CommonRepository<SysSetting, String> {
}
