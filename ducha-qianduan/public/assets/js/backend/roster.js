define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
			$(".btn-add").data("area",["1000px","1200px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'roster/index' + location.search,
                    // add_url: 'roster/add',
                    // edit_url: 'roster/edit',
                    // del_url: 'roster/del',
                    multi_url: 'roster/multi',
                    import_url: 'roster/import',
                    table: 'roster',
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
						{field: 'name', title: __('姓名')},
						{field: 'unit_id_text', title: __('单位')},
                        {field: 'post_id_text', title: __('职务')},
                        {field: 'rank_id_text', title: __('军衔'), operate: 'LIKE'},
                        {field: 'nation_id_text', title: __('民族'), operate: 'LIKE'},
                        {field: 'origin', title: __('籍贯'), operate:'RANGE'},
                        {field: 'birth_date', title: __('出生年月'), operate: 'LIKE'},
						{field: 'enlist_date', title: __('入伍时间'), operate: 'LIKE'},
						{field: 'party_date', title: __('党团时间'), operate: 'LIKE'},
                        {field: 'education_id_text', title: __('文化程度'), operate: 'LIKE'},
						{field: 'marry_list', title: __('婚姻状况'), operate: 'LIKE'},
						{field: 'phone', title: __('家庭住址及联系方式'), operate: 'LIKE'},
						//{field: 'lead_text', title: __('备注'), operate: 'LIKE'},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
								{
									name: '查看',
									text: '查看',
									title: '查看',
									classname: 'btn btn-xs btn-info btn-view btn-dialog ',
									icon: 'fa fa-search',
									url: 'roster/details',
									extend: 'data-area=\'["1000px","80%"]\'',
									refresh: true
								}
							], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
			Table.api.bindevent(table);
			table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","1200px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
			$(document).on("change", "#J_unitselect", function(){
			    $.get('diary/huoq',{id:$("#J_unitselect").val()},function(res){
			    	$('#c-orgcadre').val(res.data.orgcadre);
			    	$('#c-orgsoldier').val(res.data.orgsoldier);
			    	$('#c-orgcivilian').val(res.data.orgcivilian);
			    	$('#c-nowcadre').val(res.data.nowcadre);
			        $('#c-nowsoldier').val(res.data.nowsoldier);
			    	$('#c-nowcivilian').val(res.data.nowcivilian);
					
			    }); 
			}); 
			$("#c-people_id,#c-dutylead,#c-dutyA,#c-dutyB,#c-dutyC,#c-dutyD,#c-dutyE,#c-dutyF,#c-lead,.people_id1").data("params", function (obj) {
				//console.log($(this).attr('id'))
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});

            //因为日期选择框不会触发change事件，导致无法刷新textarea，所以加上判断
            $(document).on("dp.change", "#add-form .datetimepicker", function () {
                $(this).parent().prev().find("input").trigger("change");
            });
			
            $(document).on("fa.event.appendfieldlist", "#add-form .btn-append", function (e, obj) {
                Form.events.selectpage(obj);
                Form.events.datetimepicker(obj);
            });
            
			
			
			$(document).on('click','.btn-append',function(){
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
				    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
				});
			});
        },
        edit: function () {
            Controller.api.bindevent();
			$(document).on("change", "#J_unitselect", function(){
			    $.get('diary/huoq',{id:$("#J_unitselect").val()},function(res){
			    	$('#c-orgcadre').val(res.data.orgcadre);
			    	$('#c-orgsoldier').val(res.data.orgsoldier);
			    	$('#c-orgcivilian').val(res.data.orgcivilian);
			    	$('#c-nowcadre').val(res.data.nowcadre);
			        $('#c-nowsoldier').val(res.data.nowsoldier);
			    	$('#c-nowcivilian').val(res.data.nowcivilian);
					
			    });
			});
			$("#c-people_id,#c-dutylead,#c-dutyA,#c-dutyB,#c-dutyC,#c-dutyD,#c-dutyE,#c-dutyF,#c-lead,.people_id1").data("params", function (obj) {
				console.log($(this).attr('id'))
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
			
			//因为日期选择框不会触发change事件，导致无法刷新textarea，所以加上判断
			$(document).on("dp.change", "#edit-form .datetimepicker", function () {
			    $(this).parent().prev().find("input").trigger("change");
			});
			
			$(document).on("fa.event.appendfieldlist", "#edit-form .btn-append", function (e, obj) {
			    Form.events.selectpage(obj);
			    Form.events.datetimepicker(obj);
			});
			
			
			
			$(document).on('click','.btn-append',function(){
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
				    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
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