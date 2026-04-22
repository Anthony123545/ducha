define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
             $(".btn-add").data("area",["1000px","400px"]);
            Table.api.init({
                extend: {
                    index_url: 'thebus_apply/index' + location.search,
                    add_url: 'thebus_apply/add',
                    edit_url: 'thebus_apply/edit',
                    del_url: 'thebus_apply/del',
                    multi_url: 'thebus_apply/multi',
                    import_url: 'thebus_apply/import',
                    table: 'thebus_apply',
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
                        {field: 'people_count', title: __('人数'), operate: false},
                        {field: 'unit_id_text', title: __('Unit_id')},
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'contact', title: __('Contact'), operate: 'LIKE'},
                        {field: 'cause', title: __('Cause'), operate: 'LIKE'},
                        // {field: 'auth_stime', title: __('Auth_stime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        // {field: 'auth_etime', title: __('Auth_etime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'unit_people_id_text', title: __('营级审批人')},
                        {field: 'unit_people_id_state', title: __('营级审批状态'),formatter:function(index,row){
                            if(row.unit_people_state == 1){
                                if(!row.unit_people_remark)return '<span class="text-success">已审批</span>'
                                return '<span class="text-warning">特殊审核</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.unit_people_remark+'"></i>'
                            }else if(row.unit_people_state == 2){
                                return '<span class="text-danger">已驳回</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.unit_people_remark+'"></i>'
                            }else{
                                return '未处理'
                            }
                        }},
                        {field: 'unit_people_id_state', title: __('初审状态'),formatter:function(index,row){
                            if(row.chushen_state == 1){
                                if(!row.chushen_remark)return '<span class="text-success">已审批</span>'
                                return '<span class="text-warning">特殊审核</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.chushen_remark+'"></i>'
                            }else if(row.chushen_state == 2){
                                return '<span class="text-danger">已驳回</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.chushen_remark+'"></i>'
                            }else{
                                return '未处理'
                            }
                        }},
                        {field: 'people1_id_text', title: __('People1_id')},
                        {field: 'people1_state', title: __('一级审批状态'),formatter:function(index,row){
                            if(row.people1_state == 1){
                                if(!row.people1_remark)return '<span class="text-success">已审批</span>'
                                return '<span class="text-warning">特殊审核</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.people1_remark+'"></i>'
                            }else if(row.people1_state == 2){
                                return '<span class="text-danger">已驳回</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.people1_remark+'"></i>'
                            }else{
                                return '未处理'
                            }
                        }},
                        {field: 'people2_id_text', title: __('People2_id')},
                        {field: 'people2_state', title: __('二级审批状态'),formatter:function(index,row){
                            if(row.people2_state == 1){
                                if(!row.people2_remark)return '<span class="text-success">已审批</span>'
                                return '<span class="text-warning">特殊审核</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.people2_remark+'"></i>'
                            }else if(row.people2_state == 2){
                                return '<span class="text-danger">已驳回</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.people2_remark+'"></i>'
                            }else{
                                return '未处理'
                            }
                        }},
                        {field: 'people3_id_text', title: __('People3_id')},
                        {field: 'people3_state', title: __('三级审批状态'),formatter:function(index,row){
                            if(row.people3_state == 1){
                                if(!row.people3_remark)return '<span class="text-success">已审批</span>'
                                return '<span class="text-warning">特殊审核</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.people3_remark+'"></i>'
                            }else if(row.people3_state == 2){
                                return '<span class="text-danger">已驳回</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.people3_remark+'"></i>'
                            }else{
                                return '未处理'
                            }
                        }},
                        // {field: 'admin_id_text', title: __('Admin_id')},
                        // {field: 'createtime', title: __('Createtime'), formatter: Table.api.formatter.datetime},
                        {field: 'updatetime', title: __('Updatetime'), formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate,buttons: [
								{
									name: '审核',
									text: '审核',
									title: '审核',
									classname: 'btn btn-xs btn-info btn-view btn-dialog ',
									icon: 'fa fa-search',
									url: function(row){
									    var url = 'thebus_apply/auth?ids='+row.id;
									    if(row.people1_id_state==0){
									        url+='&step=1';
									    }else if(row.people2_id_state==0){
									        url+='&step=2';
									    }else if(row.people3_id_state==0){
									        url+='&step=3';
									    }
									    return url;
									},
									extend: 'data-area=\'["450px","80%"]\'',
									refresh: true,
									visible:function(row){
									    if(row.unit_people_state==1 && row.chushen_state == 1){
									        if(row.people1_state==0 && Config.admin.id == row.people1_id){
    									        return true;
    									    }else if(row.people2_state==0 && Config.admin.id == row.people2_id && row.people1_state==1){
    									        return true;
    									    }else if(row.people3_state==0 && Config.admin.id == row.people3_id && row.people1_state==1 && row.people2_state==1){
    									        return true;
    									    }
									    }
									    
									    return false;
									}
								},
								{
									name: '审核',
									text: '特殊审核',
									title: '特殊审核',
									classname: 'btn btn-xs btn-warning btn-view btn-click ',
									icon: 'fa fa-search',
									refresh: true,
									visible:function(row){
									    if(row.unit_people_state==1 && row.chushen_state == 1){
    									    if((row.people1_state==0 || row.people2_state==0 || row.people3_state==0) && (row.people1_state!=2 && row.people2_state!=2 && row.people3_state!=2) && Config.admin.id == row.default_people_id){
    									        return true;
    									    }
									    }
									    return false;
									},
									click:function(e,row){
									    var id = row.id;
                                        Layer.prompt({
                                            title:'特殊授权情况'
                                        },function(val,index){
                                            if(val){
                                                $.get('thebus_apply/auth_status',{ids:id,state:1,remark:val},function(res){
                                                    Layer.closeAll();
                                                    $("a.btn-refresh").trigger("click");
                                                    Layer.msg(res.msg);
                                                });
                                            }else{
                                                Layer.msg('请输入特殊授权情况');
                                            }
                                        })
									}
								},
								{
									name: '审核',
									text: '营级审核',
									title: '营级审核',
									classname: 'btn btn-xs btn-warning btn-view btn-click ',
									icon: 'fa fa-search',
									refresh: true,
									visible:function(row){
									    if(row.unit_people_state==0 && row.unit_people_id==Config.admin.id){
									        return true;
									    }
									    return false;
									},
									click:function(e,row){
									    var id = row.id;
									    Layer.confirm('请选择审核状态', {
                                          btn: ['通过', '不通过', '关闭'] //可以无限个按钮
                                          ,btn3: function(index, layero){
                                            
                                          }
                                        }, function(index, layero){
                                            $.get('thebus_apply/tongyong_auth',{ids:id,unit_people_state:1},function(res){
                                                Layer.closeAll();
                                                $("a.btn-refresh").trigger("click");
                                                Layer.msg(res.msg);
                                            });
                                        }, function(index){
                                            Layer.prompt({
                                                title:'请输入不通过原因'
                                            },function(val,index){
                                                if(val){
                                                    $.get('thebus_apply/tongyong_auth',{ids:id,unit_people_state:2,unit_people_remark:val},function(res){
                                                        Layer.closeAll();
                                                        $("a.btn-refresh").trigger("click");
                                                        Layer.msg(res.msg);
                                                    });
                                                }else{
                                                    Layer.msg('请输入不通过原因');
                                                }
                                            })
                                        });
									    
									}
								},
								{
									name: '审核',
									text: '初审',
									title: '初审',
									classname: 'btn btn-xs btn-warning btn-view btn-click ',
									icon: 'fa fa-search',
									refresh: true,
									visible:function(row){
									    if(row.default_people_id==Config.admin.id && row.chushen_state==0 && row.unit_people_state==1){
									        return true;
									    }
									    return false;
									},
									click:function(e,row){
									    var id = row.id;
									    Layer.confirm('请选择审核状态', {
                                          btn: ['通过', '不通过', '关闭'] //可以无限个按钮
                                          ,btn3: function(index, layero){
                                            
                                          }
                                        }, function(index, layero){
                                            $.get('thebus_apply/tongyong_auth',{ids:id,chushen_state:1},function(res){
                                                Layer.closeAll();
                                                $("a.btn-refresh").trigger("click");
                                                Layer.msg(res.msg);
                                            });
                                        }, function(index){
                                            Layer.prompt({
                                                title:'请输入不通过原因'
                                            },function(val,index){
                                                if(val){
                                                    $.get('thebus_apply/tongyong_auth',{ids:id,chushen_state:2,chushen_remark:val},function(res){
                                                        Layer.closeAll();
                                                        $("a.btn-refresh").trigger("click");
                                                        Layer.msg(res.msg);
                                                    });
                                                }else{
                                                    Layer.msg('请输入不通过原因');
                                                }
                                            })
                                        });
									}
								}
							]}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
             table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","400px"]);
			})
			
			
			$('#mu-auth').click(function(){
			    var list = table.bootstrapTable('getSelections');
			    if(list.length == 0){
			        Layer.msg('请选择要审核的数据');
			        return false;
			    }
			    Layer.confirm('是否批量通过初审？',{icon:3},function(index){
			        var list = table.bootstrapTable('getSelections');
    			    var id = [];
    			    for(var i in list){
    			        id.push(list[i].id);
    			    }
    			    $.get('thebus_apply/mu_auth',{ids:id},function(res){
    			        Layer.msg(res.msg);
    			        if(res.code == 1){
    			            table.bootstrapTable('refresh');
    			        }
    			    });
			    });
			    
			});
        },
        add: function () {
            
            $(document).on("change", "#c-thebus_themplate_id", function(){
                $.get('thebus_themplate/getone',{id:$(this).val()},function(res){
                    $('#c-people1_id').val(res.data.people1_id);
                    $('#c-people1_id_text').val(res.data.people1_id_text);
                    $('#c-people2_id').val(res.data.people2_id);
                    $('#c-people2_id_text').val(res.data.people2_id_text);
                    $('#c-people3_id').val(res.data.people3_id);
                    $('#c-people3_id_text').val(res.data.people3_id_text);
                });
            });
            $("#c-people_id").data("params", function (obj) {
                var unit_id = $('#J_unitselect').val();

				return {
					"custom[unit_id][0]": "in",
					"custom[unit_id][1]": unit_id
				};
            });
            
            $("#c-people1_id,#c-people2_id,#c-people3_id").data("params", function (obj) {
			    var custom = {};
			    custom['custom'] = {};
			    custom['custom']['id'] = [];
			    custom['custom']['id'][0] = 'not in';
			    custom['custom']['id'][1] = '1';
			    return custom;
            });
            
            Controller.api.bindevent();
        },
        edit: function () {
            $(document).on("change", "#c-thebus_themplate_id", function(){
                $.get('thebus_themplate/getone',{id:$(this).val()},function(res){
                    $('#c-people1_id').val(res.data.people1_id);
                    $('#c-people2_id').val(res.data.people2_id);
                    $('#c-people3_id').val(res.data.people3_id);
                    $('#c-people1_id').selectPageRefresh();
                    $('#c-people2_id').selectPageRefresh();
                    $('#c-people3_id').selectPageRefresh();
                });
            });
            $("#c-people_id").data("params", function (obj) {
                var unit_id = $('#J_unitselect').val();

				return {
					"custom[unit_id][0]": "in",
					"custom[unit_id][1]": unit_id
				};
            });
            $("#c-people1_id,#c-people2_id,#c-people3_id").data("params", function (obj) {
			    var custom = {};
			    custom['custom'] = {};
			    custom['custom']['id'] = [];
			    custom['custom']['id'][0] = 'not in';
			    custom['custom']['id'][1] = '1';
			    return custom;
            });
            Controller.api.bindevent();
        },
        
        auth: function () {
            Controller.api.bindevent();
            $('#J_ok').click(function(){
                var id = $(this).data('id');
                Layer.confirm('确定要通过吗？',{shade:false},function(index){
                    $.get('thebus_apply/auth_status',{ids:id,state:1},function(res){
                        parent.Layer.closeAll();
                        parent.$("a.btn-refresh").trigger("click");
                        parent.Layer.msg(res.msg);
                    });
                    
                });
            });
            
            $('#J_no').click(function(){
                var id = $(this).data('id');
                Layer.prompt({
                    title:'驳回原因'
                },function(val,index){
                    if(val){
                        $.get('thebus_apply/auth_status',{ids:id,state:1,remark:val},function(res){
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
        
        outbus: function(){
            Controller.api.bindevent();
            $("#c-busnumber").data("params", function (obj) {
                return {custom: {vehiclemodel: $("#c-model").val(),status:1}};
            });
            $(document).on("fa.event.appendfieldlist", ".btn-append", function (e, obj) {
				Form.events.selectpage(obj);
				Form.events.datetimepicker(obj);

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