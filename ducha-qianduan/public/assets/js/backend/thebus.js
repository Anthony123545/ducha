define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["1000px","650px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'thebus/index' + location.search,
                    add_url: 'thebus/add',
                    edit_url: 'thebus/edit',
                    del_url: 'thebus/del',
                    multi_url: 'thebus/multi',
                    import_url: 'thebus/import',
                    table: 'thebus',
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
                        {field: 'st', title: __('状态'),operate:false,formatter:function(index,row){
                            if(row.status == 1){
                                return '<i class="fa fa-circle text-success"></i>'
                            }else if(row.status == 2){
                                return '<i class="fa fa-circle text-info"></i>'
                            }else if(row.status == 3){
                                return '<i class="fa fa-circle text-danger"></i>'
                            }else if(row.status == 4){
                                return '<i class="fa fa-circle text-warning"></i>'
                            }
                            
                        }},
                        {field: 'order_number', title: __('Id')},
                        {field: 'vehiclenumber', title: __('Vehiclenumber'), operate: false},
                        {field: 'vehiclemodel', title: __('Vehiclemodel'), searchList: $.getJSON('category/searchlist?type=motorcycle_type'),visible:false},
                        {field: 'vehiclemodel_text', title: __('Vehiclemodel'), operate: false},
                        {field: 'people_id', title: __('车辆负责人'), operate: false,visible:false},
                        {field: 'people_id_text', title: __('车辆负责人'), operate: false},
                        {field: 'unit_id_text', title: __('车属单位'), operate: false},
                        // {field: 'parkingspacenumber', title: __('Parkingspacenumber'), operate: false},
                        {field: 'contactnumber', title: __('Contactnumber'), operate: false},
                        {field: 'engine_number', title: __('发动机号'), operate: false},
                        {field: 'frame_number', title: __('车架号'), operate: false},
                        {field: 'source', title: __('车辆来源'), operate: false},
                        {field: 'vehiclecolor', title: __('Vehiclecolor'), operate: false},
                        {field: 'date1', title: __('出厂日期'), operate: false},
                        {field: 'status', title: __('车辆状态'),formatter:function(index,row){
                            if(row.status == 1){
                                return '<span class="text-success">在位</span>'
                            }else if(row.status == 2){
                                return '<span class="text-info">值班</span>'
                            }else if(row.status == 3){
                                var str = '<span class="text-danger">出车</span>'
                                if(row.cause)str+=' <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.cause+'"></i>'
                                return str;
                            }else if(row.status == 4){
                                return '<span class="text-warning">待修</span>'
                            }
                        }},
                        // {field: 'etc_brand', title: __('ETC品牌'), operate: false},
                        // {field: 'etc_number', title: __('ETC编码'), operate: false},
                        {field: 'position', title: __('所在位置'), operate: false},
                        {field: 'is_listing', title: __('是否挂牌'), operate: false},
                        {field: 'seat_count', title: __('固定座位数')},
                        // {field: 'remarks', title: __('Remarks'), operate: false},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","650px"]);
			})
        },
        index2: function () {
            $(".btn-add").data("area",["1000px","650px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'thebus/index2' + location.search,
                    add_url: 'thebus/add',
                    edit_url: 'thebus/edit',
                    del_url: 'thebus/del',
                    multi_url: 'thebus/multi',
                    import_url: 'thebus/import',
                    table: 'thebus',
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
                        
                        {field: 'order_number', title: __('Id'), operate: false},
                        {field: 'unit_id_text', title: __('单位'), operate: false},
                        {field: 'people_id_text', title: __('人员'), operate: false},
						{field: 'model', title: __('车辆型号'), operate: false},
                        {field: 'app_unit_id_text', title: __('用车人员单位'), operate: false},
                        {field: 'app_people_id_text', title: __('用车人员姓名'), operate: false},
                        {field: 'contact', title: __('用车人员联系方式'), operate: false},
                        {field: 'cause', title: __('事由'), operate: false},
                        {field: 'route', title: __('出车路线'), operate: false},
                        {field: 'outstatus', title: __('出车状态'),formatter:function(index,row){
                            if(row.outstatus == 1){
                                return '<span class="text-success">已出车</span>'
                            }else if(row.outstatus == 0){
                                return '未出车'
                            }
                        },searchList:{0:'未出车',1:'已出车'},defaultValue:''},
                        {field: 'outtime', title: __('出车时间'),formatter:Table.api.formatter.datetime, operate: false},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate,buttons: [
								{
									name: '出车',
									text: '出车',
									title: '选择司机',
									classname: 'btn btn-xs btn-info btn-view btn-dialog ',
									icon: 'fa fa-automobile',
									extend: 'data-area=\'["800px","50%"]\'',
									refresh: true,
									visible:function(row){
									    if(row.outstatus != 1)return true;
									    return false;
									},
									url:'thebus_apply/outbus'
								},
								{
									name: '查看',
									text: '查看',
									title: '查看',
									classname: 'btn btn-xs btn-info btn-view btn-dialog ',
									icon: 'fa fa-automobile',
									extend: 'data-area=\'["800px","50%"]\'',
									refresh: true,
									visible:function(row){
									    if(row.outstatus != 0)return true;
									    return false;
									},
									url:'thebus_apply/details'
								}
							]}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","650px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
            $("#c-people_id").data("eSelect", function(){
				$.get('duty_basic/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-contactnumber').val(res.data.phone);
				});
			    //后续操作
			});
        },
        edit: function () {
            Controller.api.bindevent();
             $("#c-people_id").data("eSelect", function(){
				$.get('duty_basic/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-contactnumber').val(res.data.phone);
				});
			    //后续操作
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