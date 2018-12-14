/* JS Document
- Created by cyj
- 2018/7/30
*/

//下拉条滚动事件
function navChange(){
    //设置窗口大小
    var windowHeight = $(window).height();
    $(".bgDiv,.divContainer").css("height",windowHeight);

    $(".divContainer").scroll(function(){
        scrollTop = $(this).scrollTop();
        if(scrollTop > 100){
            //$("#header").addClass("menuBg");
            $(".navbar-inverse").css({"background":"rgba(255,255,255,.95)","transition":"opacity 0.5s,background-color 0.9s,transform 0.5s"});
            $(".navbar-nav>li>a").css("color","#444d50");
            $(".navbar-brand img").attr("src","/static/images/logo.png");
            $(".nav-phone img").attr("src","/static/images/icon_phone_change.png");
            $(".nav-phone span").css("color","#444d50");
        }else{
            //$("#header").removeClass("menuBg");
            $(".navbar-inverse").css("background","transparent");
            $(".navbar-nav>li>a").css("color","#fff");
            $(".navbar-brand img").attr("src","/static/images/transparent_logo.png");
            $(".nav-phone img").attr("src","/static/images/icon_phone.png");
            $(".nav-phone span").css("color","rgb(247,241,230)");
        }
    });
}