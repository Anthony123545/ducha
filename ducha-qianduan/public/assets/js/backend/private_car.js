define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            $(".btn-add").data("area",["1000px","450px"]);
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'private_car/index' + location.search,
                    add_url: 'private_car/add',
                    edit_url: 'private_car/edit',
                    del_url: 'private_car/del',
                    multi_url: 'private_car/multi',
                    import_url: 'private_car/import',
                    table: 'private_car',
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
						{field: 'people_name', title: __('People_id'), operate: 'LIKE'},
                        {field: 'vehiclenumber', title: __('Vehiclenumber'), operate: 'LIKE'},
                        {field: 'vehiclemodel', title: __('Vehiclemodel'), operate: 'LIKE'},
                        {field: 'vehiclecolor', title: __('Vehiclecolor'), operate: 'LIKE'},
                        {field: 'vehiclecategory', title: __('Vehiclecategory'), searchList: {"临时":__('临时'),"内部":__('内部')}, formatter: Table.api.formatter.normal},
                        {field: 'contactnumber', title: __('Contactnumber'), operate: 'LIKE'},
                        {field: 'documenttype', title: __('Documenttype'), searchList: {"身份证":__('身份证'),"军人证":__('军人证'),"护照":__('护照'),"居住证":__('居住证'),"签证":__('签证'),"户口本":__('户口本'),"团员证":__('团员证'),"党员证":__('党员证'),"港澳通行证":__('港澳通行证')}, formatter: Table.api.formatter.normal},
                        {field: 'certificatenumber', title: __('Certificatenumber'), operate: 'LIKE'},
                        {field: 'remarks', title: __('Remarks'), operate: 'LIKE'},
                        {field: 'admin_id_text', title: __('Admin_id')},
                        {field: 'createtime', title: __('Createtime'), operate: 'LIKE'},
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
            $(document).on('change','#c-vehiclecategory',function(){
                if($(this).val()=='内部'){
                    $('#c-people_id_text').parent().show();
                    $('#c-people_name').hide();
                }else{
                    $('#c-people_id_text').parent().hide();
                    $('#c-people_id').val('');
                    $('#c-people_id_text').val('');
                    $('#c-people_name').show();
                }
            });
            $(document).on("change", "#c-people_id", function(){
                $('#c-people_name').val($('#c-people_id_text').val());
            });
        },
        edit: function () {
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