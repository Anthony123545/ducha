define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'consume_putout/index' + location.search,
                    
                    table: 'consume_putout',
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
                        //{field: 'number', title: __('领用编号')},
                        {field: 'durg_archives_id_text', title: __('耗材名称')},
                        {field: 'durg_brand', title: __('品牌')},
                        {field: 'manufacturer', title: __('厂家'), operate: 'LIKE'},
                        {field: 'specifications', title: __('规格'), operate: 'LIKE'},
                        {field: 'unit_id1_text', title: __('单位')},
                        {field: 'unit_id_text', title: __('领用单位')},
                        {field: 'receive_id_text', title: __('使用人')},
                        {field: 'put_out', title: __('领用数量'), operate: 'LIKE'},
                        {field: 'put_time', title: __('使用时间'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
        },
        add: function () {
            Controller.api.bindevent();
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
                    $.get('consume_putout/getAmount',{durg_id:$("#c-durg_archives_id").val()},function(res){
                        if($('#c-put_out').val() > res.data)$('#c-put_out').val(res.data);

                        $('#c-put_out').data('max',res.data);
                    });
                }
            }
        }
    };
    return Controller;
});