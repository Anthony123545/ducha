define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["1400px","480px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'prints_return_log/index' + location.search,
                    add_url: 'prints_return_log/add',
                    edit_url: 'prints_return_log/edit',
                    del_url: 'prints_return_log/del',
                    multi_url: 'prints_return_log/multi',
                    import_url: 'prints_return_log/import',
                    table: 'prints_return_log',
                }
            });

            var table = $("#table");

            // 初始化表格
            table.bootstrapTable({
                url: $.fn.bootstrapTable.defaults.extend.index_url,
                pk: 'id',
                sortName: 'id',
                search:false,
                commonSearch:false,
                columns: [
                    [
                        {checkbox: true},
                        {field: 'order_number', title: __('Id'),operate:false},
                        {field: 'print_id_text', title: __('Print_id'),operate:false},
                        {field: 'number', title: __('打印编号'),operate:false},
                        {field: 'count', title: __('Return_count'),operate:false},
                        {field: 'remark', title: __('备注说明'),operate:false},
                        {field: 'admin_id_text', title: __('Admin_id'),operate:false},
                        {field: 'returntime', title: __('Returntime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            $('#c-number').on('keyup',function(event){
                var _this = this;
                if(event.keyCode == 13){
                    var number = $(this).val();
                    $.get('prints_log/get_count',{number:number},function(res){
                        if(res.code==1){
                            Layer.confirm('该记录剩余 '+res.data+' 份文件未归还，是否全部归还？',function(index){
                                Layer.close(index);
                                $('#c-count2').val(res.data);
                                return_file();
                            });
                        }else{
                            $(_this).val('');
                            $('#c-count2').val(1);
                            Toastr.error(res.msg);
                        }
                    });
                }
            });

            $('#submit').on('click',function(event){
                return_file();
            });

            function return_file(){
                var number = $('#c-number').val();
                var count2 = $('#c-count2').val();
                if(!count2)Toastr.error('请输入归还份数');
                $.post('prints_return_log/add',{row:{number:number,count:count2}},function(ress){
                    if(ress.code==1){
                        $('#c-number').val('');
                        table.bootstrapTable('refresh');
                        Toastr.success('归还成功');
                    }else{
                        Toastr.error(ress.msg);
                    }
                });
            }

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1200px","680px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
        },
        add: function () {
            Controller.api.bindevent();
        },
        return_multiple: function () {
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