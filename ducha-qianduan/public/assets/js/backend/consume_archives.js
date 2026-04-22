define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["720px","520px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'consume_archives/index' + location.search,
                    add_url: 'consume_archives/add',
                    edit_url: 'consume_archives/edit',
                    del_url: 'consume_archives/del',
                    multi_url: 'consume_archives/multi',
                    import_url: 'consume_archives/import',
                    table: 'consume_archives',
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
                        {field: 'durg_name', title: __('Durg_name'), operate: 'LIKE'},
                        {field: 'durg_brand', title: __('Durg_brand'), operate: 'LIKE'},
                        {field: 'manufacturer', title: __('Manufacturer'), operate: 'LIKE'},
                        {field: 'specifications', title: __('Specifications'), operate: 'LIKE'},
                        {field: 'product_introduction', title: __('Product_introduction'), operate: 'LIKE'},
                        //{field: 'remarks', title: __('Remarks'), operate: 'LIKE'},
                        {field: 'store_house_text', title: __('Store_house')},
                        {field: 'unit_id_text', title: __('Unit_id')},
                        {field: 'amount', title: __('Amount')},
                        {field: 'put_in', title: __('Put_in')},
                        {field: 'put_out', title: __('Put_out')},
                        {field: 'overdue', title: __('Overdue')},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        //{field: 'create_time', title: __('Create_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["720px", "520px"]);
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