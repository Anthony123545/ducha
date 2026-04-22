define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'recuperate_examine/index' + location.search,
                    add_url: 'recuperate_examine/add',
                    edit_url: 'recuperate_examine/edit',
                    del_url: 'recuperate_examine/del',
                    multi_url: 'recuperate_examine/multi',
                    import_url: 'recuperate_examine/import',
                    table: 'recuperate_examine',
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
                        {field: 'recuperate_id', title: __('Recuperate_id')},
                        {field: 'people_id', title: __('People_id')},
                        {field: 'unit_id', title: __('Unit_id')},
                        {field: 'state', title: __('State'), searchList: {"不通过":__('不通过'),"通过":__('通过')}, formatter: Table.api.formatter.normal},
                        {field: 'contents', title: __('Contents'), operate: 'LIKE'},
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