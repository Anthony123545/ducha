define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
             $(".btn-add").data("area",["1000px","350px"]);
            Table.api.init({
                extend: {
                    index_url: 'thebus_themplate/index' + location.search,
                    add_url: 'thebus_themplate/add',
                    edit_url: 'thebus_themplate/edit',
                    del_url: 'thebus_themplate/del',
                    multi_url: 'thebus_themplate/multi',
                    import_url: 'thebus_themplate/import',
                    table: 'thebus_themplate',
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
                        {field: 'order_number', title: __('Id')},
                        {field: 'name', title: __('Name'), operate: 'LIKE'},
                        {field: 'people1_id', title: __('People1_id'),visible:false},
                        {field: 'people1_id_text', title: __('People1_id')},
                        {field: 'people2_id', title: __('People2_id'),visible:false},
                        {field: 'people2_id_text', title: __('People2_id')},
                        {field: 'people3_id', title: __('People3_id'),visible:false},
                        {field: 'people3_id_text', title: __('People3_id')},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
             table.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["660px", "360px"]);
            })
        },
        add: function () {
            Controller.api.bindevent();
            $(document).on("fa.event.appendfieldlist", "#add-form .btn-append", function (e, obj) {
			    Form.events.selectpage(obj);
			    Form.events.datetimepicker(obj);
			});
        },
        edit: function () {
            Controller.api.bindevent();
            
			$("#c-people1_id,#c-people2_id,#c-people3_id").data("params", function (obj) {
			    var custom = {};
			    custom['custom'] = {};
			    custom['custom']['id'] = [];
			    custom['custom']['id'][0] = 'not in';
			    custom['custom']['id'][1] = '1';
			    return custom;
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