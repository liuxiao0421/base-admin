// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));//main是<div id="main" style="width: 600px;height:400px;"></div>的id
// 指定图表的配置项和数据
var names=[];
var values=[];
//数据加载完之前先显示一段简单的loading动画
myChart.showLoading();
$.ajax({
    type : "post",
    async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
    url : "/courseClickCount",    //请求发送到dataActiont处
    data : {},
    dataType : "json",        //返回数据形式为json
    success : function(result) {
        //请求成功时执行该函数内容，result即为服务器返回的json对象
        if (result) {
            for(var i=0;i<result.length;i++){
                names.push(result[i].name);
                values.push(result[i].value);
            }
            myChart.hideLoading();    //隐藏加载动画
            myChart.setOption(
                {
                    title: {
                        text: '商品销量'
                    },
                    tooltip: {},
                    legend: {
                        data:['销量']
                    },
                    xAxis: {
                        data: names
                    },
                    yAxis: {},
                    series: [{
                        name: '销量',
                        type: 'bar',
                        data: values
                    }]
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