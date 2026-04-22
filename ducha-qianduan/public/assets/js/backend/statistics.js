define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'statistics/index' + location.search,
                    add_url: 'statistics/add',
                    edit_url: 'statistics/edit',
                    del_url: 'statistics/del',
                    multi_url: 'statistics/multi',
                    import_url: 'statistics/import',
                    table: 'statistics',
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
                        {field: 'order_number', title: __('Id')},
                        {field: 'unit_id_text', title: __('Unit_id')},
                        {field: 'should_be_to', title: __('Should_be_to')},
                        {field: 'actual_arrival', title: __('Actual_arrival')},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
                                
								{
								    name: '查看',
								    text: '查看',
								    title: '查看',
								    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
								    icon: 'fa fa-search',
								    url: 'statistics/details',
									extend: 'data-area=\'["1200px","80%"]\'',
								    refresh: true
								}
                            ], formatter: Table.api.formatter.operate}
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