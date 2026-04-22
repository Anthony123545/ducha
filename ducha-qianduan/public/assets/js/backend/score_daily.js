define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'score_daily/index' + location.search,
                    add_url: 'score_daily/add',
                    edit_url: 'score_daily/edit',
                    del_url: 'score_daily/del',
                    multi_url: 'score_daily/multi',
                    import_url: 'score_daily/import',
                    table: 'score_daily',
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
                        {field: 'unit_id_text', title: __('Unit_id')},
                        //{field: 'unit_id_full', title: __('Unit_id_full'), operate: 'LIKE'},
                        {field: 'year', title: __('Year'), operate: 'LIKE'},
                        {field: 'score', title: __('Score'), operate:'BETWEEN'},
                        {field: 'score_inc', title: __('Score_inc'), operate:'BETWEEN'},
                        {field: 'score_dec', title: __('Score_dec'), operate:'BETWEEN'},
//                        {field: 'score_daily', title: __('Score_daily'), operate:'BETWEEN'},
//                        {field: 'score_daily_inc', title: __('Score_daily_inc'), operate:'BETWEEN'},
//                        {field: 'score_daily_dec', title: __('Score_daily_dec'), operate:'BETWEEN'},
//                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
//                        {field: 'updatetime', title: __('Updatetime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
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