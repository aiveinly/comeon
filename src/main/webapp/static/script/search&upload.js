$(function() {
    //首页屏幕滚动效果
    $(".slide").KinSlideshow({
        moveStyle:"left",
        intervalTime:3,
        mouseEvent:"mouseover"
    });

    //搜索功能
    $("#search").click(function() {
        var search=$(".search").val();
        if(search==""){
            $("#search").attr("disabled","disabled");
        }else{
            $(".goods-list").empty();
            console.log(search);
            $.ajax({
                method: "post",
                url: "/product/list?type=1&rows=20&page=1&keywords="+search,
                success: function (data) {
                    console.log(data);
                    var $frame = $(document.createDocumentFragment());
                    for (var i = 0; i < data.result.productList.length; i++) {
                        if(data.result.productList[i].productname.indexOf(search)==-1){
                            alert("搜索内容不存在!");
                        }else{
                            var $divGoodsList1 = $('<div class="goods-list-1" data-psid="' + data.result.productList[i].psid + '" onclick="goGoodsDetail(this);"></div> ');
                            var $img = $('<img src="http://image.comeon365.com/' + data.result.productList[i].imgurl + '"/>');
                            var $pP1 = $('<p class="p1">' + data.result.productList[i].productname + '</p>');
                            var $pP2 = $('<p class="p2">库存:&nbsp;&nbsp;' + data.result.productList[i].stock + '</p>');
                            var $pP3 = $('<p class="p3">¥' + data.result.productList[i].price + '</p>');
                            $divGoodsList1.append($img).append($pP1).append($pP2).append($pP3);
                            $frame.append($divGoodsList1);
                        }
                    }
                    $(".goods-list").append($frame.find(".goods-list-1"));
                }
            });
        }
    })

    //上传文件
    $("#commit").click(function(){
        alert(1);
        var formData = new FormData();
        formData.append('file', $('#file')[0].files[0]);
        $.ajax({
            url: '/req/req-file-upload',
            type: 'POST',
            cache: false,
            data: formData,
            processData: false,
            contentType: false
        }).done(function(res) {
            console.log(res.message);
            if(res.message!="ok"){
                alert("文件上传失败!");
                return;
            }else{
                var productname = $(".content-1 .input-0").val();
                var brandname = $(".content-1 .input-1").val();
                var qty = $(".content-1 .input-2").val();
                var modelname = $(".content-1 .input-3").val();
                $.ajax({
                    method:"post",
                    url:"/req/product-req",
                    contentType:"application/json",
                    dataType: "json", //表示返回值类型，不必须
                    data:JSON.stringify({"uid":localStorage.getItem("uid"),"productname":productname,"brandname":brandname,"qty":qty,"modelname":modelname,"fileid":res.result}),
                    success:function(jsonResult){
                        console.log(jsonResult);
                        alert("文件上传成功!");
                    }
                })
            }

        }).fail(function(res) {

        });
    })
})