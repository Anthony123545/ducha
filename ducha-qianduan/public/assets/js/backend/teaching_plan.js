define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["988px","676px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'teaching_plan/index' + location.search,
                    add_url: 'teaching_plan/add',
                    edit_url: 'teaching_plan/edit',
                    del_url: 'teaching_plan/del',
                    multi_url: 'teaching_plan/multi',
                    import_url: 'teaching_plan/import',
                    table: 'teaching_plan',
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
                        {field: 'title', title: __('Title'), operate: 'LIKE'},
                        {field: 'type', title: __('Type'), searchList: {"文档":__('文档'),"专题":__('专题')}, formatter: Table.api.formatter.normal},
                        {field: 'unit_id_text', title: __('Unit_id')},
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'address', title: __('Address'), operate: 'LIKE'},
                        {field: 'start_time', title: __('Start_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'end_time', title: __('End_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'join_number', title: __('Join_number')},
                        {field: 'no_join_number', title: __('No_join_number')},
                        //{field: 'people_ids_text', title: __('People_ids'), operate: 'LIKE'},
                        //{field: 'file', title: __('File'), operate: false},
                        {field: 'file_name', title: __('File_name'), operate: 'LIKE'},
                        {field: 'write_time', title: __('记录时间'), operate: 'LIKE'},
                        //{field: 'updatetime', title: __('Updatetime'), operate: 'LIKE'},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
								{
								    name: '下载',
								    text: '下载',
								    title: '下载',
								    classname: 'btn btn-xs btn-info btn-view ',
								    icon: 'fa fa-search',
								    url: 'teaching_plan/download',
								    refresh: true
								}
                            ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["988px","676px"]);
            })
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-people_ids,#c-people_id").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
			$("#faupload-file").data("upload-success", function(data, ret){
			    //这里进行后续操作
			    $('#c-file_name').val(data.name);
			});
        },
        edit: function () {
            Controller.api.bindevent();
			$("#c-people_ids,#c-people_id").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
			$("#faupload-file").data("upload-success", function(data, ret){
			    //这里进行后续操作
			    $('#c-file_name').val(data.name);
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