define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            $(".btn-add").data("area",["800px","510px"]);
			// $(".btn-edit").data("area",["860px","55%"]);
            Table.api.init({
                extend: {
                    index_url: 'daily_internal_affairs/index' + location.search,
                    add_url: 'daily_internal_affairs/add',
                    // edit_url: 'daily_internal_affairs/edit',
                    del_url: 'daily_internal_affairs/del',
                    multi_url: 'daily_internal_affairs/multi',
                    import_url: 'daily_internal_affairs/import',
                    table: 'daily_internal_affairs',
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
                        {field: 'unit_id_text', title: __('检查单位'), operate: 'LIKE'},
                        {field: 'unit_id1_text', title: __('受检单位'), operate: 'LIKE'},
                        {field: 'examine_time', title: __('检查时间'), operate: 'LIKE'},
                        {field: 'peoples_id_text', title: __('受检人员'), operate: 'LIKE'},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [
                                
								{
								    name: '问题整改',
								    text: '问题整改',
								    title: '问题整改',
								    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
								    icon: 'fa fa-search',
								    url: 'risk_live/mend',
                                    //extend: 'data-area=\'["1000px","80%"]\'',
									// visible: function (row) {
									// 	if (row['state'] == 0) {
									// 		return true;
									// 	} else {
									// 		return false;
									// 	}
									// },
								    refresh: true
								}
                            ], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["800px","510px"]);
			})
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
            }
        }
    };
    return Controller;
});