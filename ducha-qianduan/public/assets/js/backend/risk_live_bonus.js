define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'risk_live_bonus/index' + location.search,
                    add_url: 'risk_live_bonus/add',
                    edit_url: 'risk_live_bonus/edit',
                    del_url: 'risk_live_bonus/del',
                    multi_url: 'risk_live_bonus/multi',
                    import_url: 'risk_live_bonus/import',
                    table: 'risk_live_bonus',
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
                        {field: 'risk_live_id', title: __('Risk_live_id')},
                        {field: 'type', title: __('Type')},
                        {field: 'status', title: __('Status'), searchList: {"通过":__('通过'),"不通过":__('不通过')}, formatter: Table.api.formatter.status},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'updatetime', title: __('Updatetime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'file', title: __('File'), operate: false},
                        {field: 'file_name', title: __('File_name'), operate: 'LIKE'},
                        {field: 'plus', title: __('Plus'), operate:'BETWEEN'},
                        {field: 'minus', title: __('Minus'), operate:'BETWEEN'},
                        {field: 'state', title: __('State')},
                        {field: 'cause', title: __('Cause'), operate: 'LIKE'},
                        {field: 'address', title: __('Address'), operate: 'LIKE'},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
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