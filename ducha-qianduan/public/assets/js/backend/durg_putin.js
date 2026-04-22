define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area", ["740px", "460px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'durg_putin/index' + location.search,
                    add_url: 'durg_putin/add',
                    // edit_url: 'durg_putin/edit',
                    // del_url: 'durg_putin/del',
                    multi_url: 'durg_putin/multi',
                    import_url: 'durg_putin/import',
                    table: 'durg_putin',
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
                        {field: 'number', title: __('入库编号')},
                        {field: 'durg_archives_id_text', title: __('Durg_archives_id')},
                        {field: 'durg_brand', title: __('品牌'), operate: 'LIKE'},
                        {field: 'specifications', title: __('规格'), operate: 'LIKE'},
                        {field: 'manufacturer', title: __('生产厂商'), operate: 'LIKE'},
                        {field: 'unit_id_text', title: __('单位'), operate: 'LIKE'},
                        {field: 'store_house_text', title: __('库房'), operate: 'LIKE'},
                        {field: 'source', title: __('Source'), operate: 'LIKE'},
                        {field: 'put_in', title: __('Put_in')},
                        {field: 'place', title: __('Place'), operate: 'LIKE'},
                        //{field: 'remarks', title: __('Remarks'), operate: 'LIKE'},
                        {field: 'overduedate', title: __('Overduedate'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        {field: 'put_time', title: __('Put_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["750px", "460px"]);
            })
        },
        add: function () {
            Controller.api.bindevent();
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
        // edit: function () {
        //     Controller.api.bindevent();
        // },
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            }
        }
    };
    return Controller;
});