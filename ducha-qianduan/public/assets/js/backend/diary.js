define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {



	var Controller = {

// 		index: function () {

// 			$(".btn-add").data("area", ["1020px", "775px"]);

// 			// 初始化表格参数配置

// 			Table.api.init({

// 				extend: {

// 					index_url: 'diary/index' + location.search,

// 					add_url: 'diary/add',

// 					edit_url: 'diary/edit',

// 					del_url: 'diary/del',

// 					multi_url: 'diary/multi',

// 					import_url: 'diary/import',

// 					table: 'diary',

// 				}

// 			});



// 			var table = $("#table");



// 			// 初始化表格

// 			table.bootstrapTable({

// 				url: $.fn.bootstrapTable.defaults.extend.index_url,

// 				pk: 'id',

// 				sortName: 'id',

// 				columns: [

// 					[

// 						{
// 							checkbox: true
// 						},

// 						{
// 							field: 'order_number',
// 							title: __('Id')
// 						},

// 						{
// 							field: 'unit_id_text',
// 							title: __('单位')
// 						},

// 						{
// 							field: 'people_id_text',
// 							title: __('People_id')
// 						},

// 						{
// 							field: 'blog_date',
// 							title: __('Blog_date'),
// 							operate: 'LIKE'
// 						},

// 						{
// 							field: 'orgcadre',
// 							title: __('Orgcadre'),
// 							operate: 'LIKE'
// 						},

// 						{
// 							field: 'orgsoldier',
// 							title: __('Orgsoldier'),
// 							operate: 'RANGE'
// 						},

// 						{
// 							field: 'orgcivilian',
// 							title: __('Orgcivilian'),
// 							operate: 'LIKE'
// 						},

// 						// {field: 'lead_text', title: __('Lead'), operate: 'LIKE'},

// 						{
// 							field: 'operate',
// 							title: __('Operate'),
// 							table: table,
// 							events: Table.api.events.operate,
// 							buttons: [

// 								{

// 									name: '查看',

// 									text: '查看',

// 									title: '查看',

// 									classname: 'btn btn-xs btn-info btn-view btn-dialog ',

// 									icon: 'fa fa-search',

// 									url: 'diary/details',

// 									extend: 'data-area=\'["1020px","775px"]\'',

// 									refresh: true

// 								}

// 							],
// 							formatter: Table.api.formatter.operate
// 						}

// 					]

// 				]

// 			});



// 			// 为表格绑定事件

// 			Table.api.bindevent(table);

// 			table.on('post-body.bs.table', function () {

// 				$(".btn-editone").data("area", ["1020px", "1020px"]);

// 			})

// 		},

        index: function () {
            $(".btn-add").data("area", ["1020px", "775px"]);
            // 初始化表格参数配置
            Table.api.init();
            this.table.first();
            this.table.second();
        },
        table: {
            first: function () {
                // 表格1
                var table1 = $("#table1");
                table1.bootstrapTable({
                    url: 'diary/unit_ysrj',
                    toolbar: '#toolbar1',
                    sortName: 'id',
                    search: false,
                    pagination: false,
                    columns: [
                        [
                            {
                                field: 'operate', title: __('单位'), table: table1, events: Table.api.events.operate, buttons: [
                                    {
                                        name: 'log',
                                        title: '',
                                        text: function(row){return row.name},
                                        classname: 'btn btn-success btn-xs btn-click btn-custom',
                                        click: function (e, data) {
                                            console.log(data)
                                            $("#myTabContent2 .form-commonsearch input[name='unit_id']").val(data.id);
                                            $("#myTabContent2 .btn-refresh").trigger("click");
                                        },
                                        visible:function(row){
                                            if(row.isok == 1)return true;
                                            return false;
                                        }
                                    },
                                    {
                                        name: 'log',
                                        title: '',
                                        text: function(row){return row.name},
                                        classname: 'btn btn-danger btn-xs btn-click btn-custom',
                                        click: function (e, data) {
                                            console.log(data)
                                            $("#myTabContent2 .form-commonsearch input[name='unit_id']").val(data.id);
                                            $("#myTabContent2 .btn-refresh").trigger("click");
                                        },
                                        visible:function(row){
                                            if(row.isok == 0)return true;
                                            return false;
                                        }
                                    }
                                ], formatter: Table.api.formatter.operate
                            }
                        ]
                    ]
                });

                // 为表格1绑定事件
                Table.api.bindevent(table1);
            },
            second: function () {
                // 表格2
                var table2 = $("#table2");
                table2.bootstrapTable({
                    url: 'diary/index' + location.search,
                    extend: {
    					index_url: 'diary/index' + location.search,
    
    					add_url: 'diary/add',
    
    					edit_url: 'diary/edit',
    
    					del_url: 'diary/del',
    
    					multi_url: 'diary/multi',
    
    					import_url: 'diary/import',
    
    					table: 'diary',
                    },
                    toolbar: '#toolbar2',
                    sortName: 'id',
                    search: false,
                    				columns: [

					[

						{
							checkbox: true
						},

						{
							field: 'order_number',
							title: __('Id'),operate:false
						},
                        {
							field: 'unit_id',
							title: __('单位ID'),visible:false
						},
						{
							field: 'unit_id_text',
							title: __('单位'),operate:false
						},

						{
							field: 'people_id_text',
							title: __('People_id'),operate:false
						},

						{
							field: 'blog_date',
							title: __('Blog_date'),operate:false
						},

						{
							field: 'orgcadre',
							title: __('Orgcadre'),operate:false
						},

						{
							field: 'orgsoldier',
							title: __('Orgsoldier'),operate:false
						},

						{
							field: 'orgcivilian',
							title: __('Orgcivilian')
							,operate:false
						},

						// {field: 'lead_text', title: __('Lead'), operate: 'LIKE'},

						{
							field: 'operate',
							title: __('Operate'),
							table: table2,
							events: Table.api.events.operate,
							buttons: [

								{

									name: '查看',

									text: '查看',

									title: '查看',

									classname: 'btn btn-xs btn-info btn-view btn-dialog ',

									icon: 'fa fa-search',

									url: 'diary/details',

									extend: 'data-area=\'["1020px","775px"]\'',

									refresh: true

								},
								{

									name: '审批',

									text: '审批',

									title: '审批',

									classname: 'btn btn-xs btn-info btn-view btn-dialog ',

									icon: 'fa fa-search',

									url: 'diary/examine',

									extend: 'data-area=\'["1020px","775px"]\'',

									refresh: true,
									visible:function(row){
									    if(row.examine == 0 && row.people_id1 == Config.admin.id || row.examine == 0 && row.admin_id == Config.admin.id){
									        return true;
									    }else{
									        return false;
									    }
									}

								},
								{

									name: '审批不通过',

									text: '审批不通过',

									title: '审批不通过',

									classname: 'btn btn-xs btn-info',

									icon: 'fa fa-search',

									refresh: true,
									visible:function(row){
									    if(row.examine == 2 && row.people_id1 == Config.admin.id || row.examine == 2 && row.admin_id == Config.admin.id){
									        return true;
									    }else{
									        return false;
									    }
									}

								}

							],
							
							formatter: function(value,row,index){
                                var that = $.extend({},this);
                                var table = $(that.table).clone(true);
                                if(row.examine=="1"){
                                    $(table).data("operate-edit",null);
                                    that.table = table;
                                }
                                return Table.api.formatter.operate.call(that,value,row,index)
                                //console.log(value);
                                //console.log(row);
                                //console.log(index);
                            }
				// 			formatter: Table.api.formatter.operate
						}

					]

				]
                });

                // 为表格2绑定事件
                Table.api.bindevent(table2);
                table2.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["1020px", "775px"]);
            })
            }
        },

		add: function () {

			Controller.api.bindevent();

			$(document).on("change", "#J_unitselect", function () {

				$.get('diary/huoq', {
					id: $("#J_unitselect").val()
				}, function (res) {

					$('#c-orgcadre').val(res.data.orgcadre);

					$('#c-orgsoldier').val(res.data.orgsoldier);

					$('#c-orgcivilian').val(res.data.orgcivilian);

					$('#c-nowcadre').val(res.data.nowcadre);

					$('#c-nowsoldier').val(res.data.nowsoldier);

					$('#c-nowcivilian').val(res.data.nowcivilian);

				});


				//选择单位获取一周工作计划当日内容

				Controller.api.getweekwork();

			});
			$(document).on("changed", "#c-blog_date", function () {
			    Controller.api.getweekwork();
			});
			
			$('#fff').on('keyup','.J_should_arrive,.J_real_to',function(){
			    var o = $(this).parents('tr');
			    var a = o.find('.J_should_arrive').val();
			    var b = o.find('.J_real_to').val();
			    if(a && b)o.find('.J_percentage').val((b/a*100).toFixed(2)+"%");
			});
			

			$("#c-people_id,#c-dutylead,#c-dutyA,#c-dutyB,#c-dutyC,#c-dutyD,#c-dutyE,#c-dutyF,#c-lead,.people_id1").data("params", function (obj) {

				//console.log($(this).attr('id'))

				var unit_id = $('#J_unitselect').val();

				return {
					"custom[unit_id][0]": "in",
					"custom[unit_id][1]": unit_id
				};

			});

			//因为日期选择框不会触发change事件，导致无法刷新textarea，所以加上判断

			$(document).on("dp.change", "#add-form .datetimepicker", function () {

				$(this).parent().prev().find("input").trigger("change");

			});



			$(document).on("fa.event.appendfieldlist", "#add-form .btn-append", function (e, obj) {

				Form.events.selectpage(obj);

				Form.events.datetimepicker(obj);

			});



			$(document).on('click', '.btn-append', function () {

				var form = $("#add-form");

				require(['bootstrap-datetimepicker'], function () {

					var options = {

						format: 'LT',

						icons: {

							time: 'fa fa-clock-o',

							date: 'fa fa-calendar',

							up: 'fa fa-chevron-up',

							down: 'fa fa-chevron-down',

							previous: 'fa fa-chevron-left',

							next: 'fa fa-chevron-right',

							today: 'fa fa-history',

							clear: 'fa fa-trash',

							close: 'fa fa-remove'

						},

						showTodayButton: true,

						showClose: true

					};

					$('.datetimepicker3', form).parent().css('position', 'relative');

					$('.datetimepicker3', form).datetimepicker(options).on('dp.change', "#add-form .datetimepicker3", function (e) {

						$(this, document).trigger("changed");
						

					});

				});



				$(".people_id1,.people_id2,.people_id3,.people_id4,.people_id6,.people_id5").data("params", function (obj) {

					var unit_id = $('#J_unitselect').val();

					return {
						"custom[unit_id][0]": "in",
						"custom[unit_id][1]": unit_id
					};

				});

			});
			
			
             
		},

		edit: function () {

			Controller.api.bindevent();
			
			
			$(document).on("changed", "#c-blog_date", function () {
			    Controller.api.getweekwork();
			});

			$(document).on("change", "#J_unitselect", function () {

				$.get('diary/huoq', {
					id: $("#J_unitselect").val()
				}, function (res) {

					$('#c-orgcadre').val(res.data.orgcadre);

					$('#c-orgsoldier').val(res.data.orgsoldier);

					$('#c-orgcivilian').val(res.data.orgcivilian);

					$('#c-nowcadre').val(res.data.nowcadre);

					$('#c-nowsoldier').val(res.data.nowsoldier);

					$('#c-nowcivilian').val(res.data.nowcivilian);
                //选择单位获取一周工作计划当日内容

				Controller.api.getweekwork();


				});

			});

			$("#c-people_id,#c-dutylead,#c-dutyA,#c-dutyB,#c-dutyC,#c-dutyD,#c-dutyE,#c-dutyF,#c-lead,.people_id1").data("params", function (obj) {

				console.log($(this).attr('id'))

				var unit_id = $('#J_unitselect').val();

				return {
					"custom[unit_id][0]": "in",
					"custom[unit_id][1]": unit_id
				};

			});



			//因为日期选择框不会触发change事件，导致无法刷新textarea，所以加上判断

			$(document).on("dp.change", "#edit-form .datetimepicker", function () {

				$(this).parent().prev().find("input").trigger("change");

			});



			$(document).on("fa.event.appendfieldlist", "#edit-form .btn-append", function (e, obj) {

				Form.events.selectpage(obj);

				Form.events.datetimepicker(obj);

			});







			$(document).on('click', '.btn-append', function () {

				var form = $("#edit-form");

				require(['bootstrap-datetimepicker'], function () {

					var options = {

						format: 'LT',

						icons: {

							time: 'fa fa-clock-o',

							date: 'fa fa-calendar',

							up: 'fa fa-chevron-up',

							down: 'fa fa-chevron-down',

							previous: 'fa fa-chevron-left',

							next: 'fa fa-chevron-right',

							today: 'fa fa-history',

							clear: 'fa fa-trash',

							close: 'fa fa-remove'

						},

						showTodayButton: true,

						showClose: true

					};

					$('.datetimepicker3', form).parent().css('position', 'relative');

					$('.datetimepicker3', form).datetimepicker(options).on('dp.change', "#edit-form .datetimepicker3", function (e) {

						$(this, document).trigger("changed");

					});

				});



				$(".people_id1,.people_id2,.people_id3,.people_id4,.people_id6,.people_id5").data("params", function (obj) {

					var unit_id = $('#J_unitselect').val();

					return {
						"custom[unit_id][0]": "in",
						"custom[unit_id][1]": unit_id
					};

				});

			});

		},
        examine: function () {
            Controller.api.bindevent();
            $('#J_ok').click(function(){
                var id = $('#id').val();
                Layer.confirm('确定要通过吗？',{shade:false},function(index){
                    $.get('diary/auth_status',{ids:id,examine:1},function(res){
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
                        $.get('diary/auth_status',{ids:id,examine:2,contents:val},function(res){
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

			},

			getweekwork: function () {

				var date = $('#c-blog_date').val();
				var J_unitselect = $('#J_unitselect').val();

				var data = [];
				$.get('weekwork_register/index2', {
					time: date,
					unit_id: J_unitselect
				}, function (res) {
					$('#weekwork').val(res.data);
					$("#weekwork").css({
						'background': '#F00'
					});
					$("#weekwork").trigger("fa.event.refreshfieldlist");
					//					Form.events.fieldlist($('#weekwork'));
					console.log(res.data)
				});

			}

		}

	};

	return Controller;

});

