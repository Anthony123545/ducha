define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area", ["1000px", "690px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'even/index' + location.search,
                    add_url: 'even/add',
                    edit_url: 'even/edit',
                    del_url: 'even/del',
                    multi_url: 'even/multi',
                    import_url: 'even/import',
                    table: 'even',
                }
            });

            var table = $("#table");

            // 初始化表格
            table.bootstrapTable({
                url: $.fn.bootstrapTable.defaults.extend.index_url,
                pk: 'id',
                sortName: 'id',
                columns: [
                    [{
                            checkbox: true
                        },
                        {
                            field: 'order_number',
                            title: __('序号')
                        },
                        {
                            field: 'unit_id_text',
                            title: __('单位')
                        },
                        {
                            field: 'convoke_time',
                            title: __('Convoke_time'),
                            operate: 'RANGE',
                            addclass: 'datetimerange',
                            autocomplete: false
                        },
                        {
                            field: 'address',
                            title: __('Address'),
                            operate: 'LIKE'
                        },
                        {
                            field: 'compere_id_text',
                            title: __('Compere_id')
                        },
                        {
                            field: 'join_id_text',
                            title: __('Join_id'),
                            operate: 'LIKE'
                        },
                        {
                            field: 'people_id_text',
                            title: __('People_id')
                        },
                        {
                            field: 'title',
                            title: __('Title'),
                            operate: 'LIKE'
                        },
                        
                        //{field: 'updatetime', title: __('Updatetime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {
                            field: 'operate',
                            title: __('Operate'),
                            table: table,
                            events: Table.api.events.operate,
                            buttons: [

                                {
                                    name: '查看',
                                    text: '查看',
                                    title: '查看',
                                    classname: 'btn btn-xs btn-info btn-view btn-dialog ',
                                    icon: 'fa fa-search',
                                    url: 'even/details',
                                    refresh: true
                                }
                            ],
                            formatter: Table.api.formatter.operate
                        }
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["1000px", "690px"]);
            })
        },
        add: function () {
            Controller.api.bindevent();
            $("#c-people_id,#c-compere_id,#c-join_id,#c-people_ids,#c-absent_ids").data("params", function (obj) {
                var unit_id = $('#J_unitselect').val();
                return {
                    "custom[unit_id][0]": "in",
                    "custom[unit_id][1]": unit_id
                };
            });
        },
        edit: function () {
            Controller.api.bindevent();
            $("#c-people_id,#c-compere_id,#c-join_id,#c-people_ids,#c-absent_ids").data("params", function (obj) {
                var unit_id = $('#J_unitselect').val();
                return {
                    "custom[unit_id][0]": "in",
                    "custom[unit_id][1]": unit_id
                };
            });
        },
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            }
        }
    };
    return Controller;
});