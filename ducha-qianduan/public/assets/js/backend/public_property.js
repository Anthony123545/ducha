define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["1000px","450px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'public_property/index' + location.search,
                    add_url: 'public_property/add',
                    edit_url: 'public_property/edit',
                    del_url: 'public_property/del',
                    multi_url: 'public_property/multi',
                    import_url: 'public_property/import',
                    table: 'public_property',
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
                        {field: 'unit_id_text', title: __('Unit_id')},
                        {field: 'name', title: __('Name'), operate: 'LIKE'},
                        {field: 'version', title: __('Version'), operate: 'LIKE'},
                        {field: 'quantity', title: __('Quantity')},
                        {field: 'price', title: __('Price'), operate:'BETWEEN'},
                        {field: 'total_price', title: __('Total_price'), operate:'BETWEEN'},
                        {field: 'equip_time', title: __('Equip_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'acquire', title: __('Acquire'), operate: 'LIKE'},
                        {field: 'quality', title: __('Quality'), operate: 'LIKE'},
                        {field: 'address', title: __('Address'), operate: 'LIKE'},
                        {field: 'remark', title: __('Remark'), operate: 'LIKE'},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        //{field: 'updatetime', title: __('Updatetime'), operate: 'LIKE'},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
                                
								{
								    name: '查看',
								    text: '查看',
								    title: '查看',
								    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
								    icon: 'fa fa-search',
								    url: 'public_property/details',
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
				$(".btn-editone").data("area",["1000px","450px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-people_id").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
        },
        edit: function () {
            Controller.api.bindevent();
			$("#c-people_id").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
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