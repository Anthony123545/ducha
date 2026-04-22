define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["790px","360px"]);

            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'thought/index' + location.search,
                    add_url: 'thought/add',
                    edit_url: 'thought/edit',
                    del_url: 'thought/del',
                    multi_url: 'thought/multi',
                    import_url: 'thought/import',
                    table: 'thought',
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
                        {field: 'file_name', title: __('Name'), operate: 'LIKE'},
						
						{field: 'type', title: __('Type'), operate: 'LIKE'},
                        {field: 'deadline', title: __('Deadline'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'admin_id_text', title: __('上传人')},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        //{field: 'updatetime', title: __('Updatetime'), operate: 'LIKE'},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
                                
								{
								    name: '查看',
								    text: '查看',
								    title: '查看',
								    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
								    icon: 'fa fa-search',
								    url: 'thought/details',
								    refresh: true
								},
								{
								    name: '上传',
								    text: '上传',
								    title: '上传',
								    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
								    icon: 'fa fa-search',
								    url: 'thought_file/add',
								    refresh: true
								},
								{
								    name: '下载',
								    text: '下载',
								    title: '下载',
								    classname: 'btn btn-xs btn-info btn-view ',
								    icon: 'fa fa-search',
								    url: 'thought/download',
								    refresh: true
								}
                            ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["790px","450px"]);
            })
            table.on('post-body.bs.table',function(){
				$(".btn-dialog").data("area",["790px","450px"]);
            })
        },
        add: function () {
            Controller.api.bindevent();
			$("#faupload-file").data("upload-success", function(data, ret){
			    //这里进行后续操作
			    $('#c-file_name').val(data.name);
				$('#c-type').val(data.type);
			});
        },
        edit: function () {
            Controller.api.bindevent();
			$("#faupload-file").data("upload-success", function(data, ret){
			    //这里进行后续操作
			    $('#c-file_name').val(data.name);
				$('#c-type').val(data.type);
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