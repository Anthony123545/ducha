define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
             $(".btn-add").data("area", ["620px", "360px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'examine/index' + location.search,
                    add_url: 'examine/add',
                    // edit_url: 'examine/edit',
                    // del_url: 'examine/del',
                    multi_url: 'examine/multi',
                    import_url: 'examine/import',
                    table: 'examine',
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
                        {field: 'unit_id_text', title: __('Unit_id')},
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'class_id_text', title: __('人员类别')},
                        {field: 'job', title: __('工作岗位')},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons: [

								{

									name: '添加体检信息',

									text: '添加体检信息',

									title: '添加体检信息',

									classname: 'btn btn-xs btn-info btn-view btn-dialog ',

									icon: 'fa fa-search',

									url: 'examine_details/add',

									extend: 'data-area=\'["800px","600px"]\'',

									refresh: true

								},
								{

									name: '查看',

									text: '查看',

									title: '查看',

									classname: 'btn btn-xs btn-info btn-view btn-dialog ',

									icon: 'fa fa-search',

									url: 'examine_details/details',

									extend: 'data-area=\'["800px","600px"]\'',

									refresh: true

								}

							], formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table', function () {
                $(".btn-editone").data("area", ["730px", "360px"]);
            })
        },
        add: function () {
            Controller.api.bindevent();
            $("#c-people_id").data("eSelect", function(){
				$.get('examine/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-job').val(res.data.job);
					$('#c-class_id').val(res.data.class_id);
				//console.log(res);
				});
			    //后续操作
			});
            $("#c-people_id").data("params", function (obj) {

					var unit_id = $('#J_unitselect').val();

					return {
						"custom[unit_id][0]": "in",
						"custom[unit_id][1]": unit_id
					};

				});
        },
    //     edit: function () {
    //         Controller.api.bindevent();
    //         $("#c-people_id").data("params", function (obj) {

				// 	var unit_id = $('#J_unitselect').val();

				// 	return {
				// 		"custom[unit_id][0]": "in",
				// 		"custom[unit_id][1]": unit_id
				// 	};

				// });
    //     },
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            }
        }
    };
    return Controller;
});