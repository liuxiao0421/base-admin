package com.future.recordadmin.bill.repository;

import com.future.recordadmin.bill.pojo.BillRecord;
import com.future.recordadmin.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRecordRepository extends CommonRepository<BillRecord, String> {
}
