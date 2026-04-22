define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'daily_training_count/index' + location.search,
                    add_url: 'daily_training_count/add',
                    edit_url: 'daily_training_count/edit',
                    del_url: 'daily_training_count/del',
                    multi_url: 'daily_training_count/multi',
                    import_url: 'daily_training_count/import',
                    table: 'daily_training_count',
                }
            });

            var table = $("#table");
            $('#s-search').click(function(){
                window.location.href="?unit_id="+$('#s-unit_id').val();
            });
            // 初始化表格
            table.bootstrapTable({
                url: $.fn.bootstrapTable.defaults.extend.index_url,
                pk: 'id',
                sortName: 'id',
                search:false,
                showToggle: false,
                commonSearch: false,
                pagination:false,
                columns: [
                    [
                        {checkbox: true},
                        {field: 'unit_name', title: __('所属单位')},
                        {field: 'total', title: __('单位总人数')},
                        {field: 'drill_name', title: __('训练类别')},
                        {field: 'avg', title: __('平均参训人数')},
                        {field: 'pjcxgl', title: __('平均参训概率')},
                        {field: 'month_total', title: __('本月累计')},
                        {field: 'year_total', title: __('年度累计')},
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