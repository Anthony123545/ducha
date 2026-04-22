define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
			$(".btn-add").data("area", ["1000px", "490px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'drill/index' + location.search,
                    add_url: 'drill/add',
                    edit_url: 'drill/edit',
                    del_url: 'drill/del',
                    multi_url: 'drill/multi',
                    import_url: 'drill/import',
                    table: 'drill',
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
                        {field: 'project', title: __('Project'), operate: 'LIKE'},
                        {field: 'drill_time', title: __('Drill_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'address', title: __('Address'), operate: 'LIKE'},
                        {field: 'ought_to', title: __('Ought_to')},
                        {field: 'actual', title: __('Actual')},
                        {field: 'probability', title: __('Probability'), operate:'BETWEEN'},
                        {field: 'result', title: __('Result'), operate: 'LIKE'},
                        {field: 'expend', title: __('Expend'), operate: 'LIKE'},
                        // {field: 'remark', title: __('Remark'), operate: 'LIKE'},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
                                
								{
								    name: '查看',
								    text: '查看',
								    title: '查看',
								    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
								    icon: 'fa fa-search',
								    url: 'drill/details',
								    extend: 'data-area=\'["900px","600px"]\'',
								    refresh: true
								}
                            ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
			Table.api.bindevent(table);
			table.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["1000px", "600px"]);
            })
        },
        add: function () {
            Controller.api.bindevent();
			Controller.api.events.getpeople();
			$("#J_unitselect").on('change',function(){
			    Controller.api.events.getpeople();
			});
			$("#c-actual").on('change',function(){
				var max = $('#c-ought_to').data('max');
				if($(this).val()>max){
			        Toastr.error("该数量不能大于单位总人数");
			        $(this).val(max);
				}
			});
			
			$("#c-actual").on('keyup',function(){
			    var max = $('#c-ought_to').data('max');
			    if($(this).val()>max){
			        Toastr.error("该数量不能大于单位总人数");
			        $(this).val(max);
			    }
			});
			$("#c-people_id").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
        },
        edit: function () {
            Controller.api.bindevent();
			Controller.api.events.getpeople();
			$("#J_unitselect").on('change',function(){
			    Controller.api.events.getpeople();
			});
			$("#c-actual").on('change',function(){
				var max = $('#c-ought_to').data('max');
				if($(this).val()>max){
			        Toastr.error("该数量不能大于单位总人数");
			        $(this).val(max);
				}
			});
			
			$("#c-actual").on('keyup',function(){
			    var max = $('#c-ought_to').data('max');
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
					$.get('drill/huoq',{unit_id:$("#J_unitselect").val()},function(res){
						$('#c-ought_to').val(res.data);
						$('#c-ought_to').data('max',res.data);
					});
				}
			}
        }
    };
    return Controller;
});