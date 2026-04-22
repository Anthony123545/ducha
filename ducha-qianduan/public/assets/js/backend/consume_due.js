define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["1000px","775px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'consume_due/index' + location.search,
                    
                    multi_url: 'consume_due/multi',
                    import_url: 'consume_due/import',
                    table: 'consume_due',
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
                        //{field: 'number', title: __('入库编号')},
                        {field: 'durg_archives_id_text', title: __('耗材名称')},
                        {field: 'durg_brand', title: __('品牌'), operate: 'LIKE'},
                        {field: 'specifications', title: __('规格'), operate: 'LIKE'},
                        {field: 'manufacturer', title: __('生产厂商'), operate: 'LIKE'},
                        {field: 'unit_id_text', title: __('单位'), operate: 'LIKE'},
                        {field: 'store_house_text', title: __('库房'), operate: 'LIKE'},
                        {field: 'place', title: __('入库位置'), operate: 'LIKE'},
                        {field: 'put_in', title: __('入库数量')},
                        {field: 'surplus', title: __('剩余库存')},
                        //{field: 'source', title: __('Source'), operate: 'LIKE'},
                        //{field: 'remarks', title: __('Remarks'), operate: 'LIKE'},
                        {field: 'overduedate', title: __('过期时间'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        //{field: 'admin_id_text', title: __('Admin_id')},
                        //{field: 'put_time', title: __('Put_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons:
                            [
                                {
                                    name: '销毁',
                                    text: '销毁', 
                                    title: '销毁', 
                                    icon: 'fa', 
                                    confirm: '您确定要销毁吗？',
                                    classname: 'btn btn-xs btn-info btn-ajax', 
                                    refresh:true,
                                    url: function(row){return 'consume_due/ruin?ids='+row.id;},
                                    visible: function (row) {
										if (row['status'] == 0) {
											return true;
										} else {
											return false;
										}
									},
                                },
                                {
                                    name: '已销毁',
                                    text: '已销毁', 
                                    title: '已销毁', 
                                    icon: 'fa', 
                                    classname: 'btn btn-xs btn-info', 
                                    visible: function (row) {
										if (row['status'] == 1) {
											return true;
										} else {
											return false;
										}
									},
                                },
                            ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });


            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["1000px", "690px"]);
            })
        },
        
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            }
        }
    };
    return Controller;
});