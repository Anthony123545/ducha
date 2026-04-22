define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area", ["800px", "500px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'flat/index' + location.search,
                    add_url: 'flat/add',
                    edit_url: 'flat/edit',
                    del_url: 'flat/del',
                    multi_url: 'flat/multi',
                    import_url: 'flat/import',
                    table: 'flat',
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
                        {field: 'number', title: __('Number'), operate: 'LIKE'},
                        {field: 'number1', title: __('Number1'), operate: 'LIKE'},
                        {field: 'tier', title: __('Tier'), operate: 'LIKE'},
                        {field: 'element', title: __('Element'), operate: 'LIKE'},
                        {field: 'room_number', title: __('Room_number')},
                        {field: 'name', title: __('Name'), operate: 'LIKE'},
                        {field: 'behoof', title: __('Behoof'), operate: 'LIKE'},
                        {field: 'status_quo', title: __('Status_quo'), operate: 'LIKE'},
                        {field: 'period', title: __('Period'), operate: 'LIKE'},
                        {field: 'area', title: __('Area'), operate: 'LIKE'},
                        {field: 'remark', title: __('Remark'), operate: 'LIKE'},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE'},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
             table.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["800px", "500px"]);
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