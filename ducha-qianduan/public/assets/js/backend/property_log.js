define(['jquery', 'bootstrap', 'backend', 'table', 'form'], function ($, undefined, Backend, Table, Form) {

    var Controller = {
        index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'property_log/index' + location.search,
                    add_url: 'property_log/add',
                    edit_url: 'property_log/edit',
                    del_url: 'property_log/del',
                    multi_url: 'property_log/multi',
                    import_url: 'property_log/import',
                    table: 'property_log',
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
                        {field: 'p.type', title: __('类型'), searchList:{0:'公物',1:'营具'} ,visible:false},
                        {field: 'name', title: __('公用物资名称'), operate: 'LIKE'},
                        {field: 'specification', title: __('Specification'),operate:false},
                        {field: 'version', title: __('Version'),operate:false},
                        {field: 'value', title: __('Value'),operate:false},
                        {field: 'time2', title: __('Time2'),operate:false,formatter: Table.api.formatter.datetime},
                        {field: 'amount', title: __('Amount'),operate:false},
                        // {field: 'surplus', title: __('现有库存'),operate:false},
                        
                        {field: 'admin_id_text', title: __('Admin_id'),operate:false},
                        {field: 'time1', title: __('入库时间'),operate:false,formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });
    
            // 为表格绑定事件
            Table.api.bindevent(table);
        },index1: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'property_log/index1' + location.search,
                    add_url: 'property_log/add1',
                    edit_url: 'property_log/edit1',
                    del_url: 'property_log/del',
                    multi_url: 'property_log/multi',
                    import_url: 'property_log/import',
                    table: 'property_log',
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
                        {field: 'p.type', title: __('类型'), searchList:{0:'公物',1:'营具'} ,visible:false},
                        {field: 'name', title: __('公用物资名称'), operate: false},
                        {field: 'specification', title: __('Specification'), operate: false},
                        {field: 'version', title: __('Version'), operate: false},
                        {field: 'category_id_text', title: __('Category_id'), operate: false},
                        {field: 'amount', title: __('Amount'), operate: false},
                        {field: 'value', title: __('Value'), operate: false},
                        {field: 'time1', title: __('发放时间'),formatter: Table.api.formatter.datetime, operate: false},
                        {field: 'unit1_id_text', title: __('Unit1_id'), operate: false},
                        {field: 'unit_id_text', title: __('Unit_id'), operate: false},
                        {field: 'people_id_text', title: __('People_id'), operate: false},
                        {field: 'admin_id_text', title: __('Admin_id'), operate: false},
                        {field: 'createtime', title: __('Createtime'),formatter: Table.api.formatter.datetime, operate: false},
                        {field: 'updatetime', title: __('Updatetime'),formatter: Table.api.formatter.datetime, operate: false},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
        },index2: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'property_log/index2' + location.search,
                    table: 'property_log',
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
                        {field: 'name', title: __('公用物资名称'), operate: false},
                        {field: 'specification', title: __('Specification'), operate: false},
                        {field: 'version', title: __('Version'), operate: false},
                        {field: 'category_id_text', title: __('Category_id'), operate: false},
                        {field: 'amount', title: __('Amount'), operate: false},
                        {field: 'value', title: __('Value'), operate: false},
                        {field: 'time1', title: __('发放时间'),formatter: Table.api.formatter.datetime, operate: false},
                        {field: 'unit1_id_text', title: __('Unit1_id'), operate: false},
                        {field: 'unit_id_text', title: __('Unit_id'), operate: false},
                        {field: 'people_id_text', title: __('People_id'), operate: false},
                        {field: 'admin_id_text', title: __('Admin_id'), operate: false},
                        {field: 'createtime', title: __('Createtime'),formatter: Table.api.formatter.datetime, operate: false},
                        {field: 'updatetime', title: __('Updatetime'),formatter: Table.api.formatter.datetime, operate: false},
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
        },
        add: function () {
            
            $(document).on("change", "#c-property_id", function(){
                $.get('property/huoq',{ids:$(this).val()},function(res){
                    $('#c-name').val(res.name);
                    $('#c-specification').val(res.specification);
                    $('#c-surplus').val(res.amount);
                    $('#c-version').val(res.version);
                });
            });
            $("#c-property_id").data("params", function (obj) {
                return {custom: {type: $("#c-type").val(),unit_id:Config.admin.unit_id}};
            });
            Controller.api.bindevent();
        },
        add1: function () {
            $(document).on("change", "#c-property_id", function(){
                $.get('property/huoq',{ids:$(this).val()},function(res){
                    $('#c-name').val(res.name);
                    $('#c-specification').val(res.specification);
                    $('#c-surplus').val(res.amount);
                    $('#c-version').val(res.version);
                });
            });
            $("#c-people_id").data("params", function (obj) {
                return {custom: {unit_id: $("#c-unit_id").val(),}};
            });
            $(document).on('change',"#c-people_id",function(){
                $("input[id=c-people_id]").val($(this).val());
                $("input[id=c-people_id]").selectPageRefresh();
            });
            $(document).on("change", "#c-fafang_type", function(){
                var t = $(this).val()==1?0:1;
                $('.J_type'+$(this).val()).show();
                $('.J_type'+t).hide();
            });
            
            var t = $("#c-fafang_type").val()==1?0:1;
                $('.J_type'+$("#c-fafang_type").val()).show();
                $('.J_type'+t).hide();
            Controller.api.bindevent();
        },
        edit: function () {
            $(document).on("change", "#c-property_id", function(){
                $.get('property/huoq',{ids:$(this).val()},function(res){
                    $('#c-name').val(res.name);
                    $('#c-specification').val(res.specification);
                    $('#c-surplus').val(res.amount);
                    $('#c-version').val(res.version);
                });
            });
            $("#c-property_id").data("params", function (obj) {
                return {custom: {type: $("#c-type").val(),unit_id:Config.admin.unit_id}};
            });
            Controller.api.bindevent();
        },
        edit1: function () {
            $(document).on("change", "#c-property_id", function(){
                $.get('property/huoq',{ids:$(this).val()},function(res){
                    $('#c-name').val(res.name);
                    $('#c-specification').val(res.specification);
                    $('#c-surplus').val(res.amount);
                    $('#c-version').val(res.version);
                });
            });
            $(document).on('change',"#c-people_id",function(){
                $("input[id=c-people_id]").val($(this).val());
                $("input[id=c-people_id]").selectPageRefresh();
            });
            $(document).on("change", "#c-fafang_type", function(){
                var t = $(this).val()==1?0:1;
                
                $('.J_type'+$(this).val()).show();
                $('.J_type'+t).hide();
            });
            var t = $("#c-fafang_type").val()==1?0:1;
                $('.J_type'+$("#c-fafang_type").val()).show();
                $('.J_type'+t).hide();
                setTimeout(function() {
                    $("#c-people_id").selectPageRefresh();
                }, 1000);
                // $("#c-people_id").selectPageRefresh();
            Controller.api.bindevent();
            
            
        },
        
        
        yj_index: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'property_log/yj_index' + location.search,
                    add_url: 'property_log/yj_add',
                    edit_url: 'property_log/yj_edit',
                    del_url: 'property_log/del',
                    multi_url: 'property_log/multi',
                    import_url: 'property_log/import',
                    table: 'property_log',
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
                        {field: 'name', title: __('公用物资名称'), operate: 'LIKE'},
                        {field: 'specification', title: __('Specification'),operate:false},
                        {field: 'version', title: __('Version'),operate:false},
                        {field: 'value', title: __('Value'),operate:false},
                        {field: 'time2', title: __('Time2'),operate:false,formatter: Table.api.formatter.datetime},
                        {field: 'amount', title: __('Amount'),operate:false},
                        // {field: 'surplus', title: __('现有库存'),operate:false},
                        
                        {field: 'admin_id_text', title: __('Admin_id'),operate:false},
                        {field: 'time1', title: __('入库时间'),operate:false,formatter: Table.api.formatter.datetime},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });
    
            // 为表格绑定事件
            Table.api.bindevent(table);
        },yj_index1: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'property_log/yj_index1' + location.search,
                    add_url: 'property_log/yj_add1',
                    edit_url: 'property_log/yj_edit1',
                    del_url: 'property_log/del',
                    multi_url: 'property_log/multi',
                    import_url: 'property_log/import',
                    table: 'property_log',
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
                        {field: 'name', title: __('公用物资名称'), operate: false},
                        {field: 'specification', title: __('Specification'), operate: false},
                        {field: 'version', title: __('Version'), operate: false},
                        {field: 'category_id_text', title: __('Category_id'), operate: false},
                        {field: 'amount', title: __('Amount'), operate: false},
                        {field: 'value', title: __('Value'), operate: false},
                        {field: 'time1', title: __('发放时间'),formatter: Table.api.formatter.datetime, operate: false},
                        {field: 'unit1_id_text', title: __('Unit1_id'), operate: false},
                        {field: 'unit_id_text', title: __('Unit_id'), operate: false},
                        {field: 'people_id_text', title: __('People_id'), operate: false},
                        {field: 'admin_id_text', title: __('Admin_id'), operate: false},
                        {field: 'createtime', title: __('Createtime'),formatter: Table.api.formatter.datetime, operate: false},
                        {field: 'updatetime', title: __('Updatetime'),formatter: Table.api.formatter.datetime, operate: false},
                        {field: 'operate', title: __('Operate'), table: table, events: Table.api.events.operate, formatter: Table.api.formatter.operate}
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
        },yj_index2: function () {
            // 初始化表格参数配置
            Table.api.init({
                extend: {
                    index_url: 'property_log/yj_index2' + location.search,
                    table: 'property_log',
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
                        {field: 'name', title: __('公用物资名称'), operate: false},
                        {field: 'specification', title: __('Specification'), operate: false},
                        {field: 'version', title: __('Version'), operate: false},
                        {field: 'category_id_text', title: __('Category_id'), operate: false},
                        {field: 'amount', title: __('Amount'), operate: false},
                        {field: 'value', title: __('Value'), operate: false},
                        {field: 'time1', title: __('发放时间'),formatter: Table.api.formatter.datetime, operate: false},
                        {field: 'unit1_id_text', title: __('Unit1_id'), operate: false},
                        {field: 'unit_id_text', title: __('Unit_id'), operate: false},
                        {field: 'people_id_text', title: __('People_id'), operate: false},
                        {field: 'admin_id_text', title: __('Admin_id'), operate: false},
                        {field: 'createtime', title: __('Createtime'),formatter: Table.api.formatter.datetime, operate: false},
                        {field: 'updatetime', title: __('Updatetime'),formatter: Table.api.formatter.datetime, operate: false},
                    ]
                ]
            });

            // 为表格绑定事件
            Table.api.bindevent(table);
        },
        yj_add: function () {
            $(document).on("change", "#c-property_id", function(){
                $.get('property/huoq',{ids:$(this).val()},function(res){
                    $('#c-name').val(res.name);
                    $('#c-specification').val(res.specification);
                    $('#c-surplus').val(res.amount);
                    $('#c-version').val(res.version);
                });
            });
            Controller.api.bindevent();
        },
        yj_add1: function () {
            $(document).on("change", "#c-property_id", function(){
                $.get('property/huoq',{ids:$(this).val()},function(res){
                    $('#c-name').val(res.name);
                    $('#c-specification').val(res.specification);
                    $('#c-surplus').val(res.amount);
                    $('#c-version').val(res.version);
                });
            });
            $("#c-people_id").data("params", function (obj) {
                return {custom: {unit_id: $("#c-unit_id").val()}};
            });
            Controller.api.bindevent();
        },
        yj_edit: function () {
            $(document).on("change", "#c-property_id", function(){
                $.get('property/huoq',{ids:$(this).val()},function(res){
                    $('#c-name').val(res.name);
                    $('#c-specification').val(res.specification);
                    $('#c-surplus').val(res.amount);
                    $('#c-version').val(res.version);
                });
            });
            Controller.api.bindevent();
        },
        yj_edit1: function () {
            $(document).on("change", "#c-property_id", function(){
                $.get('property/huoq',{ids:$(this).val()},function(res){
                    $('#c-name').val(res.name);
                    $('#c-specification').val(res.specification);
                    $('#c-surplus').val(res.amount);
                    $('#c-version').val(res.version);
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