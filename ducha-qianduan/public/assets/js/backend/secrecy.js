define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["1000px","360px"]);
            $(".btn-edit").data("area",["1000px","360px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'secrecy/index' + location.search,
                    add_url: 'secrecy/add',
                    edit_url: 'secrecy/edit',
                    del_url: 'secrecy/del',
                    multi_url: 'secrecy/multi',
                    import_url: 'secrecy/import',
                    table: 'secrecy',
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
                        {field: 'secrecy_type_text', title: __('Secrecy_type')},
                        {field: 'number', title: __('Number'), operate: 'LIKE'},
                        {field: 'name', title: __('Name'), operate: 'LIKE'},
                        {field: 'kind', title: __('Kind'), operate: 'LIKE'},
                        {field: 'version', title: __('Version'), operate: 'LIKE'},
                        {field: 'confidential', title: __('Confidential'), operate: 'LIKE'},
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'people_id1_text', title: __('People_id1')},
                        {field: 'people_id2_text', title: __('People_id2')},
                        {field: 'start_time', title: __('Start_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'end_time', title: __('End_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'hind_over_time', title: __('Hind_over_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'unit_id_text', title: __('Unit_id')},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
                                
								{
								    name: '查看',
								    text: '查看',
								    title: '查看',
								    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
								    icon: 'fa fa-search',
								    url: 'secrecy/details',
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
				$(".btn-editone").data("area",["1000px","360px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-people_id,#c-people_id1,#c-people_id2").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
        },
        edit: function () {
            Controller.api.bindevent();
			$("#c-people_id,#c-people_id1,#c-people_id2").data("params", function (obj) {
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