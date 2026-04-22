define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'durg_amount/index' + location.search,
                    add_url: 'durg_amount/add',
                    edit_url: 'durg_amount/edit',
                    del_url: 'durg_amount/del',
                    multi_url: 'durg_amount/multi',
                    import_url: 'durg_amount/import',
                    table: 'durg_amount',
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
                        {field: 'durg_archives_id_text', title: __('药品名称')},
                        {field: 'unit_id_text', title: __('单位')},
                        {field: 'store_house_text', title: __('store_house')},
                        {field: 'amount', title: __('Amount')},
                        {field: 'put_in', title: __('Put_in')},
                        {field: 'put_out', title: __('Put_out')},
                        {field: 'overdue', title: __('Overdue')},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        {field: 'create_time', title: __('Create_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'update_time', title: __('Update_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
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