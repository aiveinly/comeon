//鼠标滚动标题行颜色变化
var scrollFunc = function (e) {
    var direct = 0;
    e = e || window.event;
    if (e.wheelDelta) {  //判断浏览器IE，谷歌滑轮事件
        if (e.wheelDelta > 0) { //当滑轮向上滚动时
            var top = $(this).scrollTop(); // 当前窗口的滚动距离
            $(".bar-header").css("background","linear-gradient(-180deg, rgba(38,37,44,0.85) 0%, rgba(25,24,29,0.00) 100%)");
            $("#sec-2").css("position","");
            $("#sec-2").removeClass("top");
        }
        if (e.wheelDelta < 0) { //当滑轮向下滚动时
            $(window).bind("scroll", function(){
                var top = $(this).scrollTop(); // 当前窗口的滚动距离
                if(top<490){//当滚动距离小于490px
                    $(".bar-header").css("background-color","#013281");
                    $(".bar-header").css("opacity","0.69");
                }
                if(top>490){//当滚动距离大于等于490px
                    $(".bar-header").css("background-color","#013281");
                    $(".bar-header").css("opacity","1");
                    $("#sec-2").css("position","fixed");
                    $("#sec-2").css("top","110px");
                }
            });
        }
    } else if (e.detail) {  //Firefox滑轮事件
        if (e.detail> 0) { //当滑轮向上滚动时
            var top = $(this).scrollTop(); // 当前窗口的滚动距离
            $(".bar-header").css("background","linear-gradient(-180deg, rgba(38,37,44,0.85) 0%, rgba(25,24,29,0.00) 100%)");
            $("#sec-2").css("position","");
            $("#sec-2").removeClass("top");
        }
        if (e.detail< 0) { //当滑轮向下滚动时
            var top = $(this).scrollTop(); // 当前窗口的滚动距离
            if(top<490){//当滚动距离小于490px
                $(".bar-header").css("background-color","#013281");
                $(".bar-header").css("opacity","0.69");
            }
            if(top>490){//当滚动距离大于490px
                $(".bar-header").css("background-color","#013281");
                $(".bar-header").css("opacity","1");
                $("#sec-2").css("position","fixed");
                $("#sec-2").css("top","110px");
            }
        }
    }
        /*ScrollText(direct);*/
}
//给页面绑定滑轮滚动事件
if (document.addEventListener) {
    document.addEventListener('DOMMouseScroll', scrollFunc, false);
}
//滚动滑轮触发scrollFunc方法
window.onmousewheel = document.onmousewheel = scrollFunc;