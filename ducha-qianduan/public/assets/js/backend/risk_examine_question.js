define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'risk_examine_question/index' + location.search,
                    // add_url: 'risk_examine_question/add',
                    // edit_url: 'risk_examine_question/edit',
                    // del_url: 'risk_examine_question/del',
                    // multi_url: 'risk_examine_question/multi',
                    // import_url: 'risk_examine_question/import',
                    table: 'risk_examine_question',
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
                        //{field: 'risk_examine_id', title: __('Risk_examine_id')},
                        {field: 'question', title: __('问题')},
                        {field: 'plan', title: __('措施')},
                        //{field: 'status', title: __('Status'), searchList: {"未完成":__('未完成'),"已完成":__('已完成')}, formatter: Table.api.formatter.status},
                        // {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        // {field: 'updatetime', title: __('Updatetime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        //{field: 'file', title: __('File'), formatter: Controller.api.formatter.thumb, operate: false},
                        {field: 'picture', title: __('图片'), formatter: Controller.api.formatter.thumb, operate: false},
                        {field: 'file_name', title: __('文件'), operate: 'LIKE',formatter: Controller.api.formatter.link},
                        
                        {field: 'time_limit', title: __('整改时限')},
                        // {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        // {field: 'updatetime', title: __('Updatetime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
							{
								name: '审批',
								text: '审批',
								title: '审批',
								classname: 'btn btn-xs btn-info btn-view btn-dialog ',
								icon: 'fa fa-search',
								url: 'risk_examine_question/examine',
								extend: 'data-area=\'["450px","70%"]\'',
								visible: function (row) {
									if (row['state'] == 0) {
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
								classname: 'btn btn-xs btn-info btn-view',
								icon: 'fa',
								//url: 'risk_live_question/examine',
								//extend: 'data-area=\'["1000px","80%"]\'',
								visible: function (row) {
									if (row['state'] == 1) {
										return true;
									} else {
										return false;
									}
								},
								refresh: true
							},
							{
								name: '审批不通过',
								text: '审批不通过',
								title: '审批不通过',
								classname: 'btn btn-xs btn-info btn-view',
								icon: 'fa',
								//url: 'risk_live_question/examine',
								//extend: 'data-area=\'["1000px","80%"]\'',
								visible: function (row) {
									if (row['state'] == 2) {
										return true;
									} else {
										return false;
									}
								},
								refresh: true
							},{
								name: '重新整改',
								text: '重新整改',
								title: '重新整改',
								classname: 'btn btn-xs btn-info btn-dialog',
								icon: 'fa',
								//url: 'risk_live_question/examine1',
								url:function(row){
									return 'risk_examine_question/examine1?ids='+row['id'];
								},
								//extend: 'data-area=\'["1000px","80%"]\'',
								visible: function (row) {
									if (row['state'] == 2) {
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
        },
        add: function () {
            Controller.api.bindevent();
        },
        edit: function () {
            Controller.api.bindevent();
        },
		examine: function () {
		    Controller.api.bindevent();
		},
		examine1: function () {
		    Controller.api.bindevent();

			$("#faupload-file").data("upload-success", function(data, ret){

			    //这里进行后续操作

			    $('#c-file_name').val(data.name);

			});
		},
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            },
            formatter: {
                thumb: function (value, row, index) {
                    return '<a href="' + value + '" class="btn-dialog"><img src="' + value + '" alt="" style="height:90px;width:120px"></a>';
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