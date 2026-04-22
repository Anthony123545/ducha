define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["710px","360px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'risk_examine/index' + location.search,
                    add_url: 'risk_examine/add',
                    del_url: 'risk_examine/del',
                    import_url: 'risk_examine/import',
                    table: 'risk_examine',
                }
            });

            var table = $("#table");
            var status = Config.status==1?true:false;
			function stringToEntity(str,radix){
				let arr=str.split('')
				radix=radix||0
				let tmp=arr.map(item=>
					`&#${(radix?'x'+item.charCodeAt(0).toString(16):item.charCodeAt(0))};`).join('')
				console.log(`'${str}' 转实体为 '${tmp}'`)
				return tmp
			}
			function entityToString(entity){
				let entities=entity.split(';')
				entities.pop()
				let tmp=entities.map(item=>String.fromCharCode(
					item[2]==='x'?parseInt(item.slice(3),16):parseInt(item.slice(2)))).join('')
				console.log(`'${entity}' 转字符串为 '${tmp}'`)
				return tmp
			}
            // 初始化表格
            table.bootstrapTable({
                url: $.fn.bootstrapTable.defaults.extend.index_url,
                pk: 'id',
                sortName: 'id',
                onLoadSuccess:function(data){
                },
                columns: [
                    [
                        {checkbox: true},
                        {field: 'qstate', title: __('状态'),formatter:function(val,row){
						    if(val == 1){
						        return '<span class="fa fa-circle text-success"></span>'
						    }else if(val == 2){
						        return '<span class="fa fa-circle text-danger"></span>'
						    }else if(val == 0){
						        if(row.plan){
						            return '<span class="fa fa-circle text-info"></span>'
						        }else{
						            return '<span class="fa fa-circle text-warning"></span>'
						        }
						        
						    }
						},visible:status
						    
						},
                        {field: 'order_number', title: __('序号')},
        //                 {field: 'st', title: __('状态'),operate:false,formatter:function(index,row){
        //                     if(val == 1){
						  //      return '<span class="text-success"></span>'
						  //  }else if(val == 2){
						  //      return '<span class="text-danger"></span>'
						  //  }else if(val == 0){
						  //      if(row.plan){
						  //          return '<span class="text-info"></span>'
						  //      }else{
						  //          return '<span class="text-warning"></span>'
						  //      }
						        
						  //  }
        //                 }},
                        // {field: 'name', title: __('Name'), operate: 'LIKE'},
                        // {field: 'inspection_team_name', title: __('Inspection_team'), operate: 'LIKE'},
                        {field: 'unit_id_text', title: __('Unit_id')},
						{field: 'people_id_text', title: __('People_id'),visible:status},
                        {field: 'examine_time', title: __('Examine_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        //{field: 'start_time', title: __('Start_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                       //{field: 'plus', title: __('加分')},
						{field: 'minus', title: __('分值'),formatter:function(value,row){
							if(row.mode =='inc'){
								return '<span class="text-success">'+value+'</span>';
							}else{
								return '<span class="text-danger">'+value+'</span>'
							}
						}},
						{field: 'question', title: __('事由')},
						{field: 'qstate', title: __('状态'),formatter:function(val,row){
						    if(val == 1){
						        return '<span class="fa text-success">已通过</span>'
						    }else if(val == 2){
						        return '<span class="fa text-danger">未通过</span>'
						    }else if(val == 0){
						        if(row.plan){
						            return '<span class="fa text-info">待验收</span>'
						        }else{
						            return '<span class="fa text-warning">待处理</span>'
						        }
						        
						    }
						},visible:status
						    
						},
						{field: 'time_frame', title: __('整改时限'),visible:status},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
                                
								{
								    name: '问题整改',
								    text: '问题整改',
								    title: '问题整改',
								    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
								    icon: 'fa fa-search',
								    url: 'risk_examine/mend',
                                    //extend: 'data-area=\'["1000px","80%"]\'',
									visible: function (row) {
										if (row['state'] == 0 && row.mode == 'dec') {
											return true;
										} else {
											return false;
										}
									},
								    refresh: true
								},
								{
								    name: '审批',
								    text: '审批',
								    title: '审批',
								    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
								    icon: 'fa fa-search',
								    //url: 'risk_examine_question/examine',
									url:function(row){
										return 'risk_examine_question/index?ids='+row['id'];
									},
								    //extend: 'data-area=\'["1000px","80%"]\'',
									visible: function (row) {
										if (row['state'] == 1) {
											return true;
										} else {
											return false;
										}
									},
								    refresh: true
								}
                            ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });
			$(document).on("mouseover mouseout",'.J_stips',function(){
				if(event.type == "mouseover"){
					//鼠标悬浮
					$(this).find('.stips').show();
				}else if(event.type == "mouseout"){
					//鼠标离开
					$(this).find('.stips').hide();
				}
			});
            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["750px","480px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
            $(document).on("fa.event.appendfieldlist", "#add-form .btn-append", function (e, obj) {
                Form.events.plupload(obj);
                Form.events.selectpage(obj);
                Form.events.selectpicker(obj);
				$("#faupload-readme_file").data("upload-success", function(data, ret){
				    //这里进行后续操作
				    $('#c-file_name').val(data.name);
				});
            });
			$("#c-minus").on('change',function(){
				var max = 0;
				if($(this).val()<max){
			        Toastr.error("分值不能为负数");
			        $(this).val(max);
				}
			});
        },
        addplus: function () {
            Controller.api.bindevent();
            $(document).on("fa.event.appendfieldlist", "#add-form .btn-append", function (e, obj) {
                Form.events.plupload(obj);
                Form.events.selectpage(obj);
                Form.events.selectpicker(obj);
				$("#faupload-readme_file").data("upload-success", function(data, ret){
				    //这里进行后续操作
				    $('#c-file_name').val(data.name);
				});
            });
            $("#c-minus").on('change',function(){
				var max = 0;
				if($(this).val()<max){
			        Toastr.error("分值不能为负数");
			        $(this).val(max);
				}
			});
        },
//         edit: function () {
//             Controller.api.bindevent();
//             $(document).on("fa.event.appendfieldlist", "#edit-form .btn-append", function (e, obj) {
//                 Form.events.plupload(obj);
//                 Form.events.selectpage(obj);
//                 Form.events.selectpicker(obj);
// 				$("#faupload-readme_file").data("upload-success", function(data, ret){
// 			    //这里进行后续操作
// 			    $('#c-file_name').val(data.name);
// 			});
//             });
			
//         },
		mend: function () {
		    Controller.api.bindevent();
		    $(document).on("fa.event.appendfieldlist", "#mend-form .btn-append", function (e, obj) {
		        Form.events.plupload(obj);
		        Form.events.selectpage(obj);
		        Form.events.selectpicker(obj);
				$(".wjfile").data("upload-success", function(data, ret){
					console.log(data.name)
				    //这里进行后续操作
				    $('#c-file_name'+$(this).data('id')).val(data.name);
					$('#c-file_name'+$(this).data('id')).trigger("change");
					// $('#c-file_name'+$(this).data('id'))
				});
		    });
		},
		examine: function () {
		    Controller.api.bindevent();
		    $(document).on("fa.event.appendfieldlist", "#examine-form .btn-append", function (e, obj) {
		        Form.events.plupload(obj);
		        Form.events.selectpage(obj);
		        Form.events.selectpicker(obj);
				$(".wjfile").data("upload-success", function(data, ret){
					console.log(data.name)
				    //这里进行后续操作
				    $('#c-file_name'+$(this).data('id')).val(data.name);
					// $('#c-file_name'+$(this).data('id'))
				});
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