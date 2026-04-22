define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            $(".btn-add").data("area",["860px","46%"]);
			$(".btn-edit").data("area",["860px","46%"]);
            Table.api.init({
                extend: {
                    index_url: 'duty_statistics/index' + location.search,
                    add_url: 'duty_statistics/add',
                    edit_url: 'duty_statistics/edit',
                    del_url: 'duty_statistics/del',
                    multi_url: 'duty_statistics/multi',
                    import_url: 'duty_statistics/import',
                    table: 'duty_statistics',
                }
            });

            var table = $("#table");
            table.on('post-common-search.bs.table', function (event, table) {
                var form = $("form", table.$commonsearch);
                $("input[name='factory.name']", form).addClass("selectpage").data("source", "factories/index").data("primaryKey", "name").data("field", "name");
                $("input[name='admin.username']", form).addClass("selectpage").data("source", "auth/admin/index").data("primaryKey", "username").data("field", "username");//路径为控制器方法

                $("input[name='engineer.name']", form).addClass("selectpage").data("source", "engineers/index").data("primaryKey", "name").data("field", "name");
                Form.events.cxselect(form); Form.events.selectpage(form); 
            });
            // 初始化表格
            table.bootstrapTable({
                url: $.fn.bootstrapTable.defaults.extend.index_url,
                pk: 'id',
                sortName: 'id',
                search:false,
                commonSearch: true,
                showToggle: false,
                showColumns: false,
                pagination:false,
                columns: [
                    [
                        // {checkbox: true},
                        {field: 'order_number', title: __('Id')},
                        {field: 'unit_name', title: __('Unit_id'),operate:false},
                        {field: 'unit_id', title: __('Unit_id'),visible:false, operate: 'IN'},
                        {field: 'duty_today', title: __('Today'), operate: 'LIKE',formatter: Controller.api.formatter.status},
                        {field: 'duty_tomorrow', title: __('Tomorrow'), operate: 'LIKE',formatter: Controller.api.formatter.status},
                        {field: 'duty_bus', title: __('Vehicle'), operate: 'LIKE',formatter: Controller.api.formatter.status},
                        {field: 'duty_equip', title: __('Equip'), operate: 'LIKE',formatter: Controller.api.formatter.status}
                    ]
                ]
            });
            $('#s-search').click(function(){
                window.location.href="?unit_id="+$('#s-unit_id').val();
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
            formatter: {//渲染的方法
                status: function (value, row, index) {
                    if(value=='1')return '<span class="text-success"><i class="fa fa-check-circle"></i></span>';
                    return '<span class="text-danger"><i class="fa fa-times-circle"></i></span>';
                }
            },
        }
    };
    return Controller;
});