/*
- Created by cyj
- 2018/7/6
*/

//导航搜索框
$(function(){
    $('#searchAll').on('keyup', function () {
        var searchVal = $(this).val();
        searchVal.trim() == '' ? $('#searchIcon').show() : $('#searchIcon').hide();
    });

    //按回车搜索
    $("#searchAll").keyup(function (e) {
        if (e.keyCode == 13) {
            search();
        }
    });

    //（粘贴时）点击搜索按钮搜索
    $("#searchIcon").click(function () {
        search();
    });

    function search(){
        var searchVal = $("#searchAll").val();
        if($.trim(searchVal)==""){
            window.reload();
        }
        window.location = "/content/search/"+searchVal;
    }
});



//页脚
function loadFooter(){
    $.ajax({
        url:'/config',
        dataType:'JSON',
        async:true,
        type:'GET',
        success:function(result){
            result = result.data;
            var divHtml = '<div>'+
                    '<div class="footer">'+
                        '<div class="footer_mask">'+
                            '<div class="wrapper">'+
                                '<div class="footer_title">'+
                                    '<h4>联系我们</h4>'+
                                    '<div class="clear"></div>'+
                                '</div>'+
                                '<div class="footer_content">'+
                                    '<div class="contact">'+
                                        '<h5>联系电话：<span>'+result.tel+'</span></h5><br>'+
                                        '<h5>电子邮箱：<span>'+result.email+'</span></h5>'+
                                    '</div>'+
                                    '<div class="address">'+
                                        '<h5>地址：<span>'+result.address+'</span></h5><br>'+
                                        '<h5>邮编：<span>'+result.post+'</span></h5>'+
                                    '</div>'+
                                    '<div class="link">'+
                                        '<h5>传真：<span>'+result.fax+'</span></h5><br>'+
                                        '<h5>网址：<span>'+result.netAddress+'</span></h5>'+
                                    '</div>'+
                                    '<div class="weibo">'+
                                        '<h5><a href="'+result.weibo+'" target="view_window"><img src="/static/images/sina_wb.png" title="微博" height="30px" style="vertical-align: text-bottom;"></a></h5>'+
                                        '<h5><a href="'+result.weibo+'" target="view_window"><img src="/static/images/weixin.png" title="微信" height="30px" style="vertical-align: text-top;"> </a> </h5>'+
                                    '</div>'+
                                    '<div class="clear"></div>'+
                                '</div>'+
                                '<div class="clear"></div>'+
                                '<div class="copyright">'+
                                    '© 2018 西安冠铭科技股份有限公司 版权所有<br>Copyright © 2012-2018 www.gmuni.cn All Rights Reserved'+
                                '</div>'+
                            '</div>'+
                        '</div>'+
                    '</div>'+
                '</div>';
            $("#footer").append(divHtml);
        }
    })
}

//页脚位置调整
function footerPosition(){
    var winHeight = window.screen.height;
    var contentHeight = $('.listContent').height();
    var marginTop = 0;
    if(winHeight > 100+contentHeight){//菜单高度100
        marginTop = winHeight - (100 + contentHeight)-207;//页脚高度207px
        if(marginTop<50){
            marginTop = 50;
        }
    }else{
        marginTop = 50;
    }
    $('#footer').css('margin-top', marginTop);
}
