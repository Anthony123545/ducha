define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["800px","400px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'people_recall/index' + location.search,
                    add_url: 'people_recall/add',
                    edit_url: 'people_recall/edit',
                    del_url: 'people_recall/del',
                    multi_url: 'people_recall/multi',
                    import_url: 'people_recall/import',
					audit_url: 'people_recall/audit',
                    table: 'people_recall',
                }
            });

            var table = $("#table");

            // 初始化表格
            table.bootstrapTable({
                url: $.fn.bootstrapTable.defaults.extend.index_url,
                pk: 'id',
                sortName: 'id',
                columns: [
                    [
                        {checkbox: true},
                        {field: 'order_number', title: __('Id')},
                        //{field: 'people_id', title: __('People_id')},
                        {field: 'c_name', title: __('C_name'), operate: 'LIKE'},
                        {field: 'duty', title: __('部职别')},
                        {field: 'unit', title: __('单位')},
                        {field: 'post', title: __('职务')},
                        {field: 'grade', title: __('职务等级')},
                        {field: 'job', title: __('工作岗位')},
                        {field: 'skill', title: __('技术职务')},
                        {field: 'level', title: __('技术等级')},
                        {field: 'rank', title: __('军衔')},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
                                {
                                    name: 'audit',
                                    text: __('审核'),
                                    title: __('审核'),
                                    classname: 'btn btn-xs btn-primary btn-dialog',
                                    icon: 'fa fa-list',
                                    url: 'people_recall/audit',
                                    callback: function (data) {
                                        Layer.alert("接收到回传数据：" + JSON.stringify(data), {title: "回传数据"});
                                    },
                                    visible: function (row) {
                                        //返回true时按钮显示,返回false隐藏
                                        return true;
                                    }
                                }
                            ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["800px","500px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
            $("#c-people_id").data("eSelect", function(){
                Controller.api.events.getPeople();
            });
        },
        edit: function () {
            Controller.api.bindevent();
            Controller.api.events.getPeople();
            $("#c-people_id").data("eSelect", function(){
                Controller.api.events.getPeople();
                //后续操作
            });
        },
		audit: function () {
		    Controller.api.bindevent();

            $('.J-audit').click(function(){
                $('#myModal').modal('show');
                $('#type').val($(this).data('type'));
            });

            $('#J_modal_submit').click(function(){
                var data = $('#modalForm').serializeArray();
                $.get('',data,function(res){
                    if (res.code == 1){
                        $('#myModal').modal('hide')
                        Toastr.success(res.msg,function () {
                            setTimeout(function(){
                                window.location.reload();
                            },1500);
                        });
                    }else{
                        Toastr.error(res.msg);
                    }
                });
            });
		},
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            },
            events:{
                getPeople:function(){
                    $.get('People/huoq',{id:$("#c-people_id").val()},function(res){
                        for (var i in res.data){
                            $('#d-'+i).val(res.data[i]);
                            $('#d-'+i).selectPageRefresh();
                        }
                    });
                }
            }

        }
    };
    return Controller;
});