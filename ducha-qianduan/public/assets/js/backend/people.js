define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {


    var Controller = {
        index: function () {
            $(".btn-add").data("area", ["1020px", "775px"]);
            // 初始化表格参数配置
            Table.api.init();
            this.table.first();
            this.table.second();
        },
        table: {
            first: function () {
                // 表格1
                var table1 = $("#table1");
                table1.bootstrapTable({
                    url: 'unit/get_unit',
                    toolbar: '#toolbar1',
                    sortName: 'id',
                    search: false,
                    pagination: false,
                    columns: [
                        [
                            {
                                field: 'operate', title: __('单位'), table: table1, events: Table.api.events.operate, buttons: [
                                    {
                                        name: 'log',
                                        title: '',
                                        text: function(row){return row.name},
                                        classname: 'btn btn-success btn-xs btn-click btn-custom',
                                        click: function (e, data) {
                                            console.log(data)
                                            $("#myTabContent2 .form-commonsearch select[name='unit_id']").val(data.id);
                                            $("#myTabContent2 .btn-refresh").trigger("click");
                                        }
                                    }
                                ], formatter: Table.api.formatter.operate
                            }
                        ]
                    ]
                });

                // 为表格1绑定事件
                Table.api.bindevent(table1);
            },
            second: function () {
                // 表格2
                var table = $("#table2");
                $(".btn-add").data("area",["1000px","90%"]);
    			// $(".btn-edit").data("area",["860px","90%"]);
                // 初始化表格参数配置
                Table.api.init({
                    extend: {
                        index_url: 'people/index' + location.search,
                        add_url: 'people/add',
                        edit_url: 'people/edit',
                        del_url: 'people/del',
                        multi_url: 'people/multi',
                        import_url: 'people/import',
                        table: 'people',
                    }
                });
                Controller.api.bindevent();
                // var table = $("#table");
    			
                // 初始化表格
                table.bootstrapTable({
                    url: $.fn.bootstrapTable.defaults.extend.index_url,
                    pk: 'id',
                    sortName: 'id',
                    columns: [
                        [
                            {checkbox: true},
                            {field: 'order_number', title: __('序号'),operate:false},
                            {field: 'name', title: __('Name'), operate: 'LIKE'},
                            {field: 'sex_list', title: __('Sex_list'), searchList: {"男":__('男'),"女":__('女')}, formatter: Table.api.formatter.normal},
                            {field: 'age', title: __('Age'),operate:false},
                            {field: 'duty', title: __('Duty_id'),operate:false},
                            {field: 'unit_id', title: __('unit_id'),visible:false, searchList: $.getJSON("unit/index?lists=1")},
                            {field: 'unit_id_text', title: __('unit_id'),operate:false},
                            {field: 'post_id', title: __('Post_id'),visible:false, searchList: $.getJSON("people/post")},
                            {field: 'grade_id_text', title: __('Grade_id'),operate:false},
                            {field: 'post_date', title: __('Post_date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false,operate:false},
                            {field: 'job', title: __('Job'),operate:false},
                            {field: 'skill_id', title: __('Skill_id'),operate:'LIKE',visible:false, searchList: $.getJSON("people/skill")},
    						{field: 'rank_id', title: __('Skill_id'),operate:'LIKE',visible:false, searchList: $.getJSON("people/rank")},
                            {field: 'skill_date', title: __('Skill_date'),operate:false, addclass:'datetimerange', autocomplete:false},
                            {field: 'level_id_text', title: __('Level_id'),operate:false},
                            {field: 'level_date', title: __('Level_date'),operate:false, addclass:'datetimerange', autocomplete:false},
                            {field: 'origin', title: __('Origin'),operate:false},
                            {field: 'education_id_text', title: __('Education_id'),operate:false},
                            {field: 'school', title: __('School'),operate:false},
                            {field: 'reign', title: __('人员状态'), searchList: {'0':__('在位'),'1':__('休假'), '2':__('出差'), '6':__('外诊'), '4':__('学习'), '3': __('借调'), '5' : __('执行任务'), '7':__('其他')}, formatter: Table.api.formatter.normal},
                            {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                        ]
                    ]
                });
                // 为表格绑定事件
                Table.api.bindevent(table);
    			table.on('post-body.bs.table',function(){
    				$(".btn-editone").data("area",["1000px","90%"]);
    			})
            }
        },
        
        
//         index: function () {
// 			$(".btn-add").data("area",["1000px","90%"]);
// 			// $(".btn-edit").data("area",["860px","90%"]);
//             // 初始化表格参数配置
//             Table.api.init({
//                 extend: {
//                     index_url: 'people/index' + location.search,
//                     add_url: 'people/add',
//                     edit_url: 'people/edit',
//                     del_url: 'people/del',
//                     multi_url: 'people/multi',
//                     import_url: 'people/import',
//                     table: 'people',
//                 }
//             });
//             Controller.api.bindevent();
//             var table = $("#table");
			
//             // 初始化表格
//             table.bootstrapTable({
//                 url: $.fn.bootstrapTable.defaults.extend.index_url,
//                 pk: 'id',
//                 sortName: 'id',
//                 columns: [
//                     [
//                         {checkbox: true},
//                         {field: 'order_number', title: __('序号'),operate:false},
//                         {field: 'name', title: __('Name'), operate: 'LIKE'},
//                         {field: 'sex_list', title: __('Sex_list'), searchList: {"男":__('男'),"女":__('女')}, formatter: Table.api.formatter.normal},
//                         {field: 'age', title: __('Age'),operate:false},
//                         {field: 'duty', title: __('Duty_id'),operate:false},
//                         {field: 'unit_id', title: __('unit_id'),visible:false, searchList: $.getJSON("unit/index?lists=1")},
//                         {field: 'unit_id_text', title: __('unit_id'),operate:false},
//                         {field: 'post_id', title: __('Post_id'),visible:false, searchList: $.getJSON("people/post")},
//                         {field: 'grade_id_text', title: __('Grade_id'),operate:false},
//                         {field: 'post_date', title: __('Post_date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false,operate:false},
//                         {field: 'job', title: __('Job'),operate:false},
//                         {field: 'skill_id', title: __('Skill_id'),operate:'LIKE',visible:false, searchList: $.getJSON("people/skill")},
// 						{field: 'rank_id', title: __('Skill_id'),operate:'LIKE',visible:false, searchList: $.getJSON("people/rank")},
//                         {field: 'skill_date', title: __('Skill_date'),operate:false, addclass:'datetimerange', autocomplete:false},
//                         {field: 'level_id_text', title: __('Level_id'),operate:false},
//                         {field: 'level_date', title: __('Level_date'),operate:false, addclass:'datetimerange', autocomplete:false},
//                         {field: 'origin', title: __('Origin'),operate:false},
//                         {field: 'education_id_text', title: __('Education_id'),operate:false},
//                         {field: 'school', title: __('School'),operate:false},
//                         {field: 'reign', title: __('人员状态'), searchList: {'0':__('在位'),'1':__('休假'), '2':__('出差'), '6':__('外诊'), '4':__('学习'), '3': __('借调'), '5' : __('执行任务'), '7':__('其他')}, formatter: Table.api.formatter.normal},
//                         {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
//                     ]
//                 ]
//             });
//             // 为表格绑定事件
//             Table.api.bindevent(table);
// 			table.on('post-body.bs.table',function(){
// 				$(".btn-editone").data("area",["1000px","90%"]);
// 			})
//         },
        add: function () {
            Controller.api.bindevent();
			$("#faupload-readme_file").data("upload-success", function(data, ret){
			    //这里进行后续操作
			    $('#c-readme_file_name').val(data.name);
			});
        },
        edit: function () {
            Controller.api.bindevent();
			$("#faupload-readme_file").data("upload-success", function(data, ret){
			    //这里进行后续操作
			    $('#c-readme_file_name').val(data.name);
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