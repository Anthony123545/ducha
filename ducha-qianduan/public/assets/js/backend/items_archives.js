define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'items_archives/index' + location.search,
                    add_url: 'items_archives/add',
                    edit_url: 'items_archives/edit',
                    del_url: 'items_archives/del',
                    multi_url: 'items_archives/multi',
                    import_url: 'items_archives/import',
                    table: 'items_archives',
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
                        {field: 'item_name', title: __('Item_name'), operate: 'LIKE'},
                        {field: 'item_brand', title: __('Item_brand'), operate: 'LIKE'},
                        {field: 'specifications', title: __('Specifications'), operate: 'LIKE'},
                        {field: 'remarks', title: __('Remarks'), operate: 'LIKE'},
                        {field: 'create_time', title: __('Create_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'admin_id', title: __('Admin_id')},
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