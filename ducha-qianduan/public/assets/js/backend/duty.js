define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            $(".btn-add").data("area",["920px","480px"]);
			// $(".btn-edit").data("area",["860px","70%"]);
            Table.api.init({
                extend: {
                    index_url: 'duty/index' + location.search,
                    add_url: 'duty/add',
                    edit_url: 'duty/edit',
                    del_url: 'duty/del',
                    multi_url: 'duty/multi',
                    import_url: 'duty/import',
                    table: 'duty',
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
                        {field: 'order_number', title: __('序号'),operate:false},
                        //{field: 'unit_id_text', title: __('Unit_id'),operate:false},
                        {field: 'duty_roster_time', title: __('duty_roster_time'), operate:'=', addclass:'datepicker', autocomplete:false},
                        {field: 'people_id', title: __('People_id'),operate:false},
                        //{field: 'post_id', title: __('Post_id')},
                        //{field: 'office_phone', title: __('Office_phone'), operate:false},
                        //{field: 'dormitory_phone', title: __('Dormitory_phone'), operate: 'LIKE'},
                        //{field: 'mobile_phone', title: __('Mobile_phone'), operate: 'LIKE'},
                        {field: 'people_id1', title: __('People_id1'),operate:false},
                        //{field: 'post_id1', title: __('Post_id1')},
                        //{field: 'office_phone1', title: __('Office_phone1'),operate:false},
                        //{field: 'mobile_phone1', title: __('Mobile_phone1'), operate: 'LIKE'},
                        //{field: 'unit_id1', title: __('Unit_id1'),operate:false},
                        {field: 'team1', title: __('Team1'),operate:false},
                        {field: 'team2', title: __('Team2'),operate:false},
                        {field: 'team3', title: __('Team3'),operate:false},
                        //{field: 'team_number1', title: __('Team_number1')},
                        //{field: 'team_number2', title: __('Team_number2')},
                        //{field: 'team_number3', title: __('Team_number3')},
                        
                        {field: 'createtime', title: __('Createtime'),operate:false, addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        //{field: 'updatetime', title: __('Updatetime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        //{field: 'field1', title: __('Field1'), operate: 'LIKE'},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
			table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["920px","480px"]);
			})
			$('.J_list').click(function(){
			    var container = $("#table").data("bootstrap.table").$container;
			    var options = $("#table").bootstrapTable('getOptions');
			    //这里我们手动将数据填充到表单然后提交
			    $("form.form-commonsearch [name='duty_roster_time']", container).val($(this).data('statusdata'));
			    $("form.form-commonsearch", container).trigger('submit');
			});
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-people_id").data("eSelect", function(){
				$.get('duty/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-post_id').val(res.data.post_id);
			        $('#c-post_id').selectPageRefresh();
				});
			    //后续操作
			});
			$("#c-people_id1").data("eSelect", function(){
				$.get('duty/huoq',{id:$("#c-people_id1").val()},function(res){
					$('#c-post_id1').val(res.data.post_id);
			        $('#c-post_id1').selectPageRefresh();
				});
			    //后续操作
			});
        },
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