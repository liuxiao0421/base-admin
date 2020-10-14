// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('EchartZhu'));//main是<div id="main" style="width: 600px;height:400px;"></div>的id
// 指定图表的配置项和数据
var names=[];
var values=[];//金额
var values2=[];//笔数
//数据加载完之前先显示一段简单的loading动画
myChart.showLoading();
$.ajax({
    type : "post",
    async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
    url : ctx + "/bill/historyBillStatistics",    //请求发送到dataActiont处
    data : {},
    dataType : "json",        //返回数据形式为json
    success : function(result) {
        //请求成功时执行该函数内容，result即为服务器返回的json对象
        console.log('amount:'+result.data.historyStatisticsVo.amount);
        console.log('count:'+result.data.historyStatisticsVo.count);
        if (result) {
            let statisticsVo = result.data;
            names=['今天','近七天','历史'];
            values = [statisticsVo.todayStatisticsVo.amount,statisticsVo.conditionStatisticsVo.amount,statisticsVo.historyStatisticsVo.amount];
            values2 = [statisticsVo.todayStatisticsVo.count,statisticsVo.conditionStatisticsVo.count,statisticsVo.historyStatisticsVo.count];
            myChart.hideLoading();    //隐藏加载动画
            myChart.setOption(
                {
                    title: {
                        // text: '待结算',
                        subtext: '仅供参考'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        name: ['总金额','总笔数']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    xAxis: {
                        type : 'category',
                        data: names
                    },
                    yAxis: {
                        show: true,  // 是否显示
                        position: 'left', // y轴位置
                        offset: 0, // y轴相对于默认位置的偏移
                        type: 'value',  // 轴类型，默认为 ‘category’
                        name: '金额',   // 轴名称
                        nameLocation: 'end', // 轴名称相对位置value
                        nameTextStyle: {    // 坐标轴名称样式
                            color: '#fff',
                            padding: [5, 0, 0, 5]  // 坐标轴名称相对位置
                        },
                        nameGap: 15, // 坐标轴名称与轴线之间的距离
                        nameRotate: 270,  // 坐标轴名字旋转

                        axisLine: {    // 坐标轴 轴线
                            show: true,  // 是否显示
                            //  -----   箭头 -----
                            symbol: ['none', 'arrow'],  // 是否显示轴线箭头
                            symbolSize: [8, 8],  // 箭头大小
                            symbolOffset: [0, 7], // 箭头位置

                            // ----- 线 -------
                            lineStyle: {
                                color: 'red',
                                width: 1,
                                type: 'solid'
                            }
                        },
                        axisTick: {      // 坐标轴的刻度
                            show: true,    // 是否显示
                            inside: true,  // 是否朝内
                            length: 3,      // 长度
                            lineStyle: {
                                color: 'red',  // 默认取轴线的颜色
                                width: 1,
                                type: 'solid'
                            }
                        },
                        axisLabel: {      // 坐标轴的标签
                            show: true,    // 是否显示
                            inside: false,  // 是否朝内
                            rotate: 0,     // 旋转角度
                            margin: 8,     // 刻度标签与轴线之间的距离
                            color: 'red',  // 默认轴线的颜色
                        },
                        splitLine: {    // gird 区域中的分割线
                            show: true,   // 是否显示
                            lineStyle: {
                                color: '#666',
                                width: 1,
                                type: 'dashed'
                            }
                        },
                        splitArea: {     // 网格区域
                            show: false   // 是否显示，默认为false
                        }
                    },
                    series: [
                        {name: '金额(元)',type: 'bar',data: values},
                        {name: '数量(笔)',type: 'bar',data: values2,color: '#009999'},

                    ]
                }
            );
        }
    },
    error : function(errorMsg) {
        //请求失败时执行该函数
        alert("图表请求数据失败!");
        myChart.hideLoading();
    }
});//end ajax
