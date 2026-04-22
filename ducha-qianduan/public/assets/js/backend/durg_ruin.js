define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'durg_ruin/index' + location.search,
                    // add_url: 'durg_ruin/add',
                    // edit_url: 'durg_ruin/edit',
                    // del_url: 'durg_ruin/del',
                    // multi_url: 'durg_ruin/multi',
                    // import_url: 'durg_ruin/import',
                    table: 'durg_ruin',
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
                        {field: 'durg_archives_id_text', title: __('Durg_archives_id')},
                        {field: 'manufacturer', title: __('Manufacturer'), operate: 'LIKE'},
                        {field: 'specifications', title: __('Specifications'), operate: 'LIKE'},
                        {field: 'durg_brand', title: __('Durg_brand'), operate: 'LIKE'},
                        {field: 'put_in', title: __('Put_in'), operate: 'LIKE'},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
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