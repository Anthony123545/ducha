define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'food_board_wages/index' + location.search,
                    add_url: 'food_board_wages/add',
                    edit_url: 'food_board_wages/edit',
                    del_url: 'food_board_wages/del',
                    multi_url: 'food_board_wages/multi',
                    import_url: 'food_board_wages/import',
                    table: 'food_board_wages',
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
                        {field: 'type', title: __('Type'), searchList: {"退":__('退'),"交":__('交')}, formatter: Table.api.formatter.normal},
                        {field: 'sky', title: __('Sky')},
                        {field: 'standard', title: __('Standard'), operate:'BETWEEN'},
                        {field: 'money', title: __('Money'), operate:'BETWEEN'},
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