define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["700px","450px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'people_leave/index' + location.search,
                    add_url: 'people_leave/add',
                    edit_url: 'people_leave/edit',
                    del_url: 'people_leave/del',
					auth_url: 'people_leave/auth',
                    multi_url: 'people_leave/multi',
                    import_url: 'people_leave/import',
                    table: 'people_leave',
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
                        {field: 'people_name', title: __('People_name'), operate: 'LIKE'},
                        //{field: 'people_id', title: __('People_id')},
                        {field: 'leave_id_text', title: __('Leave_id'), formatter: Table.api.formatter.normal},
                        {field: 'start_time', title: __('Start_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'end_time', title: __('End_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'duration', title: __('Duration'), operate: 'LIKE'},
                        {field: 'reason', title: __('Reason'), operate: 'LIKE'},
                        //{field: 'people_id1', title: __('People_id1')},
                        {field: 'people_name1', title: __('People_name1'), operate: 'LIKE'},
                        {field: 'file_name', title: __('File'), operate: false,formatter: Controller.api.formatter.link},
                        {field: 'address', title: __('Address'), operate: 'LIKE'},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        //{field: 'updatetime', title: __('Updatetime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
                                {
                                    name: '审批通过',
                                    text: '审批通过',
                                    title: '审批通过',
									confirm: '确定审核通过吗？',
                                    classname: 'btn btn-xs btn-info btn-view btn-ajax',
                                    icon: 'fa fa-check',
                                    url: 'people_leave/auth',
									visible: function (row) {
										if (row['auth'] === 0) {
											return true;
										} else {
											return false;
										}
									},
                                    refresh: true
                                },
                                {
                                    name: '审批已通过',
                                    text: '审批已通过',
                                    title: '审批已通过',
                                    classname: 'btn btn-xs btn-warning',
                                    icon: 'fa fa-check',
									visible: function (row) {
										if (row['auth'] == 1) {
											return true;
										} else {
											return false;
										}
									},
                                    refresh: true
                                }
                            ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["700px","450px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-leave_id").data("eSelect", function(){
				$.get('people_leave/huoq',{leave_id:$("#c-leave_id_text").val()},function(res){
					if(res.data.name == '休假'){
						$('#c-address').parent().parent().hide();
					}else{
						$('#c-address').parent().parent().show();
					}
					console.log(res);
				});
			    //后续操作
			});
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
		auth: function () {
		    Controller.api.bindevent();
		},
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            },
            formatter: {
                thumb: function (value, row, index) {
                    return '<a href="' + value + '" target="_blank"><img src="' + value + '" alt="" style="max-height:90px;max-width:120px"></a>';
                },
                url: function (value, row, index) {
                    return '<a href="' + row.fullurl + '" target="_blank" class="label bg-green">' + row.url + '</a>';
                },
                link: function (value, row, index) {
                    return '<a href="' + row.file + '" target="_blank" class="label bg-green">' + row.file_name + '</a>';
                },
                filename: function (value, row, index) {
                    return '<div style="width:180px;margin:0 auto;text-align:center;overflow:hidden;white-space: nowrap;text-overflow: ellipsis;">' + Table.api.formatter.search.call(this, value, row, index) + '</div>';
                },
            }
        }
    };
    return Controller;
});