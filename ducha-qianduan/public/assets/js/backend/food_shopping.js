define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'food_shopping/index' + location.search,
                    add_url: 'food_shopping/add',
                    edit_url: 'food_shopping/edit',
                    del_url: 'food_shopping/del',
                    multi_url: 'food_shopping/multi',
                    import_url: 'food_shopping/import',
                    table: 'food_shopping',
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
                        {field: 'z_name', title: __('Z_name'), operate: 'LIKE'},
                        {field: 'z_weight', title: __('Z_weight'), operate:'BETWEEN'},
                        {field: 'z_money', title: __('Z_money'), operate:'BETWEEN'},
                        {field: 'total', title: __('Total'), operate:'BETWEEN'},
                        {field: 'food_id', title: __('Food_id')},
                        {field: 'f_name', title: __('F_name'), operate: 'LIKE'},
                        {field: 'f_weight', title: __('F_weight'), operate:'BETWEEN'},
                        {field: 'f_money', title: __('F_money'), operate:'BETWEEN'},
                        {field: 'q_name', title: __('Q_name'), operate: 'LIKE'},
                        {field: 'q_weight', title: __('Q_weight'), operate:'BETWEEN'},
                        {field: 'q_money', title: __('Q_money'), operate:'BETWEEN'},
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