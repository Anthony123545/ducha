define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'driver/index' + location.search,
                    add_url: 'driver/add',
                    edit_url: 'driver/edit',
                    del_url: 'driver/del',
                    multi_url: 'driver/multi',
                    import_url: 'driver/import',
                    table: 'driver',
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
                        {field: 'order_number', title: __('序号'),operate:false},
                        {field: 'unit_id', title: __('Unit_id'),visible:false, searchList: $.getJSON("unit/index?lists=1")},
                        {field: 'unit_id_text', title: __('Unit_id'),operate:false},
                        {field: 'people_id', title: __('姓名'),visible:false,operate:false},
                        {field: 'people_id_text', title: __('姓名'),operate:false},
                        {field: 'contact', title: __('Contact'),operate:false},
                        {field: 'drivers_license', title: __('Drivers_license'),operate:false},
                        {field: 'birth_date', title: __('出生日期'),operate:false},
                        {field: 'enlist_date', title: __('Enlist_date'),operate:false},
                        {field: 'sex_list', title: __('Sex_list'), searchList: {"男":__('男'),"女":__('女')}, formatter: Table.api.formatter.normal},
                        {field: 'rank_id', title: __('Rank_id'),visible:false,operate:false},
                        {field: 'rank_id_text', title: __('Rank_id'),operate:false},
                        {field: 'driving_age', title: __('Driving_age'),operate:false},
                        {field: 'driving_model', title: __('Driving_model'),operate:false},
                        {field: 'date1', title: __('Date1'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'date2', title: __('Date2'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'date3', title: __('Date3'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'result', title: __('Result'), operate: 'LIKE'},
                        {field: 'vocational_skills', title: __('Vocational_skills'), operate: 'LIKE'},
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
            $("#c-people_id").data("params", function (obj) {
                return {custom: {unit_id: $("#c-unit_id").val()}};
            });
            
            $(document).on("change", "#c-people_id", function(){
                $.get('people/huoq',{'id':$(this).val()},function(res){
                    $('#c-contact').val(res.data.phone);
                    $('#c-birth_date').val(res.data.birth_date);
                    $('#c-enlist_date').val(res.data.enlist_date);
                    $('#c-rank_id').val(res.data.rank_id);
                    $('#c-rank_id').selectPageRefresh();
                    $('#c-sex_list').val(res.data.sex_list);
                    $('#c-sex_list').selectpicker('refresh');
                });
            });
        },
        edit: function () {
            Controller.api.bindevent();
            $("#c-people_id").data("params", function (obj) {
                return {custom: {unit_id: $("#c-unit_id").val()}};
            });
            
            $(document).on("change", "#c-people_id", function(){
                $.get('people/huoq',{'id':$(this).val()},function(res){
                    $('#c-contact').val(res.data.phone);
                    $('#c-birth_date').val(res.data.birth_date);
                    $('#c-enlist_date').val(res.data.enlist_date);
                    $('#c-rank_id').val(res.data.rank_id);
                    $('#c-rank_id').selectPageRefresh();
                    $('#c-sex_list').val(res.data.sex_list);
                    $('#c-sex_list').selectpicker('refresh');
                });
            });
        },
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            }
        }
    };
    return Controller;
});