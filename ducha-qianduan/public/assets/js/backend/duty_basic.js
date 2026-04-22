define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            $(".btn-add").data("area", ["768px", "410px"]);
            Table.api.init({
                extend: {
                    index_url: 'duty_basic/index' + location.search,
                    add_url: 'duty_basic/add',
                    edit_url: 'duty_basic/edit',
                    del_url: 'duty_basic/del',
                    multi_url: 'duty_basic/multi',
                    import_url: 'duty_basic/import',
                    table: 'duty_basic',
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
                        {field: 'unit_id_text', title: __('Unit_id'), operate: 'LIKE'},
                        {field: 'people_id_text', title: __('People_id'), operate: 'LIKE'},
                        {field: 'post_id_text', title: __('Post_id'), operate: 'LIKE'},
                        {field: 'office_phone', title: __('Office_phone'), operate: 'LIKE'},
                        {field: 'dormitory_phone', title: __('Dormitory_phone'), operate: 'LIKE'},
                        {field: 'mobile_phone', title: __('Mobile_phone'), operate: 'LIKE'},
                        {field: 'people_id1_text', title: __('People_id1'), operate: 'LIKE'},
                        {field: 'post_id1_text', title: __('Post_id1'), operate: 'LIKE'},
                        {field: 'unit_id1_text', title: __('Unit_id1')},
                        //{field: 'statusdata', title: __('Statusdata'), searchList: {"当日值班":__('当日值班'),"次日值班":__('次日值班')}, formatter: Table.api.formatter.normal},
                        //{field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'duty_roster_time', title: __('Duty_roster_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
             table.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["768px", "410px"]);
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
				$.get('duty_basic/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-post_id').val(res.data.post_id);
			        $('#c-post_id').selectPageRefresh();
				});
			    //后续操作
			});
			$("#c-people_id1").data("eSelect", function(){
				$.get('duty_basic/huoq',{id:$("#c-people_id1").val()},function(res){
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