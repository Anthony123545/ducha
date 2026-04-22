define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["100px","320px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'thebus_config/index' + location.search,
                    add_url: 'thebus_config/add',
                    edit_url: 'thebus_config/edit',
                    del_url: 'thebus_config/del',
                    multi_url: 'thebus_config/multi',
                    import_url: 'thebus_config/import',
                    table: 'thebus_config',
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
                        {field: 'people_type', title: __('People_type'), operate: 'LIKE'},
                        {field: 'auth_auto_open', title: __('Auth_auto_open')},
                        {field: 'car_auto_back', title: __('Car_auto_back')},
                        {field: 'noauth_open', title: __('Noauth_open')},
                        {field: 'time1', title: __('Time1')},
                        {field: 'time2', title: __('Time2')},
                        {field: 'default_unit_id', title: __('Default_unit_id')},
                        {field: 'default_people_id', title: __('Default_people_id')},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["500px","400px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
            
        },
        edit: function () {
            Controller.api.bindevent();
            
            $("#c-default_people_id").data("params", function (obj) {
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