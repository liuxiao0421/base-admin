package com.future.recordadmin.bill.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.future.recordadmin.bill.pojo.BillRecord;
import com.future.recordadmin.bill.repository.BillRecordRepository;
import com.future.recordadmin.bill.vo.*;
import com.future.recordadmin.common.pojo.Result;
import com.future.recordadmin.common.service.CommonServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BillServiceImpl extends CommonServiceImpl<BillRecordVo, BillRecord, String> implements BillService {
    @Autowired
    private BillRecordRepository billRecordRepository;

    @Override
    public Result conditionBillRecords(BillRecordVo billRecordVo) {
        return null;
    }

    @Override
    public Result billStatistics(BillRecordVo billRecordVo) {
        StatisticsVo statisticsVo = new StatisticsVo();
        List<BillRecord> list = new ArrayList<>();
        BillStatisticsReqVo billStatisticsReqVo = new BillStatisticsReqVo();
        //1.查询结算日期为今日的未结算
        billStatisticsReqVo.setIsEnd("0");
        billStatisticsReqVo.setConditionType(0);
        billStatisticsReqVo.setAbType(1);
        billStatisticsReqVo.setDayNum(1);
        billStatisticsReqVo.setUserId(billRecordVo.getUserId());
        list = billStatistics(billStatisticsReqVo);
        TodayStatisticsVo todayStatisticsVo = new TodayStatisticsVo();
        if(CollUtil.isEmpty(list)){
            todayStatisticsVo.setCount(0);
            todayStatisticsVo.setAmount("0.00");
        }else{
            BigDecimal bigDecimal = new BigDecimal("0.00");
            for(BillRecord billRecord: list){
                bigDecimal = bigDecimal.add(new BigDecimal(billRecord.getNetReceiptsAmt()));
            }
            todayStatisticsVo.setCount(list.size());
            todayStatisticsVo.setAmount(bigDecimal.toString());
        }

        //2.查询结算日期为近7天的未结算的
        billStatisticsReqVo.setDayNum(7);
        billStatisticsReqVo.setUserId(billRecordVo.getUserId());
        list = billStatistics(billStatisticsReqVo);
        ConditionStatisticsVo conditionStatisticsVo = new ConditionStatisticsVo();
        if(CollUtil.isEmpty(list)){
            conditionStatisticsVo.setCount(0);
            conditionStatisticsVo.setAmount("0.00");
        }else{
            BigDecimal bigDecimal = new BigDecimal("0.00");
            for(BillRecord billRecord: list){
                bigDecimal = bigDecimal.add(new BigDecimal(billRecord.getNetReceiptsAmt()));
            }
            conditionStatisticsVo.setCount(list.size());
            conditionStatisticsVo.setAmount(bigDecimal.toString());
        }

        //3.查询历史未结算的
        billStatisticsReqVo.setConditionType(3);
        billStatisticsReqVo.setUserId(billRecordVo.getUserId());
        list = billStatistics(billStatisticsReqVo);
        HistoryStatisticsVo historyStatisticsVo = new HistoryStatisticsVo();
        if(CollUtil.isEmpty(list)){
            historyStatisticsVo.setCount(0);
            historyStatisticsVo.setAmount("0.00");
        }else{
            BigDecimal bigDecimal = new BigDecimal("0.00");
            for(BillRecord billRecord: list){
                bigDecimal = bigDecimal.add(new BigDecimal(billRecord.getNetReceiptsAmt()));
            }
            historyStatisticsVo.setCount(list.size());
            historyStatisticsVo.setAmount(bigDecimal.toString());
        }

        statisticsVo.setTodayStatisticsVo(todayStatisticsVo);
        statisticsVo.setConditionStatisticsVo(conditionStatisticsVo);
        statisticsVo.setHistoryStatisticsVo(historyStatisticsVo);
        return Result.of(statisticsVo);
    }

    public List<BillRecord> findSearch(BillRecordVo billRecordVo) {
        if(ObjectUtil.isEmpty(billRecordVo)){
            billRecordVo = new BillRecordVo();
        }
        BillRecord finalModel = BeanUtil.copyProperties(billRecordVo,BillRecord.class);
        List<BillRecord> result = billRecordRepository.findAll(new Specification<BillRecord>() {
            @Override
            public Predicate toPredicate(Root<BillRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if(ObjectUtil.isNotEmpty(finalModel.getId())){
                    list.add(cb.equal(root.get("id").as(String.class), finalModel.getId()));
                }
                if(ObjectUtil.isNotEmpty(finalModel.getIsEnd())){
                    list.add(cb.equal(root.get("isEnd").as(String.class), finalModel.getIsEnd()));
                }
                if(ObjectUtil.isNotEmpty(finalModel.getUserId())){
                    list.add(cb.equal(root.get("userId").as(String.class), finalModel.getUserId()));
                }
                if(ObjectUtil.isNotEmpty(finalModel.getPayer())){
                    list.add(cb.equal(root.get("payer").as(String.class), finalModel.getPayer()));
                }
                if(ObjectUtil.isNotEmpty(finalModel.getSettlementTime())){
                    list.add(cb.equal(root.get("settlementTime").as(String.class), finalModel.getSettlementTime()));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
        return result;
    }

    public List<BillRecord> billStatistics(BillStatisticsReqVo billStatisticsReqVo) {
        List<BillRecord> result = billRecordRepository.findAll(new Specification<BillRecord>() {
            @Override
            public Predicate toPredicate(Root<BillRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();

                if(3!=billStatisticsReqVo.getConditionType()){
                    String start;
                    String end;
                    Date now = new Date();
                    if(0 == billStatisticsReqVo.getAbType()){//开始时间为：当前时间前推
                        Date dateTime = DateUtil.offsetDay(now,(billStatisticsReqVo.getDayNum())*(-1)+1);
                        start = DateUtil.format(dateTime,"yyyyMMdd");//包含当天
                        end = DateUtil.format(now,"yyyyMMdd");
                    }else{//开始时间为：当前时间
                        start = DateUtil.format(now,"yyyyMMdd");
                        Date dateTime = DateUtil.offsetDay(now, billStatisticsReqVo.getDayNum() - 1);
                        end = DateUtil.format(dateTime,"yyyyMMdd");//包含当天
                    }

                    if(0 == billStatisticsReqVo.getConditionType()){
                        list.add(cb.between(root.get("settlementTime").as(String.class), start,end));
                    }else{
                        list.add(cb.between(root.get("gmtCreate").as(String.class), start,end));
                    }
                }

                if(ObjectUtil.isNotEmpty(billStatisticsReqVo.getIsEnd())){
                    list.add(cb.equal(root.get("isEnd").as(String.class), billStatisticsReqVo.getIsEnd()));
                }
                if(ObjectUtil.isNotEmpty(billStatisticsReqVo.getUserId())){
                    list.add(cb.equal(root.get("userId").as(String.class), billStatisticsReqVo.getUserId()));
                }

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        });
        return result;
    }

    @Data
    public static class BillStatisticsReqVo{
//        private String settlementTime;
//        private String gmtCreate;
        //0:settlementTime 1:gmtCreate 3:查询所有
        private int conditionType;
        //0:选定日期之前-before 1:选定日期之后-after
        private int abType;
        //天数
        private int dayNum;
        //是否结算
        private String isEnd;
        //用户标识
        private String userId;

    }
}
