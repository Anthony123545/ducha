define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'cookcategory/index' + location.search,
                    add_url: 'cookcategory/add',
                    edit_url: 'cookcategory/edit',
                    del_url: 'cookcategory/del',
                    multi_url: 'cookcategory/multi',
                    import_url: 'cookcategory/import',
                    table: 'cookcategory',
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
                        {field: 'typelist', title: __('Typelist'), searchList: {"主食":__('主食'),"副食":__('副食')}, formatter: Table.api.formatter.normal},
                        {field: 'timelist', title: __('Timelist'), searchList: {"早餐":__('早餐'),"午餐":__('午餐'),"晚餐":__('晚餐')}, operate:'FIND_IN_SET', formatter: Table.api.formatter.label},
                        {field: 'name', title: __('Name'), operate: 'LIKE'},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'updatetime', title: __('Updatetime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
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