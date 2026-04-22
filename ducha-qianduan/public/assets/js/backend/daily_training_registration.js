define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            $(".btn-add").data("area",["1000px","480px"]);
		
            Table.api.init({
                extend: {
                    index_url: 'daily_training_registration/index' + location.search,
                    add_url: 'daily_training_registration/add',
                    edit_url: 'daily_training_registration/edit',
                    del_url: 'daily_training_registration/del',
                    multi_url: 'daily_training_registration/multi',
                    import_url: 'daily_training_registration/import',
                    table: 'daily_training_registration',
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
                        {field: 'test_name', title: __('Test_name'), operate: 'LIKE'},
						{field: 'leader_name', title: __('Leader_name'), operate: 'LIKE'},
						{field: 'drill_id_text', title: '训练类别', operate: 'LIKE'},
                        {field: 'subjects', title: __('Subjects'), operate: 'LIKE'},
						{field: 'organization_name', title: __('Organization_name'), operate: 'LIKE'},
                        {field: 'enter_count', title: __('Enter_count')},
                        {field: 'drill_duration', title: __('Drill_duration')},
                        {field: 'drill_time', title: __('Drill_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","480px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();

            Controller.api.events.getpeople();
			$("#c-test_id").on('change',function(){
                Controller.api.events.getpeople();
            });
			$("#c-enter_count").on('change',function(){
				var max = $(this).data('max');
				if($(this).val()>max){
                    Toastr.error("该数量不能大于单位总人数");
                    $(this).val(max);
				}
			});

            $("#c-enter_count").on('keyup',function(){
                var max = $(this).data('max');
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
            $("#c-enter_count").on('change',function(){
                var max = $(this).data('max');
                if($(this).val()>max){
                    Toastr.error("该数量不能大于单位总人数");
                    $(this).val(max);
                }
            });

            $("#c-enter_count").on('keyup',function(){
                var max = $(this).data('max');
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
                    $.get('daily_training_registration/huoq',{unit_id:$("#c-test_id").val()},function(res){
                        if($('#c-enter_count').val() > res.data)$('#c-enter_count').val(res.data);

                        $('#c-enter_count').data('max',res.data);
                    });
                }
            }

        }
    };
    return Controller;
});