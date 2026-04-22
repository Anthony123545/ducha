define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
			$(".btn-add").data("area",["1024px","600px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'daily_education/index' + location.search,
                    add_url: 'daily_education/add',
                    edit_url: 'daily_education/edit',
                    del_url: 'daily_education/del',
                    multi_url: 'daily_education/multi',
                    import_url: 'daily_education/import',
                    table: 'daily_education',
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
                        {field: 'test_name', title: __('Test_name'), operate: 'LIKE'},
                        {field: 'test_name1', title: __('Test_name1'), operate: 'LIKE'},
						{field: 'should_be_to', title: '应到', operate: 'LIKE'},
						{field: 'actual_arrival', title: '实到', operate: 'LIKE'},
						{field: 'people_name', title: '主持人', operate: 'LIKE'},
						{field: 'topic', title: '教育题目', operate: 'LIKE'},
						{field: 'education_id_text', title: '教育类别', operate: 'LIKE'},
						{field: 'address', title: '地点', operate: 'LIKE'},
                        {field: 'time', title: __('Time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'people_name1', title: __('People_name1'), operate: 'LIKE'},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
			Table.api.bindevent(table);
			table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1024px","600px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
			Controller.api.events.getpeople();
			$("#c-test_id").on('change',function(){
			    Controller.api.events.getpeople();
			});
			$("#c-actual_arrival").on('change',function(){
				var max = $('#c-should_be_to').data('max');
				if($(this).val()>max){
			        Toastr.error("该数量不能大于单位总人数");
			        $(this).val(max);
				}
			});
			
			$("#c-actual_arrival").on('keyup',function(){
			    var max = $('#c-should_be_to').data('max');
			    if($(this).val()>max){
			        Toastr.error("该数量不能大于单位总人数");
			        $(this).val(max);
			    }
			});
        },
        edit: function () {
            Controller.api.bindevent();
			Controller.api.events.getpeople();
			$("#c-test_id").on('change',function(){
			    Controller.api.events.getpeople();
			});
			$("#c-actual_arrival").on('change',function(){
				var max = $('#c-should_be_to').data('max');
				if($(this).val()>max){
			        Toastr.error("该数量不能大于单位总人数");
			        $(this).val(max);
				}
			});
			
			$("#c-actual_arrival").on('keyup',function(){
			    var max = $('#c-should_be_to').data('max');
			    if($(this).val()>max){
			        Toastr.error("该数量不能大于单位总人数");
			        $(this).val(max);
			    }
			});
        },
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            },
			events: {
				getpeople:function(){
					$.get('daily_education/huoq',{unit_id:$("#c-test_id").val()},function(res){
						$('#c-should_be_to').val(res.data);
						$('#c-should_be_to').data('max',res.data);
					});
				}
			}
        }
    };
    return Controller;
});