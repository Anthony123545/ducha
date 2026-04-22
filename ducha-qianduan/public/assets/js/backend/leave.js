define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            $(".btn-add").data("area",["800px","560px"]);
            Table.api.init({
                extend: {
                    index_url: 'leave/index' + location.search,
                    add_url: 'leave/add',
                    edit_url: 'leave/edit',
                    del_url: 'leave/del',
                    multi_url: 'leave/multi',
                    import_url: 'leave/import',
                    table: 'leave',
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
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'start_time', title: __('Start_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'end_time', title: __('End_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'leave_type', title: __('Leave_type'), searchList: {"正课外出":__('正课外出'),"周末外出":__('周末外出'),"特殊类型":__('特殊类型')}, formatter: Table.api.formatter.normal},
                        {field: 'departure_time', title: __('Departure_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'return_time', title: __('Return_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        //{field: 'return_status', title: __('Return_status'), operate: 'LIKE', formatter: Table.api.formatter.status},
                        {field: 'is_return', title: __('Is_return'), searchList: {"0":__('未归队'),"1":__('已归队')}, formatter: Table.api.formatter.normal},
                        //{field: 'updatetime', title: __('Updatetime'), operate: 'LIKE'},
                        //{field: 'admin_id_text', title: __('记录人')},
                        {field: 'people_id1_text', title: __('People_id1')},
                        {field: 'status1', title: __('一级审批状态'), formatter: Table.api.formatter.normal,formatter:function(index,row){
                            if(row.status1 == 1){
                                if(!row.contents1)return '<span class="text-success">已审批</span>'
                            }else if(row.status1 == 2){
                                return '<span class="text-danger">已驳回</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.contents+'"></i>'
                            }else{
                                return '未处理'
                            }
                        }},
                        
                        //{field: 'unit_id2_text', title: __('Unit_id2')},
                        {field: 'people_id2_text', title: __('People_id2')},
                        {field: 'status2', title: __('二级审批状态'), formatter: Table.api.formatter.normal,formatter:function(index,row){
                            if(row.status2 == 1){
                                if(!row.contents2)return '<span class="text-success">已审批</span>'
                            }else if(row.status2 == 2){
                                return '<span class="text-danger">已驳回</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.contents+'"></i>'
                            }else{
                                return '未处理'
                            }
                        }},
                        //{field: 'unit_id3_text', title: __('Unit_id3')},
                        {field: 'people_id3_text', title: __('People_id3')},
                        {field: 'status3', title: __('三级审批状态'), formatter: Table.api.formatter.normal,formatter:function(index,row){
                            if(row.status3 == 1){
                                if(!row.contents3)return '<span class="text-success">已审批</span>'
                            }else if(row.status3 == 2){
                                return '<span class="text-danger">已驳回</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.contents+'"></i>'
                            }else{
                                return '未处理'
                            }
                        }},
                        {field: 'people_id4_text', title: __('People_id4')},
                        {field: 'status4', title: __('四级审批状态'), formatter: Table.api.formatter.normal,formatter:function(index,row){
                            if(row.status4 == 1){
                                if(!row.contents4)return '<span class="text-success">已审批</span>'
                            }else if(row.status4 == 2){
                                return '<span class="text-danger">已驳回</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.contents+'"></i>'
                            }else{
                                return '未处理'
                            }
                        }},
                        {field: 'statusd', title: __('总审批状态'), searchList: {"0":__('未审批'),"1":__('审批已通过'),"2":__('审批不通过')}, formatter: Table.api.formatter.normal},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
								{
									name: '审批',
									text: '审批',
									title: '审批',
									classname: 'btn btn-xs btn-info btn-view btn-dialog ',
									icon: 'fa fa-search',
									url: function(row){
									    var url = 'leave/auth?ids='+row.id;
									    if(row.status1==0){
									        url+='&step=1';
									    }else if(row.status2==0){
									        url+='&step=2';
									    }else if(row.status3==0){
									        url+='&step=3';
									    }else if(row.status4==0){
									        url+='&step=4';
									    }
									    return url;
									},
									extend: 'data-area=\'["500px","80%"]\'',
									refresh: true,
									visible:function(row){
									    if(row.status1==0 && Config.admin.id == row.people_id1){
									        return true;
									    }else if(row.status2==0 && Config.admin.id == row.people_id2 && row.status1==1){
									        return true;
									    }else if(row.status3==0 && Config.admin.id == row.people_id3 && row.status1==1 && row.status2==1){
									        return true;
									    }else if(row.status4==0 && Config.admin.id == row.people_id4 && row.status1==1 && row.status2==1 && row.status3==1){
									        return true;
									    }
									    return false;
									}
								},
								{
									name: '归队',
									text: '归队',
									title: '归队',
									classname: 'btn btn-xs btn-info btn-ajax',
									confirm: '确定归队吗？',
									icon: 'fa fa-search',
									url: function(row){
									    var url = 'leave/rejoin?ids='+row.id;
									    return url;
									},
									refresh: true,
									visible:function(row){
									    if(row.return_time != '' && row.statusd == 1 && row.is_return == 0){
									        return true;
									    }
									    return false;
									}
								}
							],formatter: function(value,row,index){
                                var that = $.extend({},this);
                                var table = $(that.table).clone(true);
                                if(row.people_id1=="1" || row.people_id1=="2"){
                                    $(table).data("operate-edit",null);
                                    that.table = table;
                                }
                                return Table.api.formatter.operate.call(that,value,row,index)
                                //console.log(value);
                                //console.log(row);
                                //console.log(index);
                            }
                            
                        }
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);            
             table.on('post-body.bs.table',function(){
                 $(".btn-editone").data("area",["800px","420px"]);
             })
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-people_id").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
        },
        edit: function () {
            Controller.api.bindevent();
			$("#c-people_id").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
        },
        auth: function () {
            Controller.api.bindevent();
            $('#J_ok').click(function(){
                var id = $('#id').val();
                Layer.confirm('确定要通过吗？',{shade:false},function(index){
                    $.get('leave/auth_status',{ids:id,status:1},function(res){
                        parent.Layer.closeAll();
                        parent.$("a.btn-refresh").trigger("click");
                        parent.Layer.msg(res.msg);
                    });
                });
            });
            
            $('#J_no').click(function(){
                var id = $('#id').val();
                Layer.prompt({
                    title:'驳回原因'
                },function(val,index){
                    if(val){
                        $.get('leave/auth_status',{ids:id,status:2,contents:val},function(res){
                            parent.Layer.closeAll();
                            parent.$("a.btn-refresh").trigger("click");
                            parent.Layer.msg(res.msg);
                        });
                    }else{
                        Layer.msg('请输入原因');
                    }
                    
                
                })
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