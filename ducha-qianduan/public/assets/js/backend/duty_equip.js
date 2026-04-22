define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            $(".btn-add").data("area",["920px","360px"]);
			// $(".btn-edit").data("area",["860px","50%"]);
            Table.api.init({
                extend: {
                    index_url: 'duty_equip/index' + location.search,
                    add_url: 'duty_equip/add',
                    edit_url: 'duty_equip/edit',
                    del_url: 'duty_equip/del',
                    multi_url: 'duty_equip/multi',
                    import_url: 'duty_equip/import',
                    table: 'duty_equip',
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
						{field: 'unit_id_text', title: __('Unit_id')},
                        {field: 'name', title: __('Name')},
                        {field: 'time', title: __('值班日期')},
                        {field: 'amount', title: __('Amount')},
                        {field: 'type', title: __('Type')},
                        {field: 'state', title: __('State')},
                        {field: 'site', title: __('Site')},
                        {field: 'contents', title: __('Contents')},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        //{field: 'updatetime', title: __('Updatetime'), operate: 'LIKE'},
                        //{field: 'field', title: __('Field'), operate: 'LIKE'},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
			table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["920px","360px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
            
        },
        edit: function () {
            Controller.api.bindevent();
        },
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            }
        }
    };
    return Controller;
});