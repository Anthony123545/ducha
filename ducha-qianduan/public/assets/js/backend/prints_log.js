define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["1400px","480px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'prints_log/index' + location.search,
                    add_url: 'prints_log/add',
                    edit_url: 'prints_log/edit',
                    del_url: 'prints_log/del',
                    multi_url: 'prints_log/multi',
                    import_url: 'prints_log/import',
                    table: 'prints_log',
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
                        {field: 'print_id_text', title: __('Print_id')},
                        
                        {field: 'number', title: __('打印编号')},
                        {field: 'count', title: __('Count')},
                        {field: 'return', title: __('Return'),formatter:function(value){return value==1?'归还':'无需归还';}},
                        {field: 'status', title: __('Status'),formatter:function(value,row){
                            if(row.return == 1){
                                return value==1?'<span class="text-success">已归还</span>':'<span class="text-danger">未归还</span>';
                            }else{
                                return '无需归还';
                            }
                        }},
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'count2', title: __('归还份数')},
                        {field: 'returntime', title: __('归还时间'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        
                        {field: 'remark', title: __('备注说明')},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate,
                            buttons:[
                                {
                                    name: 'detail', text: '归还', 
                                    title: '归还文件', 
                                    icon: 'fa fa-cart-arrow-down', 
                                    classname: 'btn btn-xs btn-info btn-dialog', 
                                    url: function(row){return 'prints_log/return_file?ids='+row.id;},
                                    extend: 'data-area= \'["410px", "600px"]\'',
                                    visible:function(row){
                                        if(row.status == 1 || row.return == 0)return false;
                                        return true;
                                    }
                                },
                                {
                                    name: 'detail', text: '已归还', 
                                    title: '归还文件', 
                                    icon: '', 
                                    classname: 'btn btn-xs btn-success', 
                                    visible:function(row){
                                        if(row.status == 0 || row.return == 0)return false;
                                        return true;
                                    }
                                },
                                {
                                    name: 'detail', text: '无需归还', 
                                    title: '无需归还', 
                                    icon: '', 
                                    classname: 'btn btn-xs btn-grey', 
                                    visible:function(row){
                                        if(row.return == 1)return false;
                                        return true;
                                    }
                                }
                            ]
                        }
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","360px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
            Form.api.bindevent("form[role=form]", function(data, ret){
                console.log(data)
                console.log(ret)
                var url = '/weboffice/WebDocPrint.php?fileType='+data.type+'&fileaddr='+data.file+'&number='+data.number+'&title='+data.title
                window.open(url);
                //这里只要返回false，就会阻止我们的弹窗自动关闭和自动提示
                // return false;
            });
        },
        return_file:function(){
            Controller.api.bindevent();
            $("#c-count").on('keyup',function(){
				var max = $(this).data('max');
				if($(this).val()>max){
                    Toastr.error("该数量不能大于剩余归还份数");
                    $(this).val(max);
				}
			});
            $("#c-count").on('change',function(){
				var max = $(this).data('max');
				if($(this).val()>max){
                    Toastr.error("该数量不能大于剩余归还份数");
                    $(this).val(max);
				}
			});
        }
        ,
        return_multiple: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'prints_log/index' + location.search,
                    add_url: 'prints_log/add',
                    edit_url: 'prints_log/edit',
                    del_url: 'prints_log/del',
                    multi_url: 'prints_log/multi',
                    import_url: 'prints_log/import',
                    table: 'prints_log',
                }
            });

            var table = $("#table");

            // 初始化表格
            table.bootstrapTable({
                url: $.fn.bootstrapTable.defaults.extend.index_url,
                pk: 'id',
                sortName: 'id',
                commonSearch: false,
                showToggle: false,
                showColumns: false,
                visible: false,
                showExport: false,
                search:false,
                columns: [
                    [
                        {checkbox: true},
                        {field: 'order_number', title: __('Id')},
                        {field: 'print_id_text', title: __('Print_id')},
                        {field: 'count', title: __('Count')},
                        {field: 'return', title: __('Return'),formatter:function(value){return value==1?'归还':'无需归还';}},
                        {field: 'status', title: __('Status'),formatter:function(value,row){
                            if(row.return == 1){
                                return value==1?'已归还':'未归还';
                            }else{
                                return '无需归还';
                            }
                        }},
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'returntime', title: __('归还时间'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'remark', title: __('备注说明')},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate,
                            buttons:[
                                {
                                    name: 'detail', text: '归还', 
                                    title: '归还文件', 
                                    icon: 'fa fa-cart-arrow-down', 
                                    classname: 'btn btn-xs btn-info btn-dialog', 
                                    url: function(row){return 'prints_log/return_file?ids='+row.id;},
                                    extend: 'data-area= \'["410px", "600px"]\'',
                                    visible:function(row){
                                        if(row.status == 1)return false;
                                        return true;
                                    }
                                },
                                {
                                    name: 'detail', text: '已归还', 
                                    title: '归还文件', 
                                    icon: '', 
                                    classname: 'btn btn-xs btn-success', 
                                    visible:function(row){
                                        if(row.status == 0)return false;
                                        return true;
                                    }
                                }
                            ]
                        }
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
        }
        ,
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