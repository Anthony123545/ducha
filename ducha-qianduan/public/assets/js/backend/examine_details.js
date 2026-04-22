define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area", ["560px", "360px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'examine_details/index' + location.search,
                    add_url: 'examine_details/add',
                    edit_url: 'examine_details/edit',
                    del_url: 'examine_details/del',
                    multi_url: 'examine_details/multi',
                    import_url: 'examine_details/import',
                    table: 'examine_details',
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
                        {field: 'id', title: __('Id')},
                        {field: 'year', title: __('Year'), operate: 'LIKE'},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["560px", "360px"]);
            })
        },
        add: function () {
            Controller.api.bindevent();
            $(document).on('click', '.btn-append', function () {
				var form = $("#add-form");
    			require(['bootstrap-datetimepicker'], function () {
                    var options = {
                        format: 'YYYY',
                        icons: {
                            time: 'fa fa-clock-o',
                            date: 'fa fa-calendar',
                            up: 'fa fa-chevron-up',
                            down: 'fa fa-chevron-down',
                            previous: 'fa fa-chevron-left',
                            next: 'fa fa-chevron-right',
                            today: 'fa fa-history',
                            clear: 'fa fa-trash',
                            close: 'fa fa-remove'
                        },
                        showTodayButton: true,
                        showClose: true
                    };
                    $('.datetimepickerY', form).parent().css('position', 'relative');

					$('.datetimepickerY', form).datetimepicker(options).on('dp.change', "#add-form .datetimepickerY", function (e) {

						$(this, document).trigger("changed");

					});
                });
            });
            $("#c-year").on("dp.update", function(){
                var that = this;
                $("select[name='quarter']").val('');
                $("select[name='month']").val('');
                var options = table.bootstrapTable('getOptions');
                options.pageNumber = 1;
                options.queryParams = function (params) {
                    params.year = $(that).val(); // 值
                    console.log(params);
                    return params;
                };
                table.bootstrapTable('refresh', {});
                return false;
            });
        },
        edit: function () {
            Controller.api.bindevent();
        },
        details: function () {
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