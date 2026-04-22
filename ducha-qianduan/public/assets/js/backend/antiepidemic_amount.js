define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'antiepidemic_amount/index' + location.search,
                    add_url: 'antiepidemic_amount/add',
                    edit_url: 'antiepidemic_amount/edit',
                    del_url: 'antiepidemic_amount/del',
                    multi_url: 'antiepidemic_amount/multi',
                    import_url: 'antiepidemic_amount/import',
                    table: 'antiepidemic_amount',
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
                        {field: 'durg_archives_id', title: __('Durg_archives_id')},
                        {field: 'unit_id', title: __('Unit_id')},
                        {field: 'store_house', title: __('Store_house')},
                        {field: 'amount', title: __('Amount')},
                        {field: 'put_in', title: __('Put_in')},
                        {field: 'put_out', title: __('Put_out')},
                        {field: 'admin_id', title: __('Admin_id')},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE'},
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