define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'work_task_minute/index' + location.search,
                    add_url: 'work_task_minute/add',
                    edit_url: 'work_task_minute/edit',
                    del_url: 'work_task_minute/del',
                    multi_url: 'work_task_minute/multi',
                    import_url: 'work_task_minute/import',
                    table: 'work_task_minute',
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
                        {field: 'should_be_to', title: __('Should_be_to')},
                        {field: 'actual_arrival', title: __('Actual_arrival')},
                        {field: 'probability', title: __('Probability'), operate:'BETWEEN'},
                        {field: 'start_time', title: __('Start_time'), operate: 'LIKE'},
                        {field: 'end_time', title: __('End_time'), operate: 'LIKE'},
                        {field: 'people_id', title: __('People_id'), operate: 'LIKE'},
                        {field: 'work_task_id', title: __('Work_task_id')},
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