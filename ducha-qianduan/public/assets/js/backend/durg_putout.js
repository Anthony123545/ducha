define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
             $(".btn-add").data("area", ["720px", "520px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'durg_putout/index' + location.search,
                    add_url: 'durg_putout/add',
                    edit_url: 'durg_putout/edit',
                    del_url: 'durg_putout/del',
                    multi_url: 'durg_putout/multi',
                    import_url: 'durg_putout/import',
                    table: 'durg_putout',
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
                        {field: 'number', title: __('出库编号')},
                        {field: 'durg_archives_id_text', title: __('药品名称')},
                        {field: 'durg_brand', title: __('品牌'), operate: 'LIKE'},
                        {field: 'specifications', title: __('规格'), operate: 'LIKE'},
                        {field: 'manufacturer', title: __('生产厂商'), operate: 'LIKE'},
                        {field: 'unit_id_text', title: __('单位'), operate: 'LIKE'},
                        {field: 'store_house_text', title: __('库房'), operate: 'LIKE'},
                        {field: 'purpose', title: __('Purpose'), operate: 'LIKE'},
                        {field: 'put_out', title: __('Put_out')},
                        {field: 'place', title: __('Place'), operate: 'LIKE'},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        {field: 'put_time', title: __('Put_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["720px", "520px"]);
            })
        },
        add: function () {
            $("#c-receive_id").data("params", function (obj) {
				// console.log($(this).attr('id'))
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
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
            $("#c-durg_archives_id").data("eSelect", function(){
				$.get('durg_putout/huoq',{id:$("#c-durg_archives_id").val()},function(res){
					$('#durg_brand').val(res.data.durg_brand);
					$('#manufacturer').val(res.data.manufacturer);
                    $('#specifications').val(res.data.specifications);
                    $('#product_introduction').val(res.data.product_introduction);
                    $('#unit_id').val(res.data.unit_id);
                    $('#store_house').val(res.data.store_house);
				//console.log($("#c-durg_archives_id").val());
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
            },
            events: {
                getAmount:function(){
                    $.get('durg_putout/getAmount',{durg_id:$("#c-durg_archives_id").val()},function(res){
                        if($('#c-put_out').val() > res.data)$('#c-put_out').val(res.data);

                        $('#c-put_out').data('max',res.data);
                    });
                }
            }
        }
    };
    return Controller;
});