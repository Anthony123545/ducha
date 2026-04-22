define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["1000px","540px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'people_talk/index' + location.search,
                    add_url: 'people_talk/add',
                    edit_url: 'people_talk/edit',
                    del_url: 'people_talk/del',
                    multi_url: 'people_talk/multi',
                    import_url: 'people_talk/import',
                    table: 'people_talk',
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
                        {field: 'p_name', title: __('P_name'), operate: 'LIKE'},
						{field: 'grade_id_text', title: '谈心人员等级', operate: 'LIKE'},
                        {field: 'p_name1', title: __('P_name1'), operate: 'LIKE'},
						{field: 'grade_id1_text', title: '记录人员等级', operate: 'LIKE'},
                        {field: 'p_name2', title: __('P_name2'), operate: 'LIKE'},
						{field: 'grade_id2_text', title: '谈话对象等级', operate: 'LIKE'},
						{field: 'address', title: '谈话地点', operate: 'LIKE'},
						{field: 'content', title: '谈话内容', operate: 'LIKE'},
                        {field: 'talk_time', title: __('Talk_time'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","620px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-people_id").data("eSelect", function(){
				$.get('People/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-grade_id').val(res.data.grade_id);
					$('#c-grade_id').selectPageRefresh();
					$('#c-p_name').val(res.data.name);
				});
			    //后续操作
			});
			$("#c-people_id1").data("eSelect", function(){
				$.get('People/huoq',{id:$("#c-people_id1").val()},function(res){
					$('#c-grade_id1').val(res.data.grade_id);
					$('#c-grade_id1').selectPageRefresh();
					$('#c-p_name1').val(res.data.name);
				});
			    //后续操作
			});
			$("#c-people_id2").data("eSelect", function(){
				$.get('People/huoq',{id:$("#c-people_id2").val()},function(res){
					$('#c-grade_id2').val(res.data.grade_id);
					$('#c-grade_id2').selectPageRefresh();
					$('#c-p_name2').val(res.data.name);
				});
			    //后续操作
			});
			$("#faupload-file").data("upload-success", function(data, ret){
			    //这里进行后续操作
			    $('#c-file_name').val(data.name);
			});
        },
        edit: function () {
            Controller.api.bindevent();
			$("#c-people_id").data("eSelect", function(){
				$.get('People/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-grade_id').val(res.data.grade_id);
					$('#c-grade_id').selectPageRefresh();
					$('#c-p_name').val(res.data.name);
				});
			    //后续操作
			});
			$("#c-people_id1").data("eSelect", function(){
				$.get('People/huoq',{id:$("#c-people_id1").val()},function(res){
					$('#c-grade_id1').val(res.data.grade_id);
					$('#c-grade_id1').selectPageRefresh();
					$('#c-p_name1').val(res.data.name);
				});
			    //后续操作
			});
			$("#c-people_id2").data("eSelect", function(){
				$.get('People/huoq',{id:$("#c-people_id2").val()},function(res){
					$('#c-grade_id2').val(res.data.grade_id);
					$('#c-grade_id2').selectPageRefresh();
					$('#c-p_name2').val(res.data.name);
				});
			    //后续操作
			});
			$("#faupload-file").data("upload-success", function(data, ret){
			    //这里进行后续操作
			    $('#c-file_name').val(data.name);
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