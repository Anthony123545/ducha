define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            //$('.btn-add').data('area',['100%','100%']);

            $('.btn-edit').data('area',['40%','20%']);
            Table.api.init({
                extend: {
                    index_url: 'duty_measure/index' + location.search,
                    add_url: 'duty_measure/add',
                    edit_url: 'duty_measure/edit',
                    del_url: 'duty_measure/del',
                    multi_url: 'duty_measure/multi',
                    import_url: 'duty_measure/import',
                    table: 'duty_measure',
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
                        {field: 'yl_zscb', title: __('Yl_zscb'), operate: 'LIKE'},
                        {field: 'yl_cxyz', title: __('Yl_cxyz'), operate: 'LIKE'},
                        {field: 'yl_zcnl', title: __('Yl_zcnl'), operate: 'LIKE'},
                        {field: 'jx_zscb', title: __('Jx_zscb'), operate: 'LIKE'},
                        {field: 'jx_cxyz', title: __('Jx_cxyz'), operate: 'LIKE'},
                        {field: 'jx_zcnl', title: __('Jx_zcnl'), operate: 'LIKE'},
                        {field: 'zbj_zscb', title: __('Zbj_zscb'), operate: 'LIKE'},
                        {field: 'wswz_zscb', title: __('Wswz_zscb'), operate: 'LIKE'},
                        {field: 'wswz_cxyz', title: __('Wswz_cxyz'), operate: 'LIKE'},
                        {field: 'dy_ccl', title: __('Dy_ccl'), operate: 'LIKE'},
                        {field: 'wx_ccl', title: __('Wx_ccl'), operate: 'LIKE'},
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