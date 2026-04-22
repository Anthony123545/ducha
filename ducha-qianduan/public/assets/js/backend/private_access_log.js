define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'private_access_log/index' + location.search,
                    add_url: 'private_access_log/add',
                    edit_url: 'private_access_log/edit',
                    del_url: 'private_access_log/del',
                    multi_url: 'private_access_log/multi',
                    import_url: 'private_access_log/import',
                    table: 'private_access_log',
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
                        {field: 'order_number', title: __('Id'), operate: false},
                        {field: 'vehiclenumber', title: __('Vehiclenumber'), operate: false},
                        {field: 'vehiclecategory', title: __('Vehiclecategory'), searchList: {"临时":__('临时'),"内部":__('内部')}, formatter: Table.api.formatter.normal},
                        {field: 'vehiclemodel', title: __('Vehiclemodel'), operate: false},
                        {field: 'vehiclecolor', title: __('Vehiclecolor'), operate: false},
                        {field: 'people_id_text', title: __('People_id'), operate: false},
                        {field: 'contactnumber', title: __('Contactnumber'), operate: false},
                        {field: 'time1', title: __('Time1'), operate: false},
                        {field: 'time2', title: __('Time2'), operate: false},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate
                            ,buttons:[
                            {
								name: '返回',
								text: '返回',
								title: '返回',
								classname: 'btn btn-xs btn-warning btn-view btn-ajax ',
								icon: 'fa fa-search',
								refresh: true,
								url: 'private_access_log/goback',
								confirm:'确认已返回？',
								visible:function(row){
								    if(row.time2=='-'){
								        return true;
								    }
								    return false;
								}
								
							}
                            ]
                        }
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
        },
        add: function () {
            $(document).on("change", "#c-private_car_id", function(){
                $('#c-vehiclenumber').val($('#c-private_car_id_text').val());
                var ids = $(this).val();
                $.get('private_car/huoq',{ids:ids},function(res){
                    $('#c-vehiclecategory').val(res.data.vehiclecategory);
                    $('#c-vehiclecategory').selectpicker('refresh');
                    $('#c-vehiclemodel').val(res.data.vehiclemodel);
                    $('#c-people_id').val(res.data.people_id);
                    $('#c-people_id').selectPageRefresh();
                    $('#c-vehiclecolor').val(res.data.vehiclecolor);
                    $('#c-contactnumber').val(res.data.contactnumber);
                    
                });
            });
            $(document).on('change','#c-driver_id',function(){
                var ids = $(this).val();
                $.get('people/huoq',{ids:ids},function(res){
                    $('#c-contactnumber').val(res.data.contact);
                });
            });
            Controller.api.bindevent();
        },
        edit: function () {
            $(document).on("change", "#c-private_car_id", function(){
                $('#c-vehiclenumber').val($('#c-private_car_id_text').val());
                var ids = $(this).val();
                $.get('private_car/huoq',{ids:ids},function(res){
                    $('#c-vehiclecategory').val(res.data.vehiclecategory);
                    $('#c-vehiclecategory').selectpicker('refresh');
                    $('#c-vehiclemodel').val(res.data.vehiclemodel);
                    $('#c-people_id').val(res.data.people_id);
                    $('#c-people_id').selectPageRefresh();
                    $('#c-vehiclecolor').val(res.data.vehiclecolor);
                    $('#c-contactnumber').val(res.data.contactnumber);
                    
                });
            });
            $(document).on('change','#c-driver_id',function(){
                var ids = $(this).val();
                $.get('people/huoq',{ids:ids},function(res){
                    $('#c-contactnumber').val(res.data.contact);
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