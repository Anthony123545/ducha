define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["1000px","680px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'work_task/index' + location.search,
                    add_url: 'work_task/add',
                    edit_url: 'work_task/edit',
                    del_url: 'work_task/del',
					entering_url:'work_task/entering',
                    multi_url: 'work_task/multi',
                    import_url: 'work_task/import',
                    table: 'work_task',
                }
            });

            var table = $("#table");

            // 初始化表格
            table.bootstrapTable({
                url: $.fn.bootstrapTable.defaults.extend.index_url,
                pk: 'id',
                sortName: 'id',
                search:true,
                escape: false,
                columns: [
                    [
                        {checkbox: true},
                        {field: 'order_number', title: __('Id')},
						{field: 'name', title: __('Name')},
                        {field: 'number', title: __('Number')},
                        {field: 'type', title: __('Type'), operate: '='},
                        {field: 'people_id_text', title: '执行人'},
                        {field: 'start_time', title: __('Start_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'end_time', title: __('End_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'unit_name', title: '发布单位', operate: false},
                        {field: 'contents', title: '描述', operate: false},
                        {field: 'remark', title: '备注', operate: false},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
                                {
                                    name: '进度录入',
                                    text: '进度录入',
                                    title: '进度录入',
                                    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
                                    icon: 'fa fa-calendar-check-o',
                                    url: 'work_progress/add',
                                    visible:function(row){
                                        return row.is_jindu;
                                    },
                                    refresh: true
                                },
								{
								    name: '查看',
								    text: '查看',
								    title: '查看',
								    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
								    icon: 'fa fa-search',
								    url: 'work_task/details',
								    refresh: true
								}
                            ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            //绑定事件
            // $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
            //     var panel = $(this).attr("href");
            //     console.log(panel)
            //     //移除绑定的事件
            //     // $(this).unbind('shown.bs.tab');
            // });

            $('.J_list').click(function(){
                var container = $("#table").data("bootstrap.table").$container;
                var options = $("#table").bootstrapTable('getOptions');
                //这里我们手动将数据填充到表单然后提交
                $("form.form-commonsearch [name='type']", container).val($(this).data('type'));
                $("form.form-commonsearch [name='start_time']", container).val($(this).data('time'));
                $("form.form-commonsearch [name='end_time']", container).val($(this).data('time'));
                $("form.form-commonsearch", container).trigger('submit');
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","800px"]);
			})
        },
        add: function () {

            $("#c-people_id").data("params", function (obj) {
                var unit_id = $('#J_unitselect').val();

                return {"custom[unit_id][0]":"find_in_set","custom[unit_id][1]":unit_id};
                // return {custom: {unit_id: $("#J_unitselect").val()}};
            });

            $("#faupload-file").data("upload-success", function(data, ret){
                //这里进行后续操作
                $('#c-file_name').val(data.name);
            });

            Controller.api.bindevent();
        },
        edit: function () {
            $("#c-people_id").data("params", function (obj) {
                var unit_id = $('#J_unitselect').val();
                return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
            });
            $("#faupload-file").data("upload-success", function(data, ret){
                //这里进行后续操作
                $('#c-file_name').val(data.name);
            });
            Controller.api.bindevent();
        },
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            }
        }
    };
    return Controller;
});