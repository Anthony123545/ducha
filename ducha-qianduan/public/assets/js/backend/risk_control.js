define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["800px","500px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'risk_control/index' + location.search,
                    add_url: 'risk_control/add',
                    edit_url: 'risk_control/edit',
                    del_url: 'risk_control/del',
                    multi_url: 'risk_control/multi',
                    import_url: 'risk_control/import',
                    table: 'risk_control',
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
						{field: 'title', title: __('Title'), operate: 'LIKE'},
                        {field: 'start_time', title: __('Start_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'end_time', title: __('End_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'describe', title: __('风险描述'), operate: false},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
								{
								    name: '下载',
								    text: '下载',
								    title: '下载',
								    classname: 'btn btn-xs btn-info btn-view ',
								    icon: 'fa fa-search',
								    url: 'risk_control/download',
								    refresh: true
								}
                            ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["800px","600px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
			$("#faupload-file").data("upload-success", function(data, ret){
			    //这里进行后续操作
			    $('#c-file_name').val(data.name);
			});
        },
        edit: function () {
            Controller.api.bindevent();
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