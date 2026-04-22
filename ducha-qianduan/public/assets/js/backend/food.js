define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["1000px","690px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'food/index' + location.search,
                    add_url: 'food/add',
                    edit_url: 'food/edit',
                    del_url: 'food/del',
                    multi_url: 'food/multi',
                    import_url: 'food/import',
                    table: 'food',
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
						{field: 'unit_id_text', title: __('单位')},
                        {field: 'people_id_text', title: __('People_id')},
						{field: 'record_date', title: __('Record_date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'chairman_id_text', title: __('经委会主任')},
                        {field: 'people_id1_text', title: __('People_id1')},
                        //{field: 'people_ids', title: __('People_ids'), operate: 'LIKE'},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        //{field: 'updatetime', title: __('Updatetime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
                                
								{
								    name: '查看',
								    text: '查看',
								    title: '查看',
								    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
								    icon: 'fa fa-search',
								    url: 'food/details',
									extend: 'data-area=\'["960px","80%"]\'',
								    refresh: true
								}
                            ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","690px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-people_id,#c-chairman_id,#c-people_id1,#c-people_ids").data("params", function (obj) {
				// console.log($(this).attr('id'))
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
			$(document).on('click','.btn-append',function(){
				$(".people_id1,.people_id2").data("params", function (obj) {
				    var unit_id = $('#J_unitselect').val();
				    return {
						"custom[unit_id][0]": "in",
						"custom[unit_id][1]": unit_id
					};
				});
			});
            $(document).on("fa.event.appendfieldlist", "#add-form .btn-append", function (e, obj) {
                Form.events.selectpage(obj);
                Form.events.selectpicker(obj);
                // Form.events.datetimepicker(obj);
            });
        },
        edit: function () {
            Controller.api.bindevent();
			$("#c-people_id,#c-chairman_id,#c-people_id1,#c-people_ids").data("params", function (obj) {
				// console.log($(this).attr('id'))
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
			$(document).on('click','.btn-append',function(){
				$(".people_id1,.people_id2").data("params", function (obj) {
				    var unit_id = $('#J_unitselect').val();
				    return {
						"custom[unit_id][0]": "in",
						"custom[unit_id][1]": unit_id
					};
				});
			});
			$(document).on("fa.event.appendfieldlist", "#edit-form .btn-append", function (e, obj) {
			    Form.events.selectpage(obj);
			    Form.events.selectpicker(obj);
			    // Form.events.datetimepicker(obj);
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