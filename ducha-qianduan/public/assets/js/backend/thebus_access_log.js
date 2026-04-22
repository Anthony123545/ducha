define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'thebus_access_log/index' + location.search,
                    add_url: 'thebus_access_log/add',
                    edit_url: 'thebus_access_log/edit',
                    del_url: 'thebus_access_log/del',
                    multi_url: 'thebus_access_log/multi',
                    import_url: 'thebus_access_log/import',
                    table: 'thebus_access_log',
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
                        {field: 'vehiclemodel_text', title: __('Vehiclemodel'), operate: false},
                        {field: 'parkingspacenumber', title: __('Parkingspacenumber'), operate: false},
                        {field: 'vehiclecolor', title: __('Vehiclecolor'), operate: false},
                        {field: 'unit_id_text', title: __('Unit_id'), operate: false},
                        {field: 'driver_id_text', title: __('Driver_id'), operate: false},
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
								url: 'thebus_access_log/goback',
								confirm:'确认已返回？',
								data:{
								    row:{time2:Controller.api.gettime()}
								},
								visible:function(row){
								    if(row.time2=='-'){
								        return true;
								    }
								    return false;
								}
								
							}
                            ]}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
        },
        add: function () {
            $(document).on("change", "#c-thebus_id", function(){
                $('#c-vehiclenumber').val($('#c-thebus_id_text').val());
                var ids = $(this).val();
                $.get('thebus/huoq',{ids:ids},function(res){
                    $('#c-unit_id').val(res.data.unit_id);
                    $('#c-unit_id').selectpicker('refresh');
                    $('#c-vehiclemodel').val(res.data.vehiclemodel);
                    $('#c-parkingspacenumber').val(res.data.parkingspacenumber);
                    $('#c-vehiclecolor').val(res.data.vehiclecolor);
                    $('#c-vehiclemodel').selectPageRefresh ();
                });
            });
            $(document).on('change','#c-driver_id',function(){
                var ids = $(this).val();
                $.get('driver/huoq',{ids:ids},function(res){
                    $('#c-contactnumber').val(res.data.contact);
                });
            });
            $("#c-driver_id").data("params", function (obj) {
                return {custom: {unit_id: $("#c-unit_id").val()}};
            });
            Controller.api.bindevent();
        },
        edit: function () {
            $(document).on("change", "#c-thebus_id", function(){
                $('#c-vehiclenumber').val($('#c-thebus_id_text').val());
                var ids = $(this).val();
                $.get('thebus/huoq',{ids:ids},function(res){
                    $('#c-unit_id').val(res.data.unit_id);
                    $('#c-unit_id').selectpicker('refresh');
                    $('#c-vehiclemodel').val(res.data.vehiclemodel);
                    $('#c-parkingspacenumber').val(res.data.parkingspacenumber);
                    $('#c-vehiclecolor').val(res.data.vehiclecolor);
                    $('#c-vehiclemodel').selectPageRefresh ();
                });
            });
            $(document).on('change','#c-driver_id',function(){
                var ids = $(this).val();
                $.get('driver/huoq',{ids:ids},function(res){
                    $('#c-contactnumber').val(res.data.contact);
                });
            });
            $("#c-driver_id").data("params", function (obj) {
                return {custom: {unit_id: $("#c-unit_id").val()}};
            });
            Controller.api.bindevent();
        },
        api: {
            bindevent: function () {
                Form.api.bindevent($("form[role=form]"));
            },
            gettime:function(){
                //判断是否在前面加0
                function getNow(s) {
                return s < 10 ? '0' + s: s;
                }
                 
                var myDate = new Date();            
                 
                var year=myDate.getFullYear();        //获取当前年
                var month=myDate.getMonth()+1;   //获取当前月
                var date=myDate.getDate();            //获取当前日
                 
                 
                var h=myDate.getHours();              //获取当前小时数(0-23)
                var m=myDate.getMinutes();          //获取当前分钟数(0-59)
                var s=myDate.getSeconds();
                 
                var now=year+'-'+getNow(month)+"-"+getNow(date)+" "+getNow(h)+':'+getNow(m)+":"+getNow(s);
                return now;
            }
        },
        
        
    };
    return Controller;
});