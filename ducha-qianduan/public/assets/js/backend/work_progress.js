define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'work_progress/index' + location.search,
                    add_url: 'work_progress/add',
                    edit_url: 'work_progress/edit',
                    del_url: 'work_progress/del',
                    multi_url: 'work_progress/multi',
                    import_url: 'work_progress/import',
                    table: 'work_progress',
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
                        {field: 'id', title: __('Id')},
                        {field: 'task_id', title: __('Task_id')},
                        {field: 'task_name', title: __('Task_name'), operate: 'LIKE'},
                        {field: 'stauts', title: __('Stauts'), operate: 'LIKE'},
                        {field: 'file', title: __('File'), operate: false},
                        {field: 'accomplish_time', title: __('Accomplish_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'updatetime', title: __('Updatetime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'field1', title: __('Field1'), operate: 'LIKE'},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
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