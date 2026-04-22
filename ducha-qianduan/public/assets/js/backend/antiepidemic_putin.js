define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area", ["735px", "360px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'antiepidemic_putin/index' + location.search,
                    add_url: 'antiepidemic_putin/add',
                    edit_url: 'antiepidemic_putin/edit',
                    del_url: 'antiepidemic_putin/del',
                    multi_url: 'antiepidemic_putin/multi',
                    import_url: 'antiepidemic_putin/import',
                    table: 'antiepidemic_putin',
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
                        {field: 'durg_archives_id_text', title: __('Durg_archives_id')},
                        {field: 'durg_brand', title: __('品牌')},
                        {field: 'specifications', title: __('规格'), operate: 'LIKE'},
                        {field: 'store_house_text', title: __('库房')},
                        {field: 'unit_id_text', title: __('单位')},
                        {field: 'put_in', title: __('Put_in')},
                        {field: 'place', title: __('Place'), operate: 'LIKE'},
                        {field: 'put_time', title: __('Put_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'updatetime', title: __('Updatetime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime,visible:false},
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
        },
        add: function () {
            Controller.api.bindevent();
            $("#c-durg_archives_id").data("eSelect", function(){
				$.get('antiepidemic_putin/huoq',{id:$("#c-durg_archives_id").val()},function(res){
					$('#durg_brand').val(res.data.durg_brand);
					$('#specifications').val(res.data.specifications);
					$('#store_house').val(res.data.store_house);
					$('#unit_id').val(res.data.unit_id);
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