define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'weekwork_register/index' + location.search,
                    add_url: 'weekwork_register/add',
                    edit_url: 'weekwork_register/edit',
                    del_url: 'weekwork_register/del',
                    multi_url: 'weekwork_register/multi',
                    import_url: 'weekwork_register/import',
                    table: 'weekwork_register',
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
                        {field: 'week', title: __('Week'), searchList: {"星期天":__('星期天'),"星期六":__('星期六'),"星期五":__('星期五'),"星期四":__('星期四'),"星期三":__('星期三'),"星期二":__('星期二'),"星期一":__('星期一')}, formatter: Table.api.formatter.normal},
                        {field: 'time', title: __('Time'), operate: 'LIKE'},
                        {field: 'dateline', title: __('Dateline'), searchList: {"晚上":__('晚上'),"下午":__('下午'),"上午":__('上午')}, formatter: Table.api.formatter.normal},
                        {field: 'unit_id1', title: __('Unit_id1')},
                        {field: 'people_ids', title: __('People_ids'), operate: 'LIKE'},
                        {field: 'site', title: __('Site'), operate: 'LIKE'},
                        {field: 'start_time', title: __('Start_time'), operate: 'LIKE'},
                        {field: 'end_time', title: __('End_time'), operate: 'LIKE'},
                        {field: 'weekwork_id', title: __('Weekwork_id')},
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