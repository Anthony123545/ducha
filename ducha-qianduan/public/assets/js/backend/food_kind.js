define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'food_kind/index' + location.search,
                    add_url: 'food_kind/add',
                    edit_url: 'food_kind/edit',
                    del_url: 'food_kind/del',
                    multi_url: 'food_kind/multi',
                    import_url: 'food_kind/import',
                    table: 'food_kind',
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
                        {field: 'distinctions', title: __('Distinctions'), searchList: {"其他":__('其他'),"副食":__('副食'),"主食":__('主食')}, formatter: Table.api.formatter.normal},
                        {field: 'name', title: __('Name'), operate: 'LIKE'},
                        {field: 'weight', title: __('Weight'), operate:'BETWEEN'},
                        {field: 'unit_price', title: __('Unit_price'), operate:'BETWEEN'},
                        {field: 'money', title: __('Money'), operate:'BETWEEN'},
                        {field: 'types', title: __('Types'), searchList: {"自产":__('自产'),"购买":__('购买')}, formatter: Table.api.formatter.normal},
                        {field: 'remark', title: __('Remark'), operate: 'LIKE'},
                        {field: 'food_id', title: __('Food_id')},
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