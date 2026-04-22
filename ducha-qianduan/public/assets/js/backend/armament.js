define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["1000px","370px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'armament/index' + location.search,
                    add_url: 'armament/add',
                    edit_url: 'armament/edit',
                    del_url: 'armament/del',
                    multi_url: 'armament/multi',
                    import_url: 'armament/import',
                    table: 'armament',
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
                        {field: 'record_date', title: __('Record_date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'metering', title: __('Metering'), operate: 'LIKE'},
                        {field: 'gist', title: __('Gist'), operate: 'LIKE'},
                        {field: 'prepare_number', title: __('Prepare_number')},
                        {field: 'sum', title: __('Sum')},
                        {field: 'intact_number', title: __('Intact_number')},
                        {field: 'repair_number', title: __('Repair_number')},
                        {field: 'send_number', title: __('Send_number')},
                        {field: 'standard', title: __('Standard'), operate: 'LIKE'},
                        {field: 'existing', title: __('Existing'), operate: 'LIKE'},
                        {field: 'flaw', title: __('Flaw'), operate: 'LIKE'},
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'people_id1_text', title: __('People_id1')},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        //{field: 'updatetime', title: __('Updatetime'), operate: 'LIKE'},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [

                                {
                                    name: '查看',
                                    text: '查看',
                                    title: '查看',
                                    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
                                    icon: 'fa fa-search',
                                    url: 'armament/details',
                                    extend: 'data-area=\'["1200px","80%"]\'',
                                    refresh: true
                                }
                            ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","370px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-people_id,#c-people_id1").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
        },
        edit: function () {
            Controller.api.bindevent();
			$("#c-people_id,#c-people_id1").data("params", function (obj) {
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