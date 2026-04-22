define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area", ["730px", "500px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'recuperate/index' + location.search,
                    add_url: 'recuperate/add',
                    //edit_url: 'recuperate/edit',
                    del_url: 'recuperate/del',
                    multi_url: 'recuperate/multi',
                    import_url: 'recuperate/import',
                    table: 'recuperate',
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
                        {field: 'job', title: __('岗位')},
                        {field: 'type_text', title: __('Type')},
                        {field: 'number', title: __('Number'), operate: 'LIKE'},
                        {field: 'start_time', title: __('Start_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'end_time', title: __('End_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'people_id1_text', title: __('People_id1')},
                        {field: 'status1', title: __('一级审批状态'), formatter: Table.api.formatter.normal,formatter:function(index,row){
                            if(row.status1 == 1){
                                if(!row.people1_remark)return '<span class="text-success">已审批</span>'
                            }else if(row.status1 == 0){
                                return '<span class="text-danger">已驳回</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.people1_remark+'"></i>'
                            }else{
                                return '未处理'
                            }
                        }},
                        //{field: 'unit_id2_text', title: __('Unit_id2')},
                        {field: 'people_id2_text', title: __('People_id2')},
                        {field: 'status2', title: __('二级审批状态'), formatter: Table.api.formatter.normal,formatter:function(index,row){
                            if(row.status2 == 1){
                                if(!row.people1_remark)return '<span class="text-success">已审批</span>'
                            }else if(row.status2 == 0){
                                return '<span class="text-danger">已驳回</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.people2_remark+'"></i>'
                            }else{
                                return '未处理'
                            }
                        }},
                        //{field: 'unit_id3_text', title: __('Unit_id3')},
                        {field: 'people_id3_text', title: __('People_id3')},
                        {field: 'status3', title: __('三级审批状态'), formatter: Table.api.formatter.normal,formatter:function(index,row){
                            if(row.status3 == 1){
                                if(!row.people1_remark)return '<span class="text-success">已审批</span>'
                            }else if(row.status3 == 0){
                                return '<span class="text-danger">已驳回</span> <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.people3_remark+'"></i>'
                            }else{
                                return '未处理'
                            }
                        }},
                        //{field: 'remark', title: __('Remark'), operate: 'LIKE'},
                        {field: 'createtime', title: __('申请时间'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
								{
									name: '审核',
									text: '审核',
									title: '审核',
									classname: 'btn btn-xs btn-info btn-view btn-dialog ',
									icon: 'fa fa-search',
									url: function(row){
									    var url = 'recuperate/auth?ids='+row.id;
									    if(row.status1==2){
									        url+='&step=1';
									    }else if(row.status2==2){
									        url+='&step=2';
									    }else if(row.status3==2){
									        url+='&step=3';
									    }
									    return url;
									},
									extend: 'data-area=\'["450px","80%"]\'',
									refresh: true,
									visible:function(row){
									    if(row.status1==2 && Config.admin.id == row.people_id1){
									        return true;
									    }else if(row.status2==2 && Config.admin.id == row.people_id2 && row.status1==1){
									        return true;
									    }else if(row.status3==2 && Config.admin.id == row.people_id3 && row.status1==1 && row.status2==1){
									        return true;
									    }
									    return false;
									}
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
            $("#c-people_id").data("params", function (obj) {
                var unit_id = $('#J_unitselect').val();
                return {"custom[unit_id][0]":"find_in_set","custom[unit_id][1]":unit_id};
                // return {custom: {unit_id: $("#J_unitselect").val()}};
            });
            $(document).on("fa.event.appendfieldlist", "#add-form .btn-append", function (e, obj) {
                Form.events.selectpage(obj);
                Form.events.datetimepicker(obj);
            });
            $("#c-people_id1").data("params", function (obj) {
                var unit_id = $('#J_unitselect1').val();
                return {"custom[unit_id][0]":"find_in_set","custom[unit_id][1]":unit_id};
                // return {custom: {unit_id: $("#J_unitselect").val()}};
            });
            $("#c-people_id2").data("params", function (obj) {
                var unit_id = $('#J_unitselect2').val();
                return {"custom[unit_id][0]":"find_in_set","custom[unit_id][1]":unit_id};
                // return {custom: {unit_id: $("#J_unitselect").val()}};
            });
            $("#c-people_id3").data("params", function (obj) {
                var unit_id = $('#J_unitselect3').val();
                return {"custom[unit_id][0]":"find_in_set","custom[unit_id][1]":unit_id};
                // return {custom: {unit_id: $("#J_unitselect").val()}};
            });
            $("#c-people_id").data("eSelect", function(){
				$.get('recuperate/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-job').val(res.data.job);
				//console.log(res);
				});
			    //后续操作
			});
        },
        edit: function () {
            Controller.api.bindevent();
            $("#c-people_id").data("params", function (obj) {
                var unit_id = $('#J_unitselect').val();
                return {"custom[unit_id][0]":"find_in_set","custom[unit_id][1]":unit_id};
                // return {custom: {unit_id: $("#J_unitselect").val()}};
            });
            $(document).on("fa.event.appendfieldlist", "#edit-form .btn-append", function (e, obj) {
                Form.events.selectpage(obj);
                Form.events.datetimepicker(obj);
            });
            $("#c-people_id1").data("params", function (obj) {
                var unit_id = $('#J_unitselect1').val();
                return {"custom[unit_id][0]":"find_in_set","custom[unit_id][1]":unit_id};
                // return {custom: {unit_id: $("#J_unitselect").val()}};
            });
            $("#c-people_id2").data("params", function (obj) {
                var unit_id = $('#J_unitselect2').val();
                return {"custom[unit_id][0]":"find_in_set","custom[unit_id][1]":unit_id};
                // return {custom: {unit_id: $("#J_unitselect").val()}};
            });
            $("#c-people_id3").data("params", function (obj) {
                var unit_id = $('#J_unitselect3').val();
                return {"custom[unit_id][0]":"find_in_set","custom[unit_id][1]":unit_id};
                // return {custom: {unit_id: $("#J_unitselect").val()}};
            });
        },
        auth: function () {
            Controller.api.bindevent();
            $('#J_ok').click(function(){
                var id = $(this).data('id');
                Layer.confirm('确定要通过吗？',{shade:false},function(index){
                    $.get('recuperate/auth_status',{ids:id,status:1},function(res){
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
                        $.get('recuperate/auth_status',{ids:id,status:0,remark:val},function(res){
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