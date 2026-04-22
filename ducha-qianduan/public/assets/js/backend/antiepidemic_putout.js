define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area", ["730px", "420px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'antiepidemic_putout/index' + location.search,
                    add_url: 'antiepidemic_putout/add',
                    edit_url: 'antiepidemic_putout/edit',
                    del_url: 'antiepidemic_putout/del',
                    multi_url: 'antiepidemic_putout/multi',
                    import_url: 'antiepidemic_putout/import',
                    table: 'antiepidemic_putout',
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
                        {field: 'durg_archives_id_text', title: __('防疫品名称')},
                        {field: 'unit_id1_text', title: __('单位')},
                        {field: 'store_house_text', title: __('库房')},
                        {field: 'durg_brand', title: __('Durg_brand'), operate: 'LIKE'},
                        {field: 'specifications', title: __('Specifications'), operate: 'LIKE'},
                        {field: 'put_out', title: __('Put_out')},
                        {field: 'place', title: __('所在位置'), operate: 'LIKE'},
                        {field: 'put_time', title: __('发放时间'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        //{field: 'people_id_text', title: __('Receive_id')},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        //{field: 'updatetime', title: __('Updatetime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        // {field: 'remark', title: __('Remark'), operate: 'LIKE'},
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
        },
        add: function () {
            Controller.api.bindevent();
            Controller.api.events.getAmount();
            $("#c-durg_archives_id").on('change',function(){
                Controller.api.events.getAmount();
            });
            $("#c-put_out").on('change',function(){
                var max = $(this).data('max');
                if($(this).val()>max){
                    Toastr.error("出库数量不能大于库存数量");
                    $(this).val(max);
                }
            });

            $("#c-put_out").on('keyup',function(){
                var max = $(this).data('max');
                if($(this).val()>max){
                    Toastr.error("出库数量不能大于库存数量");
                    $(this).val(max);
                }
            });
            $("#c-people_id").data("params", function (obj) {
                var unit_id = $('#J_unitselect').val();

                return {"custom[unit_id][0]":"find_in_set","custom[unit_id][1]":unit_id};
                // return {custom: {unit_id: $("#J_unitselect").val()}};
            });
            $("#c-durg_archives_id").data("eSelect", function(){
				$.get('antiepidemic_putout/huoq',{id:$("#c-durg_archives_id").val()},function(res){
					$('#c-durg_brand').val(res.data.durg_brand);
                    $('#c-specifications').val(res.data.specifications);
                    $('#c-store_house').val(res.data.store_house);
                    $('#c-unit_id1').val(res.data.unit_id);
				//console.log($("#c-durg_archives_id").val());
				});
			    //后续操作
			});
        },
        edit: function () {
            Controller.api.events.getAmount();
            $("#c-durg_archives_id").on('change',function(){
                Controller.api.events.getAmount();
            });
            $("#c-put_out").on('change',function(){
                var max = $(this).data('max');
                if($(this).val()>max){
                    Toastr.error("出库数量不能大于库存数量");
                    $(this).val(max);
                }
            });

            $("#c-put_out").on('keyup',function(){
                var max = $(this).data('max');
                if($(this).val()>max){
                    Toastr.error("出库数量不能大于库存数量");
                    $(this).val(max);
                }
            });
            $("#c-people_id").data("params", function (obj) {
                var unit_id = $('#J_unitselect').val();

                return {"custom[unit_id][0]":"find_in_set","custom[unit_id][1]":unit_id};
                // return {custom: {unit_id: $("#J_unitselect").val()}};
            });
            Controller.api.bindevent();
        },
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            },
			events: {
				getAmount:function(){
                    $.get('antiepidemic_putout/getAmount',{durg_id:$("#c-durg_archives_id").val()},function(res){
                        if($('#c-put_out').val() > res.data)$('#c-put_out').val(res.data);

                        $('#c-put_out').data('max',res.data);
                    });
                }
			}
        }
    };
    return Controller;
});