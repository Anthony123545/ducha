define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["1000px","480px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'equip/index' + location.search,
                    add_url: 'equip/add',
                    edit_url: 'equip/edit',
                    del_url: 'equip/del',
                    multi_url: 'equip/multi',
                    import_url: 'equip/import',
                    table: 'equip',
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
                        {field: 'unit_id1_text', title: __('Unit_id1')},
                        {field: 'type', title: __('Type'), searchList: {"教材":__('教材'),"训练器材":__('训练器材')}, formatter: Table.api.formatter.normal},
                        {field: 'title', title: __('Title'), operate: 'LIKE'},
                        {field: 'unit_id_text', title: __('Unit_id')},
                        {field: 'granttime', title: __('Granttime'), operate: 'LIKE'},
                        {field: 'grantnum', title: __('Grantnum')},
                        {field: 'ownnum', title: __('Ownnum')},
                        {field: 'stocknum', title: __('Stocknum')},
                        {field: 'intime', title: __('Intime'), operate: 'LIKE'},
                        {field: 'innum', title: __('Innum')},
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'outtime', title: __('Outtime'), operate: 'LIKE'},
                        {field: 'outnum', title: __('Outnum')},
                        {field: 'people_id1_text', title: __('People_id1'), operate: 'LIKE'},
                        {field: 'quality', title: __('Quality'), operate: 'LIKE'},
                        //{field: 'info', title: __('Info'), operate: 'LIKE'},
                        //{field: 'time', title: __('Time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
                                
								{
								    name: '查看',
								    text: '查看',
								    title: '查看',
								    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
								    icon: 'fa fa-search',
									extend: 'data-area=\'["1200px","80%"]\'',
								    url: 'equip/details',
								    refresh: true
								}
                            ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","480px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-people_id,#c-people_id1").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
        },
        edit: function () {
            Controller.api.bindevent();
			$("#c-people_id,#c-people_id1").data("params", function (obj) {
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