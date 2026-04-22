define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'cookbook_registration/index' + location.search,
                    add_url: 'cookbook_registration/add',
                    edit_url: 'cookbook_registration/edit',
                    del_url: 'cookbook_registration/del',
                    multi_url: 'cookbook_registration/multi',
                    import_url: 'cookbook_registration/import',
                    table: 'cookbook_registration',
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
                        {field: 'weeklist', title: __('Weeklist'), searchList: {"星期一":__('星期一'),"星期二":__('星期二'),"星期三":__('星期三'),"星期四":__('星期四'),"星期五":__('星期五'),"星期六":__('星期六'),"星期日":__('星期日')}, formatter: Table.api.formatter.normal},
                        {field: 'cookcategory_ids', title: __('Cookcategory_ids'), operate: 'LIKE'},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE'},
                        {field: 'updatetime', title: __('Updatetime'), operate: 'LIKE'},
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