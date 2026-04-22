define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            $(".btn-add").data("area",["1000px","450px"]);
            Table.api.init({
                extend: {
                    index_url: 'scheduling/index' + location.search,
                    add_url: 'scheduling/add',
                    edit_url: 'scheduling/edit',
                    del_url: 'scheduling/del',
                    multi_url: 'scheduling/multi',
                    import_url: 'scheduling/import',
                    table: 'scheduling',
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
                        {field: 'people_id_text', title: __('People_id')},
                        {field: 'people_id1_text', title: __('People_id1')},
                        {field: 'people_id2_text', title: __('People_id2')},
                        {field: 'people_id3_text', title: __('People_id3')},
                        {field: 'people_id4_text', title: __('People_id4')},
                        {field: 'people_id5_text', title: __('People_id5')},
						{field: 'unit_id_text', title: __('值班分队')},
                        {field: 'date', title: __('Date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
             table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["1000px","450px"]);
			})
        },
        add: function () {
            Controller.api.bindevent();
			$("#c-people_id6").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
			$("#c-people_id").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-phone').val(res.data.phone);
				});
			});
			$("#c-people_id1").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id1").val()},function(res){
					$('#c-phone1').val(res.data.phone);
				});
			});
			$("#c-people_id2").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id2").val()},function(res){
					$('#c-phone2').val(res.data.phone);
				});
			});
			$("#c-people_id3").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id3").val()},function(res){
					$('#c-phone3').val(res.data.phone);
				});
			});
			$("#c-people_id4").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id4").val()},function(res){
					$('#c-phone4').val(res.data.phone);
				});
			});
			$("#c-people_id5").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id5").val()},function(res){
					$('#c-phone5').val(res.data.phone);
				});
			});
			$("#c-people_id6").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id6").val()},function(res){
					$('#c-phone6').val(res.data.phone);
				});
			});
			$("#c-people_id7").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id7").val()},function(res){
					$('#c-phone7').val(res.data.phone);
				});
			});
        },
        edit: function () {
            Controller.api.bindevent();
			$("#c-people_id6").data("params", function (obj) {
			    var unit_id = $('#J_unitselect').val();
			    return {"custom[unit_id][0]":"in","custom[unit_id][1]":unit_id};
			});
			$("#c-people_id").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id").val()},function(res){
					$('#c-phone').val(res.data.phone);
				});
			});
			$("#c-people_id1").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id1").val()},function(res){
					$('#c-phone1').val(res.data.phone);
				});
			});
			$("#c-people_id2").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id2").val()},function(res){
					$('#c-phone2').val(res.data.phone);
				});
			});
			$("#c-people_id3").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id3").val()},function(res){
					$('#c-phone3').val(res.data.phone);
				});
			});
			$("#c-people_id4").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id4").val()},function(res){
					$('#c-phone4').val(res.data.phone);
				});
			});
			$("#c-people_id5").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id5").val()},function(res){
					$('#c-phone5').val(res.data.phone);
				});
			});
			$("#c-people_id6").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id6").val()},function(res){
					$('#c-phone6').val(res.data.phone);
				});
			});
			$("#c-people_id7").data("eSelect", function(){
				$.get('scheduling/huoq',{id:$("#c-people_id7").val()},function(res){
					$('#c-phone7').val(res.data.phone);
				});
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