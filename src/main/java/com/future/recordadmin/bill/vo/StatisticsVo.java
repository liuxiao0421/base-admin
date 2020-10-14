package com.future.recordadmin.bill.vo;

import lombok.Data;

/**
 * @Description: TODO
 * @ClassName: StatisticsVo
 * @Author: liu xiao
 * @Date: 2020/10/14 6:13 下午
 **/
@Data
public class StatisticsVo {
    private TodayStatisticsVo todayStatisticsVo;
    private ConditionStatisticsVo conditionStatisticsVo;
    private HistoryStatisticsVo historyStatisticsVo;
}