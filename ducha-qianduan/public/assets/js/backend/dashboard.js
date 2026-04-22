define(['jquery', 'bootstrap', 'backend', 'addtabs', 'table', 'echarts', 'echarts-theme', 'template'], function ($, undefined, Backend, Datatable, Table, Echarts, undefined, Template) {

    var Controller = {
        index: function () {
            $(document).on("click", ".btn-refresh", function () {
                setTimeout(function () {
                    myChart.resize();
                }, 0);
            });
            $(document).on("click", ".J_nav", function () {
                var obj = top.window.$("ul.nav-addtabs li");
                obj.find(".fa-remove").trigger("click");
                top.window.$('body').removeClass('sidebar-collapse');
                showmenu($(this).data('py'),$(this).data('url'));
            });
            // showmenu('');
            function showmenu(str,url){
                var obj = top.window.$(".sidebar-menu > li");
                $.each(obj,function(){
                    if($(this).find('a').attr('py') == str || $(this).find('a').attr('py') == 'gzt'){
                        if($(this).hasClass('treeview')){
                            $(this).addClass('treeview-open');
                            $(this).find('.treeview-menu').addClass('menu-open');
                            $(this).find('.treeview-menu').show();
                        }
                        $(this).show();
                        if($(this).hasClass('treeview')){
                            var o = $(this).find('.treeview-menu > li:eq(0)');
                            url = url||o.find('a').attr('href');
                            var title = o.text();
                            var icon = o.find('a > i').attr('class');
                            Backend.api.addtabs(url, title, icon);
                        }else{
                            url = $(this).find('a').attr('href');
                            var title = $(this).text();
                            var icon = $(this).find('a > i').attr('class');
                            Backend.api.addtabs(url, title, icon);
                        }
                        
                    }else{
                        $(this).hide();
                    }
                });
            }
        }

    };

    return Controller;
});
