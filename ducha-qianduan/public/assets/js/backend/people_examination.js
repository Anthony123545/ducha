define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {

        index: function () {
            $(".btn-add").data("area",["820px","90%"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'people_examination/index' + location.search,
                    add_url: 'people_examination/add',
                    edit_url: 'people_examination/edit',
                    del_url: 'people_examination/del',
                    multi_url: 'people_examination/multi',
                    import_url: 'people_examination/import',
                    table: 'people_examination',
                }
            });

            var table = $("#table");

            // 初始化表格
            table.bootstrapTable({
                url: $.fn.bootstrapTable.defaults.extend.index_url,
                pk: 'id',
                sortName: 'id',
				sortOrder:'desc',//默认排序方式
                columns: [
                    [
                        {checkbox: true},
                        {field: 'order_number', title: __('Id')},
                        //{field: 'people_id', title: __('People_id')},
						{field: 'c_name', title: '政审对象名称'},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["820px","90%"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-people_id").data("eSelect", function(){
				$.get('People/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-sex').val(res.data.sex_list);
					$('#c-name').val(res.data.name);
					$('#c-birth_date').val(res.data.birth_date);
					$('#c-party_id').val(res.data.party_id);
                    $('#c-party_id').selectPageRefresh();
					$('#c-nation_id').val(res.data.nation_id);
                    $('#c-nation_id').selectPageRefresh();
					$('#c-origin').val(res.data.origin);
					$('#c-enlist_date').val(res.data.enlist_date);
					$('#c-prize').val(res.data.prize);
					$('#c-enlistdesc').val(res.data.readme_file_name);
				});
			    //后续操作
			});
        },
        edit: function () {
            Controller.api.bindevent();
			$("#c-people_id").data("eSelect", function(){
				$.get('People/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-sex').val(res.data.sex_list);
					$('#c-name').val(res.data.name);
					$('#c-birth_date').val(res.data.birth_date);
					$('#c-party_id').val(res.data.party_id);
			        $('#c-party_id').selectPageRefresh();
					$('#c-nation_id').val(res.data.nation_id);
			        $('#c-nation_id').selectPageRefresh();
					$('#c-origin').val(res.data.origin);
					$('#c-enlist_date').val(res.data.enlist_date);
					$('#c-prize').val(res.data.prize);
					$('#c-enlistdesc').val(res.data.readme_file_name);
				});
			    //后续操作
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