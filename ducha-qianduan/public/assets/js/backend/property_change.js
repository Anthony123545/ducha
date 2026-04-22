define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'property_change/index' + location.search,
                    add_url: 'property_change/add',
                    edit_url: 'property_change/edit',
                    del_url: 'property_change/del',
                    multi_url: 'property_change/multi',
                    import_url: 'property_change/import',
                    table: 'property_change',
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
                        {field: 'order_number', title: __('Id'),operate:false},
                        {field: 'name', title: __('Name'), operate: 'LIKE'},
                        {field: 'ruku_number', title: __('Ruku_number'), operate: 'LIKE'},
                        {field: 'before_unit_id_text', title: __('Before_unit_id'),operate:false},
                        {field: 'before_people_id_text', title: __('Before_people_id'),operate:false},
                        {field: 'after_unit_id_text', title: __('After_unit_id'),operate:false},
                        {field: 'after_people_id_text', title: __('After_people_id'),operate:false},
                        {field: 'change_time', title: __('Change_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        // {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        // {field: 'updatetime', title: __('Updatetime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
        },
        add: function () {
            $("#c-before_people_id").data("params", function (obj) {
                return {custom: {unit_id: $("#c-before_unit_id").val()}};
            });
            $("#c-after_people_id").data("params", function (obj) {
                return {custom: {unit_id: $("#c-after_unit_id").val()}};
            });
            $(document).on("change", "#c-property_id", function(){
                $('#c-name').val($('#c-property_id_text').val());
            });
            Controller.api.bindevent();
        },
        edit: function () {
            $("#c-before_people_id").data("params", function (obj) {
                return {custom: {unit_id: $("#c-before_unit_id").val()}};
            });
            $("#c-after_people_id").data("params", function (obj) {
                return {custom: {unit_id: $("#c-after_unit_id").val()}};
            });
            $(document).on("change", "#c-property_id", function(){
                $('#c-name').val($('#c-property_id_text').val());
            });
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