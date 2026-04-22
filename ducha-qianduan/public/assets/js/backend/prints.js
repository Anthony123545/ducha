define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["850px","670px"]);
            
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'prints/index' + location.search,
                    add_url: 'prints/add',
                    edit_url: 'prints/edit',
                    del_url: 'prints/del',
                    multi_url: 'prints/multi',
                    import_url: 'prints/import',
                    table: 'prints',
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
                        {field: 'type', title: __('Type'), operate: 'LIKE'},
                        {field: 'size', title: __('Size'),formatter:function(value){
                            return (value/1024).toFixed(2)+"KB"
                        }, operate:'BETWEEN'},
                        {field: 'secretlevel_id_text', title: __('Secretlevel_id')},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate,buttons:
                            [
                                {name: 'detail', text: '打印', 
                                title: '打印', 
                                icon: 'fa fa-print', 
                                classname: 'btn btn-xs btn-info btn-dialog', 
                                url: function(row){return 'prints_log/add?ids='+row.id;},
                                extend: 'data-area= \'["410px", "600px"]\'',
                                visible:function(row){
                                    if(row.types > 0)return true;
                                    return false;
                                }

                                },
                                {name: 'detail', text: '下载', 
                                title: '下载', 
                                icon: 'fa fa-arrow-circle-o-down', 
                                classname: 'btn btn-xs btn-success', 
                                url: function(row){return row.file;},
                                extend: 'download=""',
                                visible:function(row){
                                    if(row.types == 0)return true;
                                    return false;
                                }

                                }
                            ]
                        }
                    ]
                ]
            });
            $(".btn-editone").data("area",["410px","600px"]);
            // 为表格绑定事件
            Table.api.bindevent(table);
        },
        add: function () {
            Controller.api.bindevent();
            $("#plupload-file").data("upload-success", function(data, ret){
                $('#c-name').val(data.name);
                $('#c-type').val(data.type);
                $('#c-size').val(data.size);
            });
        },
        edit: function () {
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