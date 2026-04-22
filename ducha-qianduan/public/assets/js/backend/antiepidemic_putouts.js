define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'antiepidemic_putouts/index' + location.search,
                    // add_url: 'antiepidemic_putouts/add',
                    // edit_url: 'antiepidemic_putouts/edit',
                    // del_url: 'antiepidemic_putouts/del',
                    // multi_url: 'antiepidemic_putouts/multi',
                    // import_url: 'antiepidemic_putouts/import',
                    table: 'antiepidemic_putouts',
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
                        {field: 'durg_brand', title: __('Durg_brand'), operate: 'LIKE'},
                        {field: 'specifications', title: __('Specifications'), operate: 'LIKE'},
                        {field: 'store_house_text', title: __('库房')},
                        {field: 'unit_id1_text', title: __('发放单位')},
                        {field: 'unit_id_text', title: __('Unit_id')},
                        {field: 'people_id_text', title: __('使用人')},
                        {field: 'put_out', title: __('Put_out')},
                        // {field: 'place', title: __('Place'), operate: 'LIKE'},
                        {field: 'put_time', title: __('使用时间'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        
                        
                        //{field: 'admin_id_text', title: __('Admin_id')},
                        //{field: 'createtime', title: __('Createtime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
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
            $("#c-people_id").data("params", function (obj) {
                var unit_id = $('#J_unitselect').val();

                return {"custom[unit_id][0]":"find_in_set","custom[unit_id][1]":unit_id};
                // return {custom: {unit_id: $("#J_unitselect").val()}};
            });
            $("#c-durg_archives_id").data("eSelect", function(){
				$.get('antiepidemic_putout/huoq',{id:$("#c-durg_archives_id").val()},function(res){
					$('#c-durg_brand').val(res.data.durg_brand);
                    $('#c-specifications').val(res.data.specifications);
				//console.log($("#c-durg_archives_id").val());
				});
			    //后续操作
			});
        },
        edit: function () {
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
            }
        }
    };
    return Controller;
});