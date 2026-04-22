define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {



    var Controller = {

        index: function () {

            $(".btn-add").data("area",["820px","480px"]);

            // 初始化表格参数配置

            Table.api.init({

                extend: {

                    index_url: 'risk_live/index' + location.search,

                    add_url: 'risk_live/add',
                    del_url: 'risk_live/del',

                    import_url: 'risk_live/import',

                    table: 'risk_live',

                }

            });



            var table = $("#table");
            var status = Config.status==1?true:false;

            // 初始化表格

            table.bootstrapTable({

                url: $.fn.bootstrapTable.defaults.extend.index_url,

                pk: 'id',

                sortName: 'id',

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

						{field: 'people_id_text', title: __('负责人'), operate: 'LIKE',visible:false},

						{field: 'unit_id1_text', title: __('Unit_id1')},

                        {field: 'type_text', title: __('Type'), operate: 'LIKE'},

                        {field: 'examine_time', title: __('Examine_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},

                        {field: 'address', title: __('Address'), operate: 'LIKE'},

                        {field: 'unit_id_text', title: __('Unit_id')},

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
						
                        {field: 'time_limit', title: __('整改时限'),visible:status},


                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [

                                {

                                    name: '问题整改',

                                    text: '问题整改',

                                    title: '问题整改',

                                    classname: 'btn btn-xs btn-info btn-view btn-dialog ',

                                    icon: 'fa fa-search',

                                    url: 'risk_live/mend',

                                    extend: 'data-area=\'["810px","76%"]\'',

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

								    name: '查看及审批',

								    text: '查看及审批',

								    title: '查看及审批',

								    classname: 'btn btn-xs btn-info btn-view btn-dialog ',

								    icon: 'fa fa-search',

									url:function(row){

										return 'risk_live_question/index?ids='+row['id'];

									},

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

				$(".btn-editone").data("area",["820px","480px"]);

			})

        },

        add: function () {

            Controller.api.bindevent();
            $("#c-minus").on('change',function(){
				var max = 0;
				if($(this).val()<max){
			        Toastr.error("分值不能为负数");
			        $(this).val(max);
				}
			});
        },

        // edit: function () {

        //     Controller.api.bindevent();

        // },

		addplus: function () {

		    Controller.api.bindevent();
		    
		    $("#c-minus").on('change',function(){
				var max = 0;
				if($(this).val()<max){
			        Toastr.error("分值不能为负数");
			        $(this).val(max);
				}
			});

		    $(document).on("fa.event.appendfieldlist", "#add-form .btn-append", function (e, obj) {

		        Form.events.plupload(obj);

		        Form.events.selectpage(obj);

		        Form.events.selectpicker(obj);

				$("#faupload-readme_file").data("upload-success", function(data, ret){

				    //这里进行后续操作

				    $('#c-file_name').val(data.name);

				});

		    });

		

		},

		mend: function () {

		    Controller.api.bindevent();

		    $(document).on("fa.event.appendfieldlist", "#edit-form .btn-append", function (e, obj) {

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

		examine:function(){

			

		},

        api: {

            bindevent: function () {

                Form.api.bindevent($("form[role=form]"));

            }

        }

    };

    return Controller;

});