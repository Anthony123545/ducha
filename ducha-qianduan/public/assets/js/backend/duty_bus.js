define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            $(".btn-add").data("area", ["790px", "300px"]);
            Table.api.init({
                extend: {
                    index_url: 'duty_bus/index' + location.search,
                    add_url: 'duty_bus/add',
                    edit_url: 'duty_bus/edit',
                    del_url: 'duty_bus/del',
                    multi_url: 'duty_bus/multi',
                    import_url: 'duty_bus/import',
                    table: 'duty_bus',
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
                        {field: 'order_number', title: __('Id'),operate:false},
                        {field: 'number', title: __('Number'), operate: 'LIKE'},
                        {field: 'model_text', title: __('厂牌型号'),operate:false},
                        {field: 'category', title: __('Category'),operate:false},
                        {field: 'color', title: __('颜色'),operate:false},
                        {field: 'status', title: __('Status'),formatter:function(index,row){
                            if(row.status == 1){
                                return '<span class="text-success">在位</span>'
                            }else if(row.status == 2){
                                return '<span class="text-info">值班</span>'
                            }else if(row.status == 3){
                                var str = '<span class="text-danger">出车</span>'
                                if(row.cause)str+=' <i class="fa fa-commenting-o" data-toggle="tooltip" data-original-title="'+row.cause+'"></i>'
                                return str;
                            }else if(row.status == 4){
                                return '<span class="text-warning">待修</span>'
                            }
                        },operate:false},
                        {field: 'unit_id_text', title: __('车属单位'),operate:false},
                        {field: 'location', title: __('Location'),operate:false},
                        {field: 'driver_unit_id_text', title: __('司机单位'),operate:false},
                        {field: 'driver_id_text', title: __('司机姓名'),operate:false},
                        {field: 'contact', title: __('司机联系方式'),operate:false},
                        {field: 'date', title: __('Date'), operate:'RANGE', addclass:'datetimerange', autocomplete:false},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
            table.on('post-body.bs.table', function () {
                    $(".btn-editone").data("area", ["790px", "300px"]);
                })
        },
        add: function () {
            $(document).on("change", "#c-thebus_id", function(){
                $('#c-number').val($('#c-thebus_id_text').val());
                var ids = $(this).val();
                $.get('thebus/huoq',{ids:ids},function(res){
                    $('#c-unit_id').val(res.data.unit_id);
                    $('#c-unit_id').selectpicker('refresh');
                    $('#c-category').val(res.data.car_type);
                    $('#c-status').val(res.data.status);
                    $('#c-status').selectpicker('refresh');
                    $('#c-location').val(res.data.position);
                    $('#c-color').val(res.data.vehiclecolor);
                    $('#c-model').val(res.data.vehiclemodel);
                    $('#c-model_text').val(res.data.vehiclemodel_text);
                });
            });
            
            $(document).on('change','#c-driver_id',function(){
                var ids = $(this).val();
                $('#c-driver_id_texts').val($('#c-driver_id_text').val());
                $.get('driver/huoq',{ids:ids},function(res){
                    $('#c-driver_unit_id').val(res.data.unit_id);
                    $('#c-contact').val(res.data.contact);
                });
            });
            Controller.api.bindevent();
        },
        edit: function () {
            $(document).on("change", "#c-thebus_id", function(){
                $('#c-number').val($('#c-thebus_id_text').val());
                var ids = $(this).val();
                $.get('thebus/huoq',{ids:ids},function(res){
                    $('#c-unit_id').val(res.data.unit_id);
                    $('#c-unit_id').selectpicker('refresh');
                    $('#c-category').val(res.data.car_type);
                    $('#c-status').val(res.data.status);
                    $('#c-status').selectpicker('refresh');
                    $('#c-location').val(res.data.position);
                    $('#c-color').val(res.data.vehiclecolor);
                    $('#c-model').val(res.data.vehiclemodel);
                    $('#c-model_text').val(res.data.vehiclemodel_text);
                });
            });
            
            $(document).on('change','#c-driver_id',function(){
                var ids = $(this).val();
                $('#c-driver_id_texts').val($('#c-driver_id_text').val());
                $.get('driver/huoq',{ids:ids},function(res){
                    $('#c-driver_unit_id').val(res.data.unit_id);
                    $('#c-contact').val(res.data.contact);
                });
            });
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