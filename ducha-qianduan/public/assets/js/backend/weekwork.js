define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
			$(".btn-add").data("area",["1050px","680px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'weekwork/index' + location.search,
                    add_url: 'weekwork/add',
                    edit_url: 'weekwork/edit',
                    del_url: 'weekwork/del',
                    multi_url: 'weekwork/multi',
                    import_url: 'weekwork/import',
                    table: 'weekwork',
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
                        {field: 'start_time', title: __('Start_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'end_time', title: __('End_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'phone', title: __('Phone'), operate: 'LIKE'},
                        {field: 'people_id1_text', title: __('People_id1')},
                        {field: 'people_id2_text', title: __('People_id2')},
                        {field: 'time', title: __('Time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        // {field: 'remark', title: __('Remark'), operate: 'LIKE'},
                        // {field: 'unit_id', title: __('Unit_id')},
                        // {field: 'admin_id', title: __('Admin_id')},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
								{
									name: '查看',
									text: '查看',
									title: '查看',
									classname: 'btn btn-xs btn-info btn-view btn-dialog ',
									icon: 'fa fa-search',
									url: 'weekwork/details',
									extend: 'data-area=\'["1100px","80%"]\'',
									refresh: true
								}
							], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
			Table.api.bindevent(table);
			table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1024px","680px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-people_id,#c-people_id1,#c-people_id2").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
			//因为日期选择框不会触发change事件，导致无法刷新textarea，所以加上判断
			$(document).on("dp.change", "#add-form .datetimepicker", function () {
			    $(this).parent().prev().find("input").trigger("change");
			});
			
			$(document).on("fa.event.appendfieldlist", "#add-form .btn-append", function (e, obj) {
			    Form.events.selectpage(obj);
			    Form.events.datetimepicker(obj);
			});
			
			
			
			$(document).on('click','.btn-append',function(){
				var form = $("#add-form");
				require(['bootstrap-datetimepicker'], function () {
				   var options = {
					   format: 'LT',
					   icons: {
						   time: 'fa fa-clock-o',
						   date: 'fa fa-calendar',
						   up: 'fa fa-chevron-up',
						   down: 'fa fa-chevron-down',
						   previous: 'fa fa-chevron-left',
						   next: 'fa fa-chevron-right',
						   today: 'fa fa-history',
						   clear: 'fa fa-trash',
						   close: 'fa fa-remove'
					   },
					   showTodayButton: true,
					   showClose: true
				   };
				   $('.datetimepicker3', form).parent().css('position', 'relative');
				   $('.datetimepicker3', form).datetimepicker(options).on('dp.change', "#add-form .datetimepicker3", function (e) {
				       $(this, document).trigger("changed");
				   });
				});
			});
        },
        edit: function () {
            Controller.api.bindevent();
			$("#c-people_id,#c-people_id1,#c-people_id2").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
			//因为日期选择框不会触发change事件，导致无法刷新textarea，所以加上判断
			$(document).on("dp.change", "#edit-form .datetimepicker", function () {
			    $(this).parent().prev().find("input").trigger("change");
			});
			
			$(document).on("fa.event.appendfieldlist", "#edit-form .btn-append", function (e, obj) {
			    Form.events.selectpage(obj);
			    Form.events.datetimepicker(obj);
			});
			
			
			
			$(document).on('click','.btn-append',function(){
				var form = $("#edit-form");
				require(['bootstrap-datetimepicker'], function () {
				   var options = {
					   format: 'LT',
					   icons: {
						   time: 'fa fa-clock-o',
						   date: 'fa fa-calendar',
						   up: 'fa fa-chevron-up',
						   down: 'fa fa-chevron-down',
						   previous: 'fa fa-chevron-left',
						   next: 'fa fa-chevron-right',
						   today: 'fa fa-history',
						   clear: 'fa fa-trash',
						   close: 'fa fa-remove'
					   },
					   showTodayButton: true,
					   showClose: true
				   };
				   $('.datetimepicker3', form).parent().css('position', 'relative');
				   $('.datetimepicker3', form).datetimepicker(options).on('dp.change', "#edit-form .datetimepicker3", function (e) {
				       $(this, document).trigger("changed");
				   });
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