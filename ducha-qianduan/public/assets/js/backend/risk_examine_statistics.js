define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'risk_examine_statistics/index' + location.search,
                    // add_url: 'risk_examine_question/add',
                    // edit_url: 'risk_examine_question/edit',
                    // del_url: 'risk_examine_question/del',
                    // multi_url: 'risk_examine_question/multi',
                    // import_url: 'risk_examine_question/import',
                    table: 'risk_examine_statistics',
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
                        // {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        // {field: 'updatetime', title: __('Updatetime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        //{field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
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