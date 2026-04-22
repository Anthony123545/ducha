define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'unit_score_log/index' + location.search,
                    add_url: 'unit_score_log/add',
                    edit_url: 'unit_score_log/edit',
                    del_url: 'unit_score_log/del',
                    multi_url: 'unit_score_log/multi',
                    import_url: 'unit_score_log/import',
                    table: 'unit_score_log',
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
                        {field: 'unit_id', title: __('Unit_id')},
                        {field: 'admin_id', title: __('Admin_id')},
                        {field: 'pid', title: __('Pid')},
                        {field: 'type', title: __('Type'), operate: 'LIKE'},
                        {field: 'mode', title: __('Mode'), searchList: {"inc":__('Inc'),"dec":__('Dec')}, formatter: Table.api.formatter.normal},
                        {field: 'value', title: __('Value'), operate:'BETWEEN'},
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