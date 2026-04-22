define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'second/index' + location.search,
                    add_url: 'second/add',
                    //edit_url: 'second/edit',
                    del_url: 'second/del',
                    multi_url: 'second/multi',
                    import_url: 'second/import',
                    table: 'second',
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
                        {field: 'order_number', title: __('序号')},
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'unit_id_text', title: __('Unit_id')},
                        {field: 'unit_id1', title: __('Unit_id1'), operate: 'LIKE'},
                        {field: 'start_time', title: __('Start_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'end_time', title: __('End_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'contents', title: __('Contents'), operate: 'LIKE'},
                        {field: 'duty', title: __('Duty'), operate: 'LIKE'},
                        {field: 'rank_id_text', title: __('Post_id')},
                        {field: 'level_id_text', title: __('Level_id')},
                        {field: 'createtime', title: __('Createtime'), formatter: Table.api.formatter.datetime},
                        {field: 'admin_id_text', title: __('创建人')},
                        {field: 'rejoin', title: __('状态'), searchList: {"0":__('未归队'),"1":__('已归队')}, formatter: Table.api.formatter.normal},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
                                {
                                    name: '归队',
                                    text: '归队',
                                    title: '归队',
                                    confirm: '确定归队吗？',
                                    classname: 'btn btn-xs btn-info btn-ajax',
                                    icon: 'fa fa-calendar-check-o',
                                    url: 'second/rejoin',
                                    visible:function(row){
                                        if(row.rejoin == 0){
                                            return true;
                                        }
                                        return false;
                                    },
                                    refresh: true
                                }
                            ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
        },
        add: function () {
            Controller.api.bindevent();
            $("#c-people_id").data("eSelect", function(){
				$.get('second/huoq',{id:$("#c-people_id").val()},function(res){
					$('#rank_id').val(res.data.rank_id);
					$('#level_id').val(res.data.level_id);
					$('#duty').val(res.data.duty);
				//console.log(res);
				});
			    //后续操作
			});
			$("#c-people_id").data("params", function (obj) {
                var unit_id = $('#J_unitselect').val();

                return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
                // return {custom: {unit_id: $("#J_unitselect").val()}};
            });
        },
//         edit: function () {
//             Controller.api.bindevent();
//             $("#c-people_id").data("eSelect", function(){
// 				$.get('second/huoq',{id:$("#c-people_id").val()},function(res){
// 				    //console.log(res.data);
// 					$('#rank_id').val(res.data.rank_id);
// 					$('#level_id').val(res.data.level_id);
// 					$('#duty').val(res.data.duty);
// 				//console.log(res);
// 				});
// 			    //后续操作
// 			});
//             $("#c-people_id").data("params", function (obj) {
//                 var unit_id = $('#J_unitselect').val();

//                 return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
//                 // return {custom: {unit_id: $("#J_unitselect").val()}};
//             });
//         },
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            }
        }
    };
    return Controller;
});