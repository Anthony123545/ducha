define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area", ["768px", "410px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'duty_test/index' + location.search,
                    add_url: 'duty_test/add',
                    edit_url: 'duty_test/edit',
                    del_url: 'duty_test/del',
                    multi_url: 'duty_test/multi',
                    import_url: 'duty_test/import',
                    table: 'duty_test',
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
                        {field: 'phone', title: __('Phone'), operate: 'LIKE'},
                        {field: 'people_ids_text', title: __('People_ids'), operate: 'LIKE'},
                        {field: 'site', title: __('Site'), operate: 'LIKE'},
                        {field: 'start_time', title: __('Start_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'end_time', title: __('End_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);

            table.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["768px", "410px"]);
            })
        },
        add: function () {
            Controller.api.bindevent();
            $("#c-people_id,#c-people_ids").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
			$("#c-people_id").data("eSelect", function(){
				$.get('duty_test/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-phone').val(res.data.phone);
				});
			});
        },
        edit: function () {
            Controller.api.bindevent();
            $("#c-people_id,#c-people_ids").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
			$("#c-people_id").data("eSelect", function(){
				$.get('duty_test/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-phone').val(res.data.phone);
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