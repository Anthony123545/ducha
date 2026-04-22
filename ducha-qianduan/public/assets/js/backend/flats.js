define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area", ["800px", "500px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'flats/index' + location.search,
                    add_url: 'flats/add',
                    edit_url: 'flats/edit',
                    del_url: 'flats/del',
                    multi_url: 'flats/multi',
                    import_url: 'flats/import',
                    table: 'flats',
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
                        {field: 'duty', title: __('Duty'), operate: 'LIKE'},
                        {field: 'whether', title: __('Whether'), searchList: {"否":__('否'),"是":__('是')}, formatter: Table.api.formatter.normal},
                        {field: 'grade', title: __('Grade'), operate: 'LIKE'},
                        {field: 'house_type', title: __('House_type'), operate: 'LIKE'},
                        {field: 'birth_date', title: __('Birth_date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'enlist_date', title: __('Enlist_date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'job_time', title: __('Job_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'entrance_date', title: __('Entrance_date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'job_date', title: __('Job_date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'jobs_date', title: __('Jobs_date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'whethers', title: __('Whethers'), searchList: {"否":__('否'),"是":__('是')}, formatter: Table.api.formatter.normal},
                        {field: 'rank_id_text', title: __('Rank_id')},
                        {field: 'rank_date', title: __('Rank_date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'remark', title: __('Remark'), operate: 'LIKE'},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
        },
        add: function () {
            Controller.api.bindevent();
            $("#c-people_id").data("params", function (obj) {
				var unit_id = $('#J_unitselect').val();
				return {
					"custom[unit_id][0]": "in",
					"custom[unit_id][1]": unit_id
				};
			});
            $("#c-people_id").data("eSelect", function(){
				$.get('People/huoq',{id:$("#c-people_id").val()},function(res){
				    
					$('#c-rank_id').val(res.data.rank_id);
					$('#c-rank_id').selectPageRefresh();
					$('#c-duty').val(res.data.duty);
					$('#c-rank_date').val(res.data.rank_date);
					$('#c-enlist_date').val(res.data.enlist_date);
					$('#c-birth_date').val(res.data.birth_date);
					$('#c-job_time').val(res.data.job_time);
				});
			    //后续操作
			});
        },
        edit: function () {
            Controller.api.bindevent();
            $("#c-people_id").data("params", function (obj) {
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