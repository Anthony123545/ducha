define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["800px","660px"]);
            // $(".btn-edit").data("area",["700px","90%"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'people_transfer/index' + location.search,
                    add_url: 'people_transfer/add',
                    edit_url: 'people_transfer/edit',
                    del_url: 'people_transfer/del',
                    multi_url: 'people_transfer/multi',
                    import_url: 'people_transfer/import',
                    table: 'people_transfer',
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
                        //{field: 'people_id', title: __('People_id')},
						{field: 'c_name', title: '调动人员名称'},
                        {field: 'transfer_id_text', title: __('Transfer_id')},
                        {field: 'transfer_date', title: __('Transfer_date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        // {field: 'transfer_take', title: __('Transfer_take'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        // {field: 'military_rank1', title: __('Military_rank1'), operate: 'LIKE'},
                        {field: 'military_rank2_text', title: __('Military_rank2'), operate: 'LIKE'},
                        // {field: 'post1', title: __('Post1'), operate: 'LIKE'},
                        {field: 'post2_text', title: __('Post2'), operate: 'LIKE'},
                        // {field: 'unit1', title: __('Unit1'), operate: 'LIKE'},
                        {field: 'unit2_text', title: __('Unit2'), operate: 'LIKE'},
                        {field: 'aduty_text', title: __('Aduty'), operate: 'LIKE'},
                        // {field: 'command_number', title: __('Command_number'), operate: 'LIKE'},
                        // {field: 'reason', title: __('Reason'), operate: 'LIKE'},
                        // {field: 'remark', title: __('Remark'), operate: 'LIKE'},
                        // {field: 'field1', title: __('Field1'), operate: 'LIKE'},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        // {field: 'updatetime', title: __('Updatetime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["800px","660px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-people_id").data("eSelect", function(){
				$.get('People/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-military_rank1').val(res.data.rank_id);
					$('#c-military_rank1').selectPageRefresh();
					$('#c-name').val(res.data.name);
                    $('#c-post1').val(res.data.post_id);
					$('#c-post1').selectPageRefresh();
                    $('#c-unit1').val(res.data.unit_id);
					$('#c-unit1').selectPageRefresh();
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