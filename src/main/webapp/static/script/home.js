$(function(){
    //页面间的跳转
    $("#about-us").click(function(){
        window.location.href="../../../pc/templates/about-us.html";
    })
    $("#recommend").click(function(){
        window.location.href="../../../pc/templates/goods-recommend.html";
    })
    $("#goods-center").click(function(){
        window.location.href="../../../pc/templates/goods-center.html";
    })
    $(".unlogin").click(function(){
        $(".login-window").show();
        $(".unlogin").hide();
    })
    $(".login-window .cancel").click(function(){
        $(".login-window").hide();
        $(".unlogin").show();
    })
    $("#goLogin").click(function(){
        $(".register-window").show();
        $(".login-window").hide();
    })
    /*$(".login-window .login").click(function(){
        $(".login-window").hide();
        $(".unlogin").hide();
        $(".islogin").show();
    })*/
    $(".register-window .cancel").click(function(){
        $(".register-window").hide();
        $(".unlogin").show();
    })
    /*$(".register-window .register").click(function(){
        $(".register-window").hide();
        $(".login-window").show();
    })*/
    $(".content-2 .btn-1").click(function(){
        $(".content-2 .btn-1").css("border-bottom","5px solid #0273e1");
        $(".content-2 .btn-1").css("color","#0273e1");
        $(".content-2 .btn-2").css("border-bottom","none");
        $(".content-2 .btn-2").css("color","#000");
        $(".content-1 .input-0").val("");
        $(".content-1 .input-1").val("");
        $(".content-1 .input-2").val("");
        $(".content-1 .input-3").val("");
    })
    $(".content-2 .btn-2").click(function(){
        $(".content-2 .btn-2").css("border-bottom","5px solid #0273e1");
        $(".content-2 .btn-2").css("color","#0273e1");
        $(".content-2 .btn-1").css("border-bottom","none");
        $(".content-2 .btn-1").css("color","#000");
    })
    $(".sec-2 img").click(function(){
        $(".sec-2 img").hide();
        $(".sec-2 div").show();
    })

    $.ajax({
        method: "get",
        url: "/product/list?type=1&rows=20&page=1",
        success: function (data) {
            var $frame = $(document.createDocumentFragment());
            for (var i = 0; i < data.result.productList.length; i++) {
                var $divGoodsList1 = $('<div class="goods-list-1" data-psid="'+data.result.productList[i].psid+'" onclick="goGoodsDetail(this);"></div> ');
                var $img = $('<img src="http://image.comeon365.com/' + data.result.productList[i].imgurl + '"/>');
                var $pP1 = $('<p class="p1">' + data.result.productList[i].productname + '</p>');
                var $pP2 = $('<p class="p2">库存:&nbsp;&nbsp;'+data.result.productList[i].stock+'</p>');
                var $pP3 = $('<p class="p3">¥' + data.result.productList[i].price + '</p>');
                $divGoodsList1.append($img).append($pP1).append($pP2).append($pP3);
                $frame.append($divGoodsList1);
            }
            $(".goods-list").append($frame.find(".goods-list-1"));
        }
    });


})

function goGoodsDetail(div){
    console.log(div);
    var psid=$(div)[0].dataset.psid;
    window.open("../../../pc/templates/goods-detail.html?psid="+psid,psid);
}
