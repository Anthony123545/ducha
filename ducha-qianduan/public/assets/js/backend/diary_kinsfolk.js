define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'diary_kinsfolk/index' + location.search,
                    add_url: 'diary_kinsfolk/add',
                    edit_url: 'diary_kinsfolk/edit',
                    del_url: 'diary_kinsfolk/del',
                    multi_url: 'diary_kinsfolk/multi',
                    import_url: 'diary_kinsfolk/import',
                    table: 'diary_kinsfolk',
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
                        {field: 'people_id', title: __('People_id')},
                        {field: 'name', title: __('Name'), operate: 'LIKE'},
                        {field: 'relation', title: __('Relation'), operate: 'LIKE'},
                        {field: 'come_date', title: __('Come_date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'leave_date', title: __('Leave_date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE'},
                        {field: 'updatetime', title: __('Updatetime'), operate: 'LIKE'},
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