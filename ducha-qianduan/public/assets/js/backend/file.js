define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area", ["1020px", "775px"]);
            // 初始化表格参数配置
            Table.api.init();
            this.table.first();
            this.table.second();
            $('#myModal').on('click','#J_new_folder', function () {
                var pid = $("#one #pid").val();
                // alert();
                var folder_name = $('#folder_name').val();
                if (folder_name){
                    $.get('file/make_folder',{pid:pid,name:folder_name},function(res){
                        if (res.code == 1){
                            $('#myModal').modal('hide')
                            $("#toolbar1 .btn-refresh").trigger("click");
                            Toastr.success(res.msg);
                        }else{
                            Toastr.error(res.msg);
                        }
                    });
                }else{
                    alert('请输入文件夹名称')
                }
            });
			
        },
        table: {
            first: function () {
                // 表格1
                var table1 = $("#table1");
                table1.bootstrapTable({
                    url: 'file/index' + location.search+'&type=folder',
                    extend: {
    					index_url: 'file/index' + location.search+'&type=folder',
                        add_url: 'file/add',
                        edit_url: 'file/edit',
                        del_url: 'file/del',
                        multi_url: 'file/multi',
                        import_url: '',
                        table: 'file',
                    },
                    toolbar: '#toolbar1',
                    sortName: 'id',
                    search: false,
                    pagination: false,
                    columns: [
                        [
                            {field: 'pid', title: __('Pid'),visible:false},
                            {
                                
                                field: 'operate', title: __('文件夹'), table: table1, events: Table.api.events.operate, buttons: [
                                    {
                                        name: 'log',
                                        title: '',
                                        text: function(row){return row.name},
                                        classname: 'btn text-success btn-click btn-custom',
                                        click: function (e, data) {
                                            if(data.id>0){
                                                $("#back").data('pid',data.id);
                                                $("#back").removeClass('hide');
                                            }else{
                                                $("#back").data('pid',0);
                                                $("#back").addClass('hide');
                                            }
                                            $("#myTabContent2 .form-commonsearch input[name='pid']").val(data.id);
                                            $("#myTabContent2 .form-commonsearch input[name='type']").val('file');
                                            $("#myTabContent2 .btn-refresh").trigger("click");
                                            $("#one #pid").val(data.id);
                                            $("#one .btn-refresh").trigger("click");
                                            $('#btn-upload-file').data('url','file/upload?pid='+data.id);
                                            Controller.api.reloadupload();
                                        }
                                    }
                                ], formatter: Table.api.formatter.operate
                            }
                        ]
                    ]
                });
                $('#back').click(function(){
                    var ids = $("#one #pid").val();;
                    if(ids>0){
                        $.get('file/get_pid',{'ids':ids},function(res){
                            $("#myTabContent2 .form-commonsearch input[name='pid']").val(res.data);
                            $("#myTabContent2 .form-commonsearch input[name='type']").val('file');
                            $("#myTabContent2 .btn-refresh").trigger("click");
                            $("#one #pid").val(res.data);
                            $("#one .btn-refresh").trigger("click");
                            $('#btn-upload-file').data('url','file/upload?pid='+res.data);
                            Controller.api.reloadupload();
                        });
                        
                    }
                    
                });
                // 为表格1绑定事件
                Table.api.bindevent(table1);
            },
            second: function () {
                // 表格2
                var table2 = $("#table2");
                table2.bootstrapTable({
                    url: 'file/index' + location.search,
                    extend: {
    					index_url: 'file/index' + location.search,
                        add_url: 'file/add',
                        edit_url: 'file/edit',
                        del_url: 'file/del',
                        multi_url: 'file/multi',
                        import_url: '',
                        table: 'file',
                    },
                    toolbar: '#toolbar2',
                    sortName: 'id',
                    search: false,
    				columns: [

					[

						{checkbox: true},
                        // {field: 'id', title: __('Id')},
                        {field: 'pid', title: __('Pid'),visible:false},
                        {field: 'name', title: __('Name'), operate: 'LIKE',formatter: Controller.api.formatter.url,align:'left'},
                        {field: 'url', title: __('Url'), operate: 'LIKE', formatter: Table.api.formatter.url,visible:false},
                        {field: 'type', title: __('Type'), operate: 'LIKE',visible:false},
                        {field: 'size', title: __('Size'), operate: 'LIKE'},
                        {field: 'admin_name', title: __('上传人')},
                        {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime,visible:false},
                        {field: 'updatetime', title: __('Updatetime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table2, events: Table.api.events.operate,buttons:[
                            {
                                name:'下载',
                                title:'下载',
                                text:'下载',
                                icon:'fa fa-download',
                                url:function(row){
                                    return row.url;
                                },
                                classname:'btn btn-info btn-xs',
                                extend:'download=""',
                                visible:function(row){
                                    if(row.type=='file'){
                                        return true;
                                    }
                                    return false;
                                }
                            }
                            ], formatter:
                                function(value, row, index) {
                                    var that = $.extend({}, this);
                                    var table = $(that.table).clone(true);
                                    // operate-edit编辑  perate-del删除
                                    //判断什么时候显示什么时候不显示
                                    if (row.is_me != 1) {
                                        $(table).data("operate-del", null); // 列表页面隐藏 .编辑operate-edit  - 删除按钮operate-del
                                        $(table).data("operate-edit", null); // 列表页面隐藏 .编辑operate-edit  - 删除按钮operate-del
                                    }
                                    that.table = table2;
                                    return Table.api.formatter.operate.call(that, value, row, index);
                                }

                        }

					]

				]
                });
                var toolbar = ".toolbar";
    			if ($('.btn-updata', toolbar).size() > 0) {
    			    require(['upload'], function (Upload) {
    			        Upload.api.upload($('.btn-updata', toolbar), function (data, ret) {
    			            table2.trigger("uncheckbox");
    			            table2.bootstrapTable('refresh');
    						if(data == 1){
    							Toastr.success('操作成功');
    						}else{
    							Toastr.error('操作失败');
    						}
    			        });
    			    });
    			}
                // 为表格2绑定事件
                Table.api.bindevent(table2);
                table2.on('post-body.bs.table', function () {
                    $(".btn-editone").data("area", ["460px", "280px"]);
                })
            }
        },
        
        
//         index: function () {
//             var pid = $("#page_pid").val()||0;
//             // 初始化表格参数配置
//             Table.api.init({
//                 extend: {
//                     index_url: 'file/index' + location.search,
//                     add_url: 'file/add',
//                     edit_url: 'file/edit',
//                     del_url: 'file/del',
//                     multi_url: 'file/multi',
//                     import_url: '',
//                     table: 'file',
//                 }
//             });

//             var table = $("#table");


//             // 初始化表格
//             table.bootstrapTable({
//                 url: $.fn.bootstrapTable.defaults.extend.index_url,
//                 pk: 'id',
//                 sortName: 'type',
//                 sortOrder: 'desc',
//                 columns: [
//                     [
//                         {checkbox: true},
//                         // {field: 'id', title: __('Id')},
//                         {field: 'pid', title: __('Pid'),visible:false},
//                         {field: 'name', title: __('Name'), operate: 'LIKE',formatter: Controller.api.formatter.url,align:'left'},
//                         {field: 'url', title: __('Url'), operate: 'LIKE', formatter: Table.api.formatter.url,visible:false},
//                         {field: 'type', title: __('Type'), operate: 'LIKE',visible:false},
//                         {field: 'size', title: __('Size'), operate: 'LIKE'},
//                         {field: 'admin_name', title: __('上传人')},
//                         {field: 'createtime', title: __('Createtime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime,visible:false},
//                         {field: 'updatetime', title: __('Updatetime'), operate:'RANGE', addclass:'datetimerange', autocomplete:false, formatter: Table.api.formatter.datetime},
//                         {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate,buttons:[
//                             {
//                                 name:'下载',
//                                 title:'下载',
//                                 text:'下载',
//                                 icon:'fa fa-download',
//                                 url:function(row){
//                                     return row.url;
//                                 },
//                                 classname:'btn btn-info',
//                                 extend:'download=""',
//                                 visible:function(row){
//                                     if(row.type=='file'){
//                                         return true;
//                                     }
//                                     return false;
//                                 }
//                             }
//                             ], formatter:
//                                 function(value, row, index) {
//                                     var that = $.extend({}, this);
//                                     var table = $(that.table).clone(true);
//                                     // operate-edit编辑  perate-del删除
//                                     //判断什么时候显示什么时候不显示
//                                     if (row.is_me != 1) {
//                                         $(table).data("operate-del", null); // 列表页面隐藏 .编辑operate-edit  - 删除按钮operate-del
//                                         $(table).data("operate-edit", null); // 列表页面隐藏 .编辑operate-edit  - 删除按钮operate-del
//                                     }
//                                     that.table = table;
//                                     return Table.api.formatter.operate.call(that, value, row, index);
//                                 }

//                         }
//                     ]
//                 ]
//             });

//             $('#myModal').on('click','#J_new_folder', function () {
//                 var folder_name = $('#folder_name').val();
//                 if (folder_name){
//                     $.get('file/make_folder',{pid:pid,name:folder_name},function(res){
//                         if (res.code == 1){
//                             $('#myModal').modal('hide')
//                             var container = $("#table").data("bootstrap.table").$container;
//                             var options = $("#table").bootstrapTable('getOptions');
//                             $("form.form-commonsearch", container).trigger('submit');
//                             Toastr.success(res.msg);
//                         }else{
//                             Toastr.error(res.msg);
//                         }
//                     });
//                 }else{
//                     alert('请输入文件夹名称')
//                 }
//             })
// 			var toolbar = ".toolbar";
// 			// 导入按钮事件
// 			if ($('.btn-updata', toolbar).size() > 0) {
// 			    require(['upload'], function (Upload) {
// 			        Upload.api.upload($('.btn-updata', toolbar), function (data, ret) {
// 			            table.trigger("uncheckbox");
// 			            table.bootstrapTable('refresh');
// 						if(data == 1){
// 							Toastr.success('操作成功');
// 						}else{
// 							Toastr.error('操作失败');
// 						}
// 			        });
// 			    });
// 			}
//             // 为表格绑定事件
//             Table.api.bindevent(table);
//             table.on('post-body.bs.table',function(){
// 				$(".btn-editone").data("area",["450px","360px"]);
// 			})

//             table.on('post-common-search.bs.table',function(){
//                 console.log('search')
//             });
//         },
        recyclebin: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    'dragsort_url': ''
                }
            });

            var table = $("#table");

            // 初始化表格
            table.bootstrapTable({
                url: 'file/recyclebin' + location.search,
                pk: 'id',
                sortName: 'id',
                columns: [
                    [
                        {checkbox: true},
                        {field: 'id', title: __('Id')},
                        {field: 'name', title: __('Name'), align: 'left'},
                        {
                            field: 'deletetime',
                            title: __('Deletetime'),
                            operate: 'RANGE',
                            addclass: 'datetimerange',
                            formatter: Table.api.formatter.datetime
                        },
                        {
                            field: 'operate',
                            width: '130px',
                            title: __('Operate'),
                            table: table,
                            events: Table.api.events.operate,
                            buttons: [
                                {
                                    name: 'Restore',
                                    text: __('Restore'),
                                    classname: 'btn btn-xs btn-info btn-ajax btn-restoreit',
                                    icon: 'fa fa-rotate-left',
                                    url: 'file/restore',
                                    refresh: true
                                },
                                {
                                    name: 'Destroy',
                                    text: __('Destroy'),
                                    classname: 'btn btn-xs btn-danger btn-ajax btn-destroyit',
                                    icon: 'fa fa-times',
                                    url: 'file/destroy',
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
                url: function (value, row, index) {
                    if(row.type!='folder')return '<a href="'+ (row.url||'') +'" class="btn-ip" target="_blank" download=""><i class="fa fa-file text-danger"></i> '+value + '</a>';
                    return '<a href="?pid='+row.id+'" class=" btn-ip" data-toggle="tooltip" title="" data-field="pid" data-value="'+row.id+'" data-original-title="'+value+'" '+index+'><i style="color: #E1BF4C" class="fa fa-folder"></i> '+value+'</a>';
                },
                operate:function (value, row, index) {
                    return '<a href="/file/edit/ids/'+row.id+'" class="btn btn-xs btn-success btn-editone" data-toggle="tooltip" title="" data-table-id="table" data-field-index="9" data-row-index="0" data-button-index="1" data-original-title="编辑"><i class="fa fa-pencil"></i></a> <a href="javascript:;" class="btn btn-xs btn-danger btn-delone" data-toggle="tooltip" title="删除" data-table-id="table" data-field-index="9" data-row-index="0" data-button-index="2"><i class="fa fa-trash"></i></a>'
                },
            },
            events: {
                url: {
                    //格式为：方法名+空格+DOM元素
                    // 'click .btn-ip': function (e, value, row, index) {
                    //     e.stopPropagation();
                    //     var container = $("#table").data("bootstrap.table").$container;
                    //     var options = $("#table").bootstrapTable('getOptions');
                    //     //这里我们手动将数据填充到表单然后提交
                    //     $("form.form-commonsearch [name='pid']", container).val(row.pid);
                    //     $("form.form-commonsearch", container).trigger('submit');
                    // }
                },
            }
            ,reloadupload:function(){
                var table2 = $('#table2');
                var toolbar = ".toolbar";
    			if ($('.btn-updata', toolbar).size() > 0) {
    			    require(['upload'], function (Upload) {
    			        Upload.api.upload($('.btn-updata', toolbar), function (data, ret) {
    			            table2.trigger("uncheckbox");
    			            table2.bootstrapTable('refresh');
    						if(data == 1){
    							Toastr.success('操作成功');
    						}else{
    							Toastr.error('操作失败');
    						}
    			        });
    			    });
    			}
            }
        }
    };
    return Controller;
});