define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["540px","450px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'unit/index' + location.search,
                    add_url: 'unit/add',
                    edit_url: 'unit/edit',
                    del_url: 'unit/del',
                    multi_url: 'unit/multi',
                    import_url: 'unit/import',
                    table: 'unit',
                }
            });

            var table = $("#table");

            // 初始化表格
            table.bootstrapTable({
                url: $.fn.bootstrapTable.defaults.extend.index_url,
                pk: 'id',
                sortName: 'weigh',
                search:false,
                commonSearch:false,
                cardView:false,
                pagination:false,
                columns: [
                    [
                        {checkbox: true},
                        {field: 'order_number', title: '序号'},
                        {field: 'number', title: __('Number'), operate: 'LIKE'},
                        {field: 'pnumber', title: __('Pnumber'), operate: 'LIKE'},
                        {field: 'name', title: __('Name'), operate: 'LIKE',align:'left',formatter:function (value, row, index) {
                                return value.toString().replace(/(&|&amp;)nbsp;/g, '&nbsp;');
                            }},
                        {field: 'weigh', title: __('Weigh'), operate: false,visible:false},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["540px","450px"]);
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
            },
            formatter: {
                title: function (value, row, index) {
                    value = value.toString().replace(/(&|&amp;)nbsp;/g, '&nbsp;');
                    return !row.ismenu || row.status == 'hidden' ? "<span class='text-muted'>" + value + "</span>" : value;
                }
            }
        }
    };
    return Controller;
});