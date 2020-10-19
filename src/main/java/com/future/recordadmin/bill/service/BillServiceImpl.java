package com.future.recordadmin.bill.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.future.recordadmin.bill.pojo.BillRecord;
import com.future.recordadmin.bill.repository.BillRecordRepository;
import com.future.recordadmin.bill.vo.*;
import com.future.recordadmin.common.pojo.PageCondition;
import com.future.recordadmin.common.pojo.PageInfo;
import com.future.recordadmin.common.pojo.Result;
import com.future.recordadmin.common.service.CommonServiceImpl;
import com.future.recordadmin.util.CopyUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.criteria.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
                bigDecimal = bigDecimal.add(new BigDecimal(billRecord.getReceivableAmt()));
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
                bigDecimal = bigDecimal.add(new BigDecimal(billRecord.getReceivableAmt()));
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
                bigDecimal = bigDecimal.add(new BigDecimal(billRecord.getReceivableAmt()));
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

    private Class<BillRecordVo> entityVoClass;//实体类Vo
    public BillServiceImpl() {
        Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        this.entityVoClass = (Class<BillRecordVo>) types[0];
    }

    @Override
    public Result<PageInfo<BillRecordVo>> pageByCondition(BillRecordVo entityVo) {
        Page<BillRecord> page = findPageByCondition(entityVo);
        return Result.of(PageInfo.of(page, entityVoClass));
    }

    public Page<BillRecord> findPageByCondition(BillRecordVo billRecordVo) {
        PageCondition pageCondition = billRecordVo;
        return billRecordRepository.findAll(new Specification<BillRecord>() {
            @Override
            public Predicate toPredicate(Root<BillRecord> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                /** 可添加你的其他搜索过滤条件 默认已有创建时间过滤 **/
                Path<String> gmtCreateField=root.get("gmtCreate");

                List<Predicate> list = new ArrayList<>();

                /** 创建时间 **/
                if(ObjectUtil.isNotEmpty(billRecordVo.getId())){
                    list.add(cb.equal(root.get("id").as(String.class), billRecordVo.getId()));
                }
                if(ObjectUtil.isNotEmpty(billRecordVo.getIsEnd())){
                    list.add(cb.equal(root.get("isEnd").as(String.class), billRecordVo.getIsEnd()));
                }
                if(ObjectUtil.isNotEmpty(billRecordVo.getUserId())){
                    list.add(cb.equal(root.get("userId").as(String.class), billRecordVo.getUserId()));
                }
                if(ObjectUtil.isNotEmpty(billRecordVo.getPayer())){
                    list.add(cb.equal(root.get("payer").as(String.class), billRecordVo.getPayer()));
                }
                String start;
                String end;
                if(ObjectUtil.isNotEmpty(billRecordVo.getGmtCreateBegin())||ObjectUtil.isNotEmpty(billRecordVo.getGmtCreateEnd())){
                    start = billRecordVo.getGmtCreateBegin();
                    end = billRecordVo.getGmtCreateEnd();
                    if(StrUtil.isEmpty(start)){
                        start = DateUtil.format(new Date(),"yyyyMMdd");
                    }
                    if(StrUtil.isEmpty(end)){
                        end = DateUtil.format(new Date(),"yyyyMMdd");
                    }
                    list.add(cb.between(gmtCreateField.as(String.class), start,end));
                }
                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageCondition.getPageable());
    }
}
