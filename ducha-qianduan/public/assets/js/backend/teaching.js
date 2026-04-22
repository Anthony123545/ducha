define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            var pid = $("#page_pid").val()||0;
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'teaching/index' + location.search,
                    add_url: 'teaching/add',
                    edit_url: 'teaching/edit',
                    del_url: 'teaching/del',
                    multi_url: 'teaching/multi',
                    import_url: '',
                    table: 'teaching',
                }
            });

            var table = $("#table");


            // 初始化表格
            table.bootstrapTable({
                url: $.fn.bootstrapTable.defaults.extend.index_url,
                pk: 'id',
                sortName: 'type',
                sortOrder: 'desc',
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
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter:
                                function(value, row, index) {
                                    var that = $.extend({}, this);
                                    var table = $(that.table).clone(true);
                                    // operate-edit编辑  perate-del删除
                                    //判断什么时候显示什么时候不显示
                                    if (row.is_me != 1) {
                                        $(table).data("operate-del", null); // 列表页面隐藏 .编辑operate-edit  - 删除按钮operate-del
                                        $(table).data("operate-edit", null); // 列表页面隐藏 .编辑operate-edit  - 删除按钮operate-del
                                    }
                                    that.table = table;
                                    return Table.api.formatter.operate.call(that, value, row, index);
                                }

                        }
                    ]
                ]
            });

            $('#myModal').on('click','#J_new_folder', function () {
                var folder_name = $('#folder_name').val();
                if (folder_name){
                    $.get('teaching/make_folder',{pid:pid,name:folder_name},function(res){
                        if (res.code == 1){
                            $('#myModal').modal('hide')
                            var container = $("#table").data("bootstrap.table").$container;
                            var options = $("#table").bootstrapTable('getOptions');
                            $("form.form-commonsearch", container).trigger('submit');
                            Toastr.success(res.msg);
                        }else{
                            Toastr.error(res.msg);
                        }
                    });
                }else{
                    alert('请输入文件夹名称')
                }
            })
			var toolbar = ".toolbar";
			// 导入按钮事件
			if ($('.btn-updata', toolbar).size() > 0) {
			    require(['upload'], function (Upload) {
			        Upload.api.upload($('.btn-updata', toolbar), function (data, ret) {
			            table.trigger("uncheckbox");
			            table.bootstrapTable('refresh');
						if(data == 1){
							Toastr.success('操作成功');
						}else{
							Toastr.error('操作失败');
						}
			        });
			    });
			}
            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table',function(){
				$(".btn-editone").data("area",["480px","360px"]);
			})

            table.on('post-common-search.bs.table',function(){
                console.log('search')
            });
        },
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
                url: 'teaching/recyclebin' + location.search,
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
                                    url: 'teaching/restore',
                                    refresh: true
                                },
                                {
                                    name: 'Destroy',
                                    text: __('Destroy'),
                                    classname: 'btn btn-xs btn-danger btn-ajax btn-destroyit',
                                    icon: 'fa fa-times',
                                    url: 'teaching/destroy',
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
                    if(row.type!='folder')return '<a href="'+ (row.url||'') +'" class="btn-ip" target="_blank" download="'+row.name+'"><i class="fa fa-file text-danger"></i> '+value + '</a>';
                    return '<a href="?pid='+row.id+'" class=" btn-ip" data-toggle="tooltip" title="" data-field="pid" data-value="'+row.id+'" data-original-title="'+value+'" '+index+'><i style="color: #E1BF4C" class="fa fa-folder"></i> '+value+'</a>';
                },
                operate:function (value, row, index) {
                    return '<a href="/teaching/edit/ids/'+row.id+'" class="btn btn-xs btn-success btn-editone" data-toggle="tooltip" title="" data-table-id="table" data-field-index="9" data-row-index="0" data-button-index="1" data-original-title="编辑"><i class="fa fa-pencil"></i></a> <a href="javascript:;" class="btn btn-xs btn-danger btn-delone" data-toggle="tooltip" title="删除" data-table-id="table" data-field-index="9" data-row-index="0" data-button-index="2"><i class="fa fa-trash"></i></a>'
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
        }
    };
    return Controller;
});