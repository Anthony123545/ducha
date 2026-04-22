define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["1000px","360px"]);

            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'file_management/index' + location.search,
                    add_url: 'file_management/add',
                    edit_url: 'file_management/edit',
                    del_url: 'file_management/del',
                    multi_url: 'file_management/multi',
                    import_url: 'file_management/import',
                    table: 'file_management',
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
                        {field: 'id', title: __('Id')},
                        {field: 'number', title: __('Number'), operate: 'LIKE'},
                        {field: 'name', title: __('Name'), operate: 'LIKE'},
                        {field: 'incoming_dispatches_time', title: __('Incoming_dispatches_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'confidential', title: __('Confidential'), operate: 'LIKE'},
                        {field: 'copies', title: __('Copies')},
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'borrow_id', title: __('Borrow_id')},
                        {field: 'loan_time', title: __('Loan_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'return_time', title: __('return_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'logout_id', title: __('Logout_id')},
                        {field: 'unit_id_text', title: __('Unit_id')},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        //{field: 'updatetime', title: __('Updatetime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
							{
							    name: '查看',
							    text: '查看',
							    title: '查看',
							    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
							    icon: 'fa fa-search',
							    url: 'file_management/details',
                                extend: 'data-area=\'["1000px","80%"]\'',
							    refresh: true
							}
                        ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","360px"]);
            })
            // $(".btn-dialog").data("area",["1000px","500px"]);
            table.on('post-body.bs.table',function(){
				$(".btn-dialog").data("area",["1100px","620px"]);
            })
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-people_id,#c-borrow_id,#c-logout_id").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
        },
        edit: function () {
            Controller.api.bindevent();
			$("#c-people_id,#c-borrow_id,#c-logout_id").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
        },
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            }
        }
    };
    return Controller;
});