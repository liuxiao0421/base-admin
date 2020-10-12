let tableIns;
let tree;
layui.use(['element', 'form', 'table', 'layer', 'laydate', 'tree', 'util'], function () {
    let table = layui.table;
    let form = layui.form;//select、单选、复选等依赖form
    let element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
    let laydate = layui.laydate;
    tree = layui.tree;
    let height = document.documentElement.clientHeight - 160;

    //用户列表
    tableIns = table.render({
        elem: '#billRecordsTable'
        , url: ctx + '/bill/page'
        , method: 'POST'
        //请求前参数处理
        , request: {
            pageName: 'page' //页码的参数名称，默认：page
            , limitName: 'rows' //每页数据量的参数名，默认：limit
        }
        , response: {
            statusName: 'flag' //规定数据状态的字段名称，默认：code
            , statusCode: true //规定成功的状态码，默认：0
            , msgName: 'msg' //规定状态信息的字段名称，默认：msg
            , countName: 'records' //规定数据总数的字段名称，默认：count
            , dataName: 'rows' //规定数据列表的字段名称，默认：data
        }
        //响应后数据处理
        , parseData: function (res) { //res 即为原始返回的数据
            var data = res.data;
            return {
                "flag": res.flag, //解析接口状态
                "msg": res.msg, //解析提示文本
                "records": data.records, //解析数据长度
                "rows": data.rows //解析数据列表
            };
        }
        , toolbar: '#billRecordsTableToolbar'
        , title: '账单列表'
        , cols: [[
            {field: 'id', title: 'ID', hide: true}
            , {field: 'payer', title: '客户名称'}
            // , {field: 'billDesc', title: '账单描述', hide: true}
            , {field: 'gmtCreate', title: '创建时间'}
            , {field: 'gmtModify', title: '修改时间'}
            , {field: 'receivableAmt', title: '应收(元)'}
            , {field: 'netReceiptsAmt', title: '实收(元)'}
            , {field: 'isEnd', title: '是否结清',templet:"#stateBar"}
            , {fixed: 'right', title: '操作',minWidth: 140, toolbar: '#billRecordsTableBar'}
        ]]
        , defaultToolbar: ['', '', '']
        , page: true
        , height: height
        , cellMinWidth: 80
    });

    //头工具栏事件
    table.on('toolbar(billTable)', function (obj) {
        switch (obj.event) {
            case 'addData':
                //重置操作表单
                $("#billForm")[0].reset();
                form.render();
                layer.msg("请填写右边的表单并保存！");
                break;
            case 'query':
                let payer = $("#payer").val()==""?null:$("#payer").val();
                let gmtCreate = $('#create').val()==""?null:$('#create').val();
                let query = {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , done: function (res, curr, count) {
                        //完成后重置where，解决下一次请求携带旧数据
                        // this.where = {};
                    }
                };
                //设定异步数据接口的额外参数
                query.where = {payer: payer,gmtCreate: gmtCreate};
                tableIns.reload(query);
                laydate.render({
                    elem: '#create',
                    format: "yyyyMMdd"
                });
                $("#payer").val(payer);
                $('#create').val(gmtCreate);
                break;
            case 'reload':
                let queryReload = {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , done: function (res, curr, count) {
                        //完成后重置where，解决下一次请求携带旧数据
                        this.where = {};
                    }
                };
                //设定异步数据接口的额外参数
                tableIns.reload(queryReload);
                laydate.render({
                    elem: '#create',
                    format: "yyyyMMdd"
                });
                break;
        }
    });

    //监听行工具事件
    table.on('tool(billTable)', function (obj) {
        let data = obj.data;
        //删除
        if (obj.event === 'del') {
            layer.confirm('确认删除吗？', function (index) {
                //向服务端发送删除指令
                $.delete(ctx + "/bill/delete/" + data.id, {}, function (data) {
                    tableIns.reload();
                    layer.close(index);
                })
            });
        }
        //编辑
        else if (obj.event === 'edit') {
            //回显操作表单
            $("#billForm").form(data);
            $("input[name=isEnd][value='0']").prop("checked", data.isEnd == '0' ? 'checked' : false);
            $("input[name=isEnd][value='1']").prop("checked", data.isEnd == '1' ? true : false);
            form.render();
        }
    });

    //日期选择器
    laydate.render({
        elem: '#gmtCreate',
        format: "yyyyMMdd"
    });
    laydate.render({
        elem: '#create',
        format: "yyyyMMdd"
    });
});

/**
 * 提交保存
 */
function billFormSave() {
    let billForm = $("#billForm").serializeObject();
    $.post(ctx + "/bill/save", billForm, function (data) {
        if(!data.flag){
            layer.msg(data.msg, {icon: 2,time: 2000}, function () {});
            return;
        }
        layer.msg("保存成功", {icon: 1, time: 2000}, function () {});
        tableIns.reload();
    });
}

