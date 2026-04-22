define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'food_procurement/index' + location.search,
                    add_url: 'food_procurement/add',
                    edit_url: 'food_procurement/edit',
                    del_url: 'food_procurement/del',
                    multi_url: 'food_procurement/multi',
                    import_url: 'food_procurement/import',
                    table: 'food_procurement',
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
                        {field: 'distinction', title: __('Distinction'), searchList: {"调料":__('调料'),"燃料":__('燃料'),"糖":__('糖'),"水果":__('水果'),"蔬菜":__('蔬菜'),"黄豆":__('黄豆'),"油类":__('油类'),"鱼类":__('鱼类'),"肉类":__('肉类'),"大米":__('大米'),"面粉":__('面粉')}, formatter: Table.api.formatter.normal},
                        {field: 'time', title: __('Time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'name', title: __('Name'), operate: 'LIKE'},
                        {field: 'amount', title: __('Amount')},
                        {field: 'price', title: __('Price'), operate:'BETWEEN'},
                        {field: 'consumption', title: __('Consumption'), operate:'BETWEEN'},
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