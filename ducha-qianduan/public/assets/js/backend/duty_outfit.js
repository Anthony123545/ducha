define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            $(".btn-add").data("area", ["768px", "410px"]);
            Table.api.init({
                extend: {
                    index_url: 'duty_outfit/index' + location.search,
                    add_url: 'duty_outfit/add',
                    edit_url: 'duty_outfit/edit',
                    del_url: 'duty_outfit/del',
                    multi_url: 'duty_outfit/multi',
                    import_url: 'duty_outfit/import',
                    table: 'duty_outfit',
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
                        {field: 'zbbz', title: __('Zbbz')},
                        {field: 'zbsy', title: __('Zbsy')},
                        {field: 'zbky', title: __('Zbky')},
                        {field: 'sjpt', title: __('Sjpt')},
                        {field: 'bzpt', title: __('Bzpt')},
                        {field: 'cczb', title: __('Cczb')},
                        {field: 'zbcd', title: __('Zbcd')},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["768px", "410px"]);
            })
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