define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["1000px","400px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'property/index' + location.search,
                    add_url: 'property/add',
                    edit_url: 'property/edit',
                    del_url: 'property/del',
                    multi_url: 'property/multi',
                    import_url: 'property/import',
                    table: 'property',
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
                        {field: 'order_number', title: __('序号'), operate: false},
                        {field: 'type', title: __('类型'), searchList:{0:'公物',1:'营具'} ,visible:false},
                        {field: 'type_text', title: __('类型'), operate: false},
                        {field: 'name', title: __('Name'), operate: false},
                        {field: 'specification', title: __('Specification'), operate: false},
                        {field: 'version', title: __('Version'), operate: false},
                        {field: 'category_id_text', title: __('category_id'), operate: false},
                        {field: 'unit_id_text', title: __('Unit_id'), operate: false},
                        {field: 'people_id_text', title: __('People_id'), operate: false},
                        {field: 'total_amount', title: __('总数量'), operate: false},
                        {field: 'amount', title: __('库存数量'), operate: false},
                        {field: 'use_amount', title: __('在用数量'), operate: false},
                        {field: 'admin_id_text', title: __('Admin_id'), operate: false},
                        {field: 'createtime', title: __('录入时间'),formatter: Table.api.formatter.datetime, operate: false},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate, operate: false}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","400px"]);
			})
        },
        index1: function () {
            $(".btn-add").data("area",["1000px","400px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'property/index1' + location.search,
                    add_url: 'property/add1',
                    edit_url: 'property/edit1',
                    del_url: 'property/del',
                    multi_url: 'property/multi',
                    import_url: 'property/import',
                    table: 'property',
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
                        {field: 'name', title: __('Name'), operate: 'LIKE'},
                        {field: 'specification', title: __('Specification'), operate: false},
                        {field: 'version', title: __('Version'), operate: false},
                        {field: 'category_id_text', title: __('category_id'), operate: false},
                        {field: 'unit_id_text', title: __('Unit_id')},
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'total_amount', title: __('总数量')},
                        {field: 'amount', title: __('库存数量')},
                        {field: 'use_amount', title: __('在用数量')},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        {field: 'createtime', title: __('录入时间'),formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","400px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
        },
        edit: function () {
            Controller.api.bindevent();
        },
        add1: function () {
            Controller.api.bindevent();
        },
        edit1: function () {
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