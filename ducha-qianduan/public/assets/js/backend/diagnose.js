define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area", ["730px", "400px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'diagnose/index' + location.search,
                    add_url: 'diagnose/add',
                    edit_url: 'diagnose/edit',
                    del_url: 'diagnose/del',
                    multi_url: 'diagnose/multi',
                    import_url: 'diagnose/import',
                    table: 'diagnose',
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
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'address', title: __('Address'), operate: 'LIKE'},
                        {field: 'start_time', title: __('Start_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'duration', title: __('Duration'), operate: 'LIKE'},
                        {field: 'end_time', title: __('End_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'illness', title: __('病情')},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["730px", "400px"]);
            })
        },
        add: function () {
            Controller.api.bindevent();
            $("#c-people_id").data("params", function (obj) {
				var unit_id = $('#J_unitselect').val();
				return {
					"custom[unit_id][0]": "in",
					"custom[unit_id][1]": unit_id
				};
			});
        },
        edit: function () {
            Controller.api.bindevent();
            $("#c-people_id").data("params", function (obj) {
				var unit_id = $('#J_unitselect').val();
				return {
					"custom[unit_id][0]": "in",
					"custom[unit_id][1]": unit_id
				};
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