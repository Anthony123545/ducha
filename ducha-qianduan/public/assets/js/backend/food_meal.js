define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'food_meal/index' + location.search,
                    add_url: 'food_meal/add',
                    edit_url: 'food_meal/edit',
                    del_url: 'food_meal/del',
                    multi_url: 'food_meal/multi',
                    import_url: 'food_meal/import',
                    table: 'food_meal',
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
                        {field: 'staple_food', title: __('Staple_food'), operate: 'LIKE'},
                        {field: 'menu', title: __('Menu'), operate: 'LIKE'},
                        {field: 'type1', title: __('Type1'), searchList: {"晚餐":__('晚餐'),"午餐":__('午餐'),"早餐":__('早餐')}, formatter: Table.api.formatter.normal},
                        {field: 'jiucan_count', title: __('Jiucan_count')},
                        {field: 'liandui_count', title: __('Liandui_count')},
                        {field: 'wailai_count', title: __('Wailai_count')},
                        {field: 'mianfen', title: __('Mianfen')},
                        {field: 'dami', title: __('Dami')},
                        {field: 'rolei', title: __('Rolei')},
                        {field: 'yulei', title: __('Yulei')},
                        {field: 'youlei', title: __('Youlei')},
                        {field: 'huangdo', title: __('Huangdo')},
                        {field: 'suchai', title: __('Suchai')},
                        {field: 'shuiguo', title: __('Shuiguo')},
                        {field: 'tang', title: __('Tang')},
                        {field: 'ranliao', title: __('Ranliao')},
                        {field: 'tiaoliao', title: __('Tiaoliao')},
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