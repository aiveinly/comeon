///<reference path="jquery.d.ts" />
var AppConfig = (function () {
    function AppConfig() {
        // this.baseUrl = 'http://www.comeon365.com/';
        this.baseUrl = 'http://localhost:8080/';
        this.imgBaseUrl = 'http://image.comeon365.com/';
    }

    AppConfig.getInstance = function () {
        if (!this._self) {
            this._self = new AppConfig();
        }
        return this._self;
    };
    return AppConfig;
}());
var Api = (function () {
    function Api() {
    }

    Api.get = function (interfaces) {
        var defer = $.Deferred();
        $.get(this.appConfig.baseUrl + interfaces).done(function (resp) {
            if (resp && resp.code == '200') {
                defer.resolve(resp.result);
            }
            else {
                defer.reject(resp);
            }
        }).fail(function (err) {
            return defer.reject(err);
        });
        return defer;
    };
    Api.post = function (interfaces, param) {
        var defer = $.Deferred();
        var _self = this;
        var url = _self.appConfig.baseUrl + interfaces;
        $.ajax({
            url: url,
            method: 'POST',
            data: JSON.stringify(param),
            contentType: 'application/json'
        }).then(function (resp) {
            if (resp && resp.code == '200') {
                defer.resolve(resp.result);
            }
            else {
                defer.reject(resp);
            }
        }, defer.reject);
        return defer;
    };
    Api.getInstance = function () {
        if (!this._self) {
            this._self = new Api();
        }
        return this._self;
    };
    Api.imgUrlAddPrefix = function (imgUrl) {
        return new AppConfig().imgBaseUrl + imgUrl;
    };
    Api.timeFormat = function (time, fmtStr) {
        var date = new Date(time);
        var o = {
            "M+": date.getMonth() + 1,
            "d+": date.getDate(),
            "h+": date.getHours(),
            "m+": date.getMinutes(),
            "s+": date.getSeconds(),
            "q+": Math.floor((date.getMonth() + 3) / 3),
            "S": date.getMilliseconds() //毫秒
        };
        if (new RegExp("(y+)").test(fmtStr))
            fmtStr = fmtStr.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmtStr)) {
                fmtStr = fmtStr.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        return fmtStr;
    };
    Api.moneyFormat = function (money, digits, symbol) {
        money = isNaN(Number(money)) ? 0 : Number(money);
        digits = digits || 0;
        symbol = symbol || '';
        return symbol + money.toFixed(digits);
    };
    Api.orderStatusFormat = function (value) {
        switch (value) {
            case -1:
                return '订单已取消';
            case 0:
                return '订单已提交';
            case 1:
                return '订单受理中';
            case 2:
                return '报价单已生成';
            case 3:
                return '报价单已确认';
            case 5:
                return '报价已完成';
        }
    };
    Api.quoteStatusFormat = function (value) {
        switch (value) {
            case -1:
                return '报价单已作废';
            case 0:
                return '报价单已生成';
            case 1:
                return '报价单已确认';
            case 3:
                return '报价单已完成';
        }
    };
    Api.appConfig = AppConfig.getInstance();
    return Api;
}());
var Validator = (function () {
    function Validator() {
    }

    Validator.isNumber = function (s) {
        return !isNaN(Number(s));
    };
    Validator.isPhone = function (s) {
        return this.phoneRegExp.test(s);
    };
    Validator.checkPhone = function (s) {
        if (s.length == 0) {
            return {code: -1, msg: '手机号码为空'};
        }
        if (!this.isPhone(s)) {
            return {code: -1, msg: '手机号码格式不正确'};
        }
        return {code: 0, msg: 'ok'};
    };
    Validator.checkPwd = function (s) {
        if (s.length < 6) {
            return {code: -1, msg: '密码为小于6位的字母或数字'};
        }
        return {code: 0, msg: 'ok'};
    };
    Validator.phoneRegExp = /^1[34578]\d{9}$/;
    Validator.pwdRegExp = /^(?!\d+$)(?![^a-zA-Z]){6,18}$/;
    return Validator;
}());

var Advertising=(function(){
    function Advertising(){}

    Advertising.getInstance = function () {
        if (!this._self) {
            this._self = new Advertising();
        }
        return this._self;
    };

    Advertising.renderHtml=function(data){
        var $dom = this.$gn_parentNode;
        var $tmp = this.$gn_template;
        var $index = this.$index;
        $.each(data, function (index, item) {
            $tmp.find('.ad-img'+index).attr('src', Api.imgUrlAddPrefix(item.imgurl));
            $dom.append($tmp);
        });
    }

    Advertising.$gn_parentNode = $('#gn-advertis');
    Advertising.$gn_template = $('#gn-advertis').find('.gn-advertis-img');
    Advertising.$index=1;

    Advertising.loadData=function (){
        return Api.get('/advertising/list');
    }

    Advertising.init = function () {
        Advertising.loadData().done(function (resp) {
            var data = resp.list;
            Advertising.renderHtml(data);
        });
    };

    return Advertising;
}());

var Common = (function () {
    function Common() {
    }

    Common.hashChangeHandler = function () {
        var _self = this;
        var _hashChange = window.onhashchange || function () {
            };
        window.onhashchange = function () {
            _hashChange.call(this, arguments);
            var newHash = window.location.hash.split('#')[1];
            if (_self._currentPage && _self._currentPage.name != newHash) {
                _self._currentPage.$dom.hide();
            }
            _self._currentPage = {name: newHash, $dom: $('#' + newHash)};
            _self._currentPage.$dom.show(0, null, function () {
                $(window).trigger('viewEnter', newHash);
            });
        };
    };
    Common.gnNavBarClick = function () {
        var _self = this;
        $('#gn-nav-bar').on('click', 'a', function (e) {
            var $target = $(e.target);
            var id = $target.attr('id');
            window.location.hash = $target.attr('href');
            if (_self._activeNav && _self._activeNav.name != id) {
                _self._activeNav.$dom.removeClass('active');
            }
            if (id == 'sale' || id == 'recommend') {
                $('#search-groups').show();
            }
            else {
                $('#search-groups').hide();
            }
            _self._activeNav = {name: id, $dom: $target};
            $target.addClass('active');
        });
    };
    Common.windowScrollHandler = function () {
        var $header = $('#gn-header');
        var headerH = $header.height();
        var gH = $('#gn-gallery').height();
        var posH = gH - headerH;
        var initOpc = .6;
        var $gnService = $('#gn-service');
        window.onscroll = function () {
            var $gnNavBar = $('#gn-nav-bar');
            // $gnFooter = $('#gn-footer');
            var sT = $('body').scrollTop();
            var opc = sT / posH;
            $header.css('opacity', Math.min(Math.max(opc, initOpc), 1));
            if (sT > 0) {
                $header.addClass('scrolled');
            }
            else {
                $header.removeClass('scrolled');
                $header.css('opacity', 1);
            }
            if (sT > posH) {
                $gnNavBar.addClass('fix');
                // $gnFooter.addClass('fix').fadeIn();
                $gnService.show().fadeIn();
            }
            else {
                $gnNavBar.removeClass('fix');
                // $gnFooter.removeClass('fix').fadeOut();
                $gnService.fadeOut();
            }
        };
    };
    Common.searchBtnClick = function () {
        var $searchIcon = $('#search-icon'), $searchBox = $('#searchBox'), $searchBtn = $('#searchBtn'), $clearSearchBtn = $('#clearSearchBtn');
        $searchIcon.click(function () {
            $searchBox.addClass('active');
            $searchIcon.hide();
            $searchBtn.show();
        });
        $searchBtn.click(function () {
        });
        $clearSearchBtn.click(function () {
            $searchIcon.show();
        });
    };
    Common.gallerySlide = function () {
        var $gallery = $('#gn-gallery'), $imgList = $gallery.find('ul'), childNum = $imgList.children().length, maxWidth = $imgList.width();
        $gallery.on('click', '.slide-page span', function (e) {
            var $target = $(e.target);
            var index = $('.slide-page span').index($target);
            currentIndex = index;
            changePage(index);
            move(index);
        });
        $(window).resize(function () {
            //update width
            childNum = $imgList.children().length;
            maxWidth = $imgList.width();
        });
        function move(pageIndex) {
            $imgList.animate({"left": -(maxWidth / childNum) * pageIndex}, 200);
        }

        function changePage(index) {
            var $pages = $gallery.find('.slide-page span');
            $pages.removeClass('active');
            $($pages[index]).addClass('active');
        }

        var currentIndex = 0;

        function slideEvt() {
            return setInterval(function () {
                if (currentIndex == childNum) {
                    currentIndex = 0;
                }
                changePage(currentIndex);
                move(currentIndex);
                currentIndex++;
            }, 2000);
        }

        var interval = slideEvt();
        $imgList.hover(function () {
            clearInterval(interval);
        }, function () {
            interval = slideEvt();
        });
    };
    Common.registerEvents = function () {
        this.hashChangeHandler();
        this.gnNavBarClick();
        this.windowScrollHandler();
        this.searchBtnClick();
        this.gallerySlide();
    };
    return Common;
}());
var ProductType;
(function (ProductType) {
    ProductType[ProductType["sale"] = 1] = "sale";
    ProductType[ProductType["recommend"] = 2] = "recommend";
})(ProductType || (ProductType = {}));
var DictType;
(function (DictType) {
    DictType[DictType["sort"] = 1] = "sort";
    DictType[DictType["brand"] = 2] = "brand";
    DictType[DictType["series"] = 3] = "series";
})(DictType || (DictType = {}));
var ProductList = (function () {
    function ProductList() {
    }

    ProductList.getInstance = function () {
        if (!this._self) {
            this._self = new ProductList();
        }
        return this._self;
    };
    ProductList.renderHtml = function (data) {
        var $dom = this.$parentNode, $tmp = this.$template;
        $.each(data, function (index, item) {
            $tmp = $tmp.clone().removeClass('template');
            $tmp.attr('data-psid', item.psid);
            $tmp.find('.js-pImg').attr('src', Api.imgUrlAddPrefix(item.imgurl));
            $tmp.find('.js-pName').html(item.productname);
            $tmp.find('.js-pStore').html(item.stock);
            if (item.ptype == ProductType.recommend) {
                //推荐不显示库存
                $tmp.find('.js-pStore').closest('p').remove();
            }
            $tmp.find('.js-pPrice').html(Api.moneyFormat(item.price, 2));
            $dom.append($tmp);
        });
    };
    ProductList.reRenderHtml = function (data) {
        this.clearViewData();
        this.renderHtml(data);
    };
    ProductList.renderPage = function () {
        var $pageBar = $('#pageBar');
        var $page = $($pageBar.find('.page')[0]).removeClass('active');
        var $next = $pageBar.find('.page-next');
        var totalPage = this._totalPage;
        $pageBar.find('.js-total-page').html(totalPage + '');
        $('.page').remove();
        for (var i = 1; i <= this._totalPage; i++) {
            var p = $page.clone().html('' + i);
            if (this._currentPage == i) {
                p.addClass('active');
            }
            $next.before(p);
        }
        this.pageBarClickEventHandler();
    };
    ProductList.pageBarClickEventHandler = function () {
        var $pageBar = $('#pageBar');
        $pageBar.unbind('click'); //移除之前绑定的click事件
        function checkActivePage() {
            var $page = $('.page');
            $page.removeClass('active');
            $($page[ProductList._currentPage - 1]).addClass('active');
        }

        $pageBar.on('click', '.page-next', function () {
            if (ProductList._currentPage == ProductList._totalPage) {
                return;
            }
            var page = ProductList._currentPage + 1;
            var rows = ProductList._rows;
            var type = ProductList._type;
            ProductList.loadData(type, rows, page).done(function (resp) {
                ProductList.reRenderHtml(resp.productList);
                checkActivePage();
            });
        });
        $pageBar.on('click', '.page', function (e) {
            var page = Number($(e.target).html());
            var rows = ProductList._rows;
            var type = ProductList._type;
            ProductList.loadData(type, rows, page).done(function (resp) {
                ProductList.reRenderHtml(resp.productList);
                checkActivePage();
            });
        });
        $pageBar.on('click', '.page-prev', function () {
            if (ProductList._currentPage == 1) {
                return;
            }
            var page = ProductList._currentPage - 1;
            var rows = ProductList._rows;
            var type = ProductList._type;
            ProductList.loadData(type, rows, page).done(function (resp) {
                ProductList.reRenderHtml(resp.productList);
                checkActivePage();
            });
        });
        $pageBar.on('click', '.js-confirm', function () {
            var index = $pageBar.find('.js-page-go').val();
            index = index < 1 ? 1 : index;
            index = index > ProductList._totalPage ? ProductList._totalPage : index;
            $pageBar.find('.page')[index - 1].click();
        });
    };
    ProductList.clearViewData = function () {
        var $tmp = this.$parentNode.find('.template').clone();
        this.$parentNode.find('.product-item')
            .remove();
        this.$parentNode.append($tmp);
    };
    ProductList.loadData = function (type, rows, page) {
        this._type = type; //cacheType
        this._currentPage = page;
        this._rows = rows;
        var query = '?type=' + type + '&rows=' + rows + '&page=' + page;
        return Api.get('/product/list' + query);
    };
    ProductList.searchProduct = function (keywords, rows, page) {
        var type = this._type;
        this._currentPage = page;
        var query = '?type=' + type + '&rows=' + rows + '&page=' + page + '&keywords=' + keywords;
        return Api.get('/product/list' + query);
    };
    ProductList.init = function (type, rows, page) {
        if (type === void 0) {
            type = 1;
        }
        if (rows === void 0) {
            rows = 20;
        }
        if (page === void 0) {
            page = 1;
        }
        ProductList.loadData(type, rows, page).done(function (resp) {
            var data = resp.productList;
            ProductList._totalPage = Math.ceil(Number(resp.count) / rows);
            ProductList.renderHtml(data);
            ProductList.renderPage();
        });
        this.bindClickHandler();
    };
    ProductList.bindClickHandler = function () {
        var _self = this;
        _self.$parentNode.bind('click', function (e) {
            var $target = $(e.target);
            var $item = $target.closest('.product-item');
            if ($item.length == 0) {
                return;
            }
            var psid = $item.attr('data-psid');
            ProductDetail.init(psid);
            $('.product-list-wrap').hide();
        });
    };
    ProductList.$parentNode = $('#product-list');
    ProductList.$template = $('#product-list').find('.product-item.template');
    ProductList._type = 1;
    ProductList._currentPage = 0;
    return ProductList;
}());

var ProductDetail = (function () {
    function ProductDetail() {
    }

    ProductDetail.loadData = function (psid) {
        var query = '?psid=' + psid;
        return Api.get('/product/detail' + query);
    };
    ProductDetail.renderHtml = function (data) {
        var $dom = this._parentNode;
        $dom.find('.js-hl-img').attr('src', Api.imgUrlAddPrefix(data.imgurl));
        $dom.find('.js-p-name').html(data.productname);
        $dom.find('.js-p-no').html(data.pid);
        $dom.find('.js-p-sort').html(data.sortname);
        $dom.find('.js-p-brand').html(data.brandname);
        $dom.find('.js-p-series').html(data.seriesname);
        $dom.find('.js-p-time').html(Api.timeFormat(data.saledate, 'yyyy-MM-dd'));
        $dom.find('.js-pf-img').attr('src', Api.imgUrlAddPrefix(data.imgurl2 || data.imgurl));
        var desc = data.describemodel || '暂无详细描述，您可以联系客服了解更详细的介绍！';
        $dom.find('.js-desc').html(desc);
        var price = data.ptype == 1 ? Api.moneyFormat(data.price, 2) : '详情请咨询客服';
        $dom.find('.js-p-price').html(price);
        $dom.show();
    };
    ProductDetail.init = function (psid) {
        window.location.hash = '#product-detail';
        var _self = this;
        this.loadData(psid).then(function (resp) {
            _self.renderHtml(resp[0]);
        });
    };
    ProductDetail._parentNode = $('#product-detail');
    return ProductDetail;
}());
var User = (function () {
    function User() {
    }

    User.login = function (phone, password) {
        var _self = this;
        var defer = $.Deferred();
        var query = '?username=' + phone + '&password=' + password;
        Api.get('/personal/login' + query).done(function (resp) {
            _self.userInfo = resp;
            //saveUser
            localStorage.setItem('userInfo', JSON.stringify(resp));
            defer.resolve(resp);
        }).fail(defer.reject);
        return defer;
    };
    User.getUserInfo = function (phone) {
        var query = '?username=' + phone;
        return Api.get('/personal/getUser' + query);
    };
    User.gainCode = function (phone) {
        var query = '?mobile=' + phone;
        return Api.get('/personal/identifycode' + query);
    };
    User.isLogin = function () {
        return Boolean(this.userInfo);
    };
    User.autoLogin = function () {
        var userInfo = JSON.parse(localStorage.getItem('userInfo'));
        if (!userInfo) {
            return;
        }
        this.login(userInfo.username, userInfo.password).then(function (resp) {
            $('#js-login').hide();
            $('#loginModal').modal('hide');
            //leo
            $('#userName').append('<span>' + resp.username + '</span>' + "<a href='javascript:;'>退出</a>").show();
        });
    };

    $('.username').click(function () {
        localStorage.removeItem('userInfo');
        $('#sale').click();
        $('#js-login').show();
        $('#userName').hide();
    });
    User.signIn = function (req) {
        var url = "/personal/register?uname=" + req.uname +
            "&pwd=" + req.pwd +
            "&name=" + req.name +
            "&referralcode=" + req.referralcode;
        return Api.get(url);
    };
    return User;
}());
var OrderService = (function () {
    function OrderService() {
    }

    OrderService.currentPage = 1;
    OrderService.totalPage = 1;
    OrderService.getOrderList = function (username, rows, page) {
        OrderService.currentPage = page;
        var url = '/personal/getOrderlist?username=' + username + '&rows=' + rows + '&page=' + page;
        return Api.get(url);
    };

    OrderService.getOrderDetail = function (oId) {
        var url = '/personal/getDetailOrder?oid=' + oId;
        return Api.get(url);
    };

    OrderService.renderOrderList = function (data) {
        OrderService.totalPage = Math.ceil(data.count / 5);
        $('.page').find('.js-page-index').html(OrderService.currentPage);
        var $wrap = $('#me-order-wrap').find('.m-o-list');
        var $tmp = $wrap.find('.m-o-item.template').clone();
        $wrap.find('.m-o-item').remove();//清理数据
        $wrap.append($tmp);
        $.each(data.orderList, function (i, item) {
            var $dom = $tmp.clone().removeClass('template');
            $dom.find('.js-order-no').html(item.oid);
            $dom.find('.js-o-time').html(Api.timeFormat(item.createdate, 'yyyy-MM-dd hh:mm:ss'));
            $dom.find('.js-o-status').html(Api.orderStatusFormat(item.status));
            $dom.bind('click', function () {
                if ($dom.hasClass('active')) {
                    $dom.removeClass('active');
                    return;
                }
                $wrap.find('.m-o-item').removeClass('active');
                $dom.addClass('active');
                $dom.find('.js-see-quote').addClass('disabled');
                OrderService.getOrderDetail(item.oid).done(function (resp) {
                    var req = resp.requirement.reqRequirement;
                    $dom.find('.js-o-r-name').html(req.productname);
                    $dom.find('.js-o-r-num').html(req.qty);
                    $dom.find('.js-o-r-brand').html(req.brandname);
                    $dom.find('.js-o-r-model').html(req.modelname);
                    if (resp.ordPreOrders.length > 0) {//已生成报价单
                        $dom.find('.js-see-quote').removeClass('disabled');
                        $dom.find('.js-see-quote').click(function () {
                            $(window).trigger('view.quoteInit', resp.ordOrder.qid);
                        });
                        var wrap = $dom.find('.mor-list');
                        var tmp = wrap.find('.mor-item.template').clone();
                        wrap.find('.mor-item').remove();//清理数据
                        wrap.append(tmp);
                        $.each(resp.ordPreOrders, function (i, ord) {
                            var dom = tmp.clone().removeClass('template');
                            dom.find('.js-mor-id').html(ord.poid);
                            dom.find('.js-mor-name').html(ord.productname);
                            dom.find('.js-mor-price').html('￥' + Api.moneyFormat(ord.price, 2));
                            dom.find('.js-mor-qty').html(ord.qty);
                            wrap.append(dom);
                        });
                    } else {
                        $dom.find('.js-see-quote').attr('disabled', 'disabled');
                    }
                });
            });
            $wrap.append($dom);
        });
    };
    return OrderService;
})();
var InvoiceService = (function () {
    function InvoiceService() {

    }

    InvoiceService.currentEditInvoice = null;

    InvoiceService.getInvoiceList = function (uid) {
        return Api.get('/personal/selectinvoice?uid=' + uid);
    };

    InvoiceService.clearEditData = function () {
        InvoiceService.currentEditInvoice = null;
        $('.js-titleName').val('');
        $('.js-bank').val('');
        $('.js-account').val('');
        $('.js-tax').val('');
        $('.js-companyTel').val('');
        $('.js-receiver').val('');
        $('.js-receiverTel').val('');
        $('.js-address').val('');
    };

    InvoiceService.renderInvoiceList = function (data) {
        var $wrap = $('#me-invoice-wrap').find('.m-i-list');
        var $tmp = $wrap.find('.m-i-item.template').clone();
        $wrap.find('.m-i-item').remove();//清理数据
        $wrap.append($tmp);
        $.each(data, function (i, item) {
            var $dom = $tmp.clone().removeClass('template');
            $dom.attr('data-id', item.iid);
            $dom.find('.js-m-i-name').html(item.titlename);
            /*绑定删除事件*/
            $dom.find('.js-i-del').bind('click', function () {
                InvoiceService.delInvoice(item.iid).done(function () {
                    $dom.remove();
                    Alert.show('删除开票信息成功', 'success');
                }).fail(function () {
                    Alert.show('删除开票信息失败', 'error');
                });
            });
            $dom.find('.js-i-edit').bind('click', function () {
                InvoiceService.currentEditInvoice = item;
                $('#me-invoice-wrap').find('.m-h-right').click();
                $('.js-titleName').val(item.titlename);
                $('.js-bank').val(item.bank);
                $('.js-account').val(item.account);
                $('.js-tax').val(item.tax);
                $('.js-companyTel').val(item.companytel);
                $('.js-receiver').val(item.receiver);
                $('.js-receiverTel').val(item.receivertel);
                $('.js-address').val(item.address);
            });
            $wrap.append($dom);
        });
    };

    InvoiceService.delInvoice = function (iid) {
        return Api.get('/personal/delinvoice?iid=' + iid);
    };
    return InvoiceService;
})();
var MessageService = (function () {
    function MessageService() {

    }

    MessageService.currentPage = 1;

    MessageService.lastMsgList = null;

    MessageService.getMessageList = function (uid, rows, page) {
        MessageService.currentPage = page;
        var url = '/personal/selectMessage?uid=' + uid + '&rows=' + rows + '&page=' + page;
        return Api.get(url);
    };

    MessageService.renderMessageList = function (data) {
        MessageService.lastMsgList = data;
        var $wrap = $('#me-message-wrap').find('.m-m-list');
        $('.js-m-page-index').html(MessageService.currentPage);
        var $tmp = $wrap.find('.m-m-item.template').clone();
        $wrap.find('.m-m-item').remove();//清理数据
        $wrap.append($tmp);
        $.each(data, function (i, item) {
            var $dom = $tmp.clone().removeClass('template');
            $dom.find('.js-m-m-type').html(data.mtype);
            $dom.find('.js-m-m-time').html(Api.timeFormat(item.senddate, 'yyyy-MM-dd hh:mm:ss'));
            $dom.find('.js-m-m-info').html(item.message);
            $wrap.append($dom);
        });
    };
    return MessageService;
})();
var Alert = (function () {
    function Alert() {
    }

    Alert.show = function (msg, type) {
        var _self = this;
        this.$dom.addClass('in').addClass(_self.msgType[type]);
        this.$dom.find('.js-msg').html(msg);
        setTimeout(function () {
            _self.hide();
        }, 1000);
        this.$dom.find('.close').click(function () {
            _self.hide();
        });
    };
    Alert.hide = function () {
        this.$dom.removeClass('in');
    };
    Alert.$dom = $('#alert');
    Alert.msgType = {
        success: 'alert-success',
        error: 'alert-danger',
        warning: 'alert-warning'
    };
    return Alert;
}());
$(function () {
    User.autoLogin();
    var identifyCode = '';
    var commitParam = null;

    function needLogin() {
        Alert.show('请先登录', 'warning');
        setTimeout(function () {
            $('#loginModal').modal('show');
        }, 1000);
    }

    $('#my-center').hide();
    //隐藏登陆框
    $('#loginModal').on('hidden.bs.modal', function () {
        $('#registerForm').addClass('hidden');
        $('#loginForm').removeClass('hidden');
    });
    $('#recommend').click(function () {
        $('#product-list').addClass('sale');
        ProductList.clearViewData();
        ProductList.init(2);
        Advertising.init();
    });
    $('#sale').click(function () {
        $('#product-list').removeClass('sale');
        ProductList.clearViewData();
        ProductList.init(1);
        Advertising.init();
    });
    $('#product-center').click(function () {
        $('#product-list').addClass('sale');
        ProductList.clearViewData();
        ProductList.init(1);
        Advertising.init();
    });
    $('#no-account').click(function () {
        $('#loginForm').addClass('hidden');
        $('#registerForm').removeClass('hidden');
        $('#loginModelTitle').html('注册坤玛机电');
    });
    $('#has-account').click(function () {
        $('#registerForm').addClass('hidden');
        $('#loginForm').removeClass('hidden');
        $('#loginModelTitle').html('登录坤玛机电');
    });
    $('#loginBtn').click(function () {
        var $loginForm = $('#loginForm');
        var phone = $loginForm.find('.js-phone').val();
        var password = $loginForm.find('.js-password').val();
        var err = Validator.checkPhone(phone);
        if (err.code) {
            return Alert.show(err.msg, 'error');
        }
        var errInfo = Validator.checkPwd(password);
        if (errInfo.code) {
            return Alert.show(errInfo.msg, 'error');
        }
        $('#loginModal').modal('hide');
        User.login(phone, password).done(function (resp) {
            Alert.show("登录成功", "success");
            $('#js-login').hide();
            $('#userName').html('<span>' + resp.username + '</span>' + "<a href='javascript:;'>退出</a>").show();
        }).fail(function (err) {
            Alert.show(err.message, "danger");
        });
    });

    $('#js-gain-code').click(function () {
        var phone = $('#registerForm').find('[name="phone"]').val();
        alert(phone);
        var err = Validator.checkPhone(phone);
        if (err.code) {
            return Alert.show(err.msg, 'error');
        }
        var timer = 60;
        User.gainCode(phone).done(function (data) {
            identifyCode = data;
            Alert.show("验证码已发送,请注意查收", "success");
            var interval = setInterval(function () {
                if (timer > 0) {
                    timer--;
                    var transmit = timer == 0 ? '获取验证码' : '重新发送' + timer + 's';
                    $('#js-gain-code').html(transmit).attr("disabled", "true");
                }
                if (timer == 0) {
                    clearInterval(interval);
                }
            }, 1000);
        }).fail(function () {
            Alert.show("验证码发送失败,请重新获取", "error");
            var transmit = "发送验证码";
            timer = 60;
            $('#js-gain-code').html(transmit).attr("disabled", "false");
        });
    });
    $('#registerBtn').click(function () {
        var $registerForm = $('#registerForm');

        function getVal($form, inputName) {
            return $form.find('[name="' + inputName + '"]').val();
        }

        var phone = getVal($registerForm, 'phone'), code = getVal($registerForm, 'code'), username = getVal($registerForm, 'username'), pwd = getVal($registerForm, 'pwd'), pwd2 = getVal($registerForm, 'pwd2'), inviteCode = getVal($registerForm, 'inviteCode');
        if (code.length == 0) {
            return Alert.show('请输入验证码', 'error');
        }
        var err = Validator.checkPhone(phone);
        if (err.code) {
            return Alert.show(err.msg, 'error');
        }
        if (username.length == 0) {
            return Alert.show('请输入你的姓名', 'error');
        }
        var errInfo = Validator.checkPwd(pwd);
        if (errInfo.code) {
            return Alert.show(errInfo.msg, 'error');
        }
        if (pwd != pwd2) {
            return Alert.show('两次密码不一致', 'error');
        }
        if (code != identifyCode) {
            return Alert.show('验证码错误', 'error');

        }
        $('#loginModal').modal('hide');
        var user = {
            uname: phone,
            pwd: pwd,
            name: username,
            referralcode: inviteCode
        };
        User.signIn(user).done(function (resp) {
            if (resp.result.code == 200) {
                Alert.show('注册成功', 'success');
                User.login(phone, pwd).done(function (resp) {
                    Alert.show("登录成功", "success");
                    $('#js-login').hide();
                    $('#userName').html('<span>' + resp.username + '</span>' + "<a href='javascript:;'>退出</a>").show();
                }).fail(function (err) {
                    Alert.show(err.message, "danger");
                });
            } else {
                Alert.show(resp.result.success, 'error');
            }
        }).fail(function () {
            Alert.show('注册失败', 'error');
        });
    });
    $('#commit-req').click(function () {
        if (!User.isLogin()) {
            return needLogin();
        }
        var $progressBar = $('progress'), $uploadBtn = $('#uploadBtn');
        if (commitParam) {//文件上传
            return Api.post('/req/product-req', commitParam).done(function (res) {
                $progressBar.hide();
                $uploadBtn.show();
                Alert.show('上传成功', 'success');
            }).always(function () {
                commitParam = null;
            });
        }
        var demand = {
            brandname: $('#productBrand').val(),
            productname: $('#productName').val(),
            qty: $('#productNum').val(),
            modelname: $('#productModel').val(),
            seriesname: $('#productSeries').val(),
            uid: User.userInfo.uid
        };
        //非必填项
        // if (demand.productname.length == 0) {
        //     return Alert.show('请输入产品名称', 'error');
        // }
        // if (demand.brandname.length == 0) {
        //     return Alert.show('请输入产品品牌', 'error');
        // }
        if (demand.modelname.length == 0) {
            return Alert.show('请输入产品型号', 'error');
        }
        if (demand.qty.length == 0) {
            return Alert.show('请输入需求数量', 'error');
        }
        if (!Validator.isNumber(demand.qty)) {
            return Alert.show('数量只能为数字', 'error');
        }
        return Api.post('/req/product-req', demand).done(function (resp) {
            Alert.show('需求提交成功', 'success');
            console.log(resp);
        });
    });
    $('#uploadBtn').click(function () {
        if (!User.isLogin()) {
            return needLogin();
        }
        $('#js-file').click();
    });
    $('#js-file').change(function () {
        if (!User.isLogin()) {
            var fileInput = $('#js-file')[0];
            fileInput.files = null;
            return needLogin();
        }
        var $progressBar = $('progress'), $uploadBtn = $('#uploadBtn'), formData = new FormData($('form')[0]);
        $.ajax({
            url: '/req/req-file-upload',
            type: 'POST',
            xhr: function () {
                var myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) {
                    myXhr.upload.addEventListener('progress', progressHandler, false); // for handling the progress of the upload
                }
                return myXhr;
            },
            //Ajax事件
            // beforeSend: beforeSendHandler,
            success: successHandler,
            error: errorHandler,
            // Form数据
            data: formData,
            //Options to tell JQuery not to process data or worry about content-type
            cache: false,
            contentType: false,
            processData: false
        });
        function progressHandler(e) {
            if (e.lengthComputable) {
                $uploadBtn.hide();
                $progressBar.attr({value: e.loaded, max: e.total});
                $progressBar.show();
            }
        }

        function errorHandler(e) {
            Alert.show(e.message, 'error');
        }

        function successHandler(resp) {
            commitParam = {
                uid: User.userInfo.uid,
                fileid: resp.result
            };
        }
    });
    $('#searchBtn').click(function () {
        var keywords = $('#searchBox').val();
        ProductList.searchProduct(keywords, 20, 1).done(function (resp) {
            ProductList._totalPage = Math.ceil(Number(resp.count) / 20);
            ProductList.reRenderHtml(resp.productList);
            ProductList.renderPage();
        });
    });
    $('#clear-form').click(function () {
        $('#productBrand').val('');
        $('#productName').val('');
        $('#productNum').val(1);
        $('#productSeries').val('');
        $('#productModel').val('');
    });

    $('#p-center').click(function () {//当点击产品中心时才开始加载数据
        getDictList(1).done(function (resp) {
            renderView(resp, DictType[1]);
        });
    });

    function getDictList(type, rows, page, pId) {
        rows = rows || 1;
        page = page || 20;
        var url = '/dict/dictlist?type=' + type + '&rows=' + rows + '&page=' + page;
        if (pId) {
            url += '&parentId=' + pId;
        }
        return Api.get(url);
    }

    function renderView(data, vType) {
        var $wrap = $('#p-' + vType + '-list');
        var $tmp = $wrap.find('.p-' + vType + '-item.template').clone();
        $wrap.find('.p-' + vType + '-item').remove();//清理数据
        $wrap.append($tmp);
        $.each(data, function (index, item) {
            var $dom = $tmp.clone().removeClass('template');
            $dom.attr('data-' + vType, JSON.stringify(item));
            $dom.find('img').attr('src', Api.imgUrlAddPrefix(item.imgurl));
            $dom.find('p').html(item.dictname);
            $wrap.append($dom);
        });
    }

    var selectedPro = {
        sort: null,
        brand: null,
        series: null
    };

    $('#p-sort-list').on('click', function (e) {
        var $ele = $(e.target).closest('.p-sort-item');
        if (!$ele) {
            return;
        }
        var item = JSON.parse($ele.attr('data-sort'));

        selectedPro.sort = item;
        window.location.hash = '#p-brand';

        var $title = $('#p-brand').find('.js-title');
        $title.find('img').attr('src', Api.imgUrlAddPrefix(item.imgurl));
        $title.find('span').html(item.dictname);
        getDictList(2, 1, 20, item.did).done(function (resp) {
            renderView(resp, DictType[2]);
        });
    });

    $('#p-brand-list').on('click', function (e) {
        var $ele = $(e.target).closest('.p-brand-item');
        if (!$ele) {
            return;
        }
        var pBrand = JSON.parse($ele.attr('data-brand'));

        selectedPro.brand = pBrand;
        getDictList(3, 1, 20, pBrand.did).done(function (resp) {
            if (resp.length <= 1) {
                $('a[href="#enquiry"]').click();
                // window.location.hash = '#enquiry';
            } else {
                window.location.hash = '#p-series';
                renderView(resp, DictType[3]);
            }
        });
    });

    $('#p-series-list').on('click', function (e) {

        var $ele = $(e.target).closest('.p-series-item');
        if (!$ele) {
            return;
        }
        selectedPro.series = JSON.parse($ele.attr('data-series'));
        $('a[href="#enquiry"]').click();
    });


    $(window).bind('viewEnter', function (e, viewName) {
        if (viewName == 'enquiry') {
            $('.js-progress').hide();
            var series = selectedPro.series;
            var brand = selectedPro.brand;
            if (series) {
                $('#productSeries').val(series.dictname);
            }
            if (brand) {
                $('#productBrand').val(brand.dictname);
            }
        }

        if (viewName == "my-center") {
            $('#me-nav').children().first().click();
        }
    });

    var initPage = function () {
        Common.registerEvents();
        var hash = window.location.hash;
        if (hash) {
            if (hash == '#p-brand' || hash == '#p-series') {
                hash = '#p-sort';
                window.location.hash = '#p-sort';
            }
            if (hash == "#my-center") {
                setTimeout(function () {//等待dom加载完成
                    $('#me-nav').children().first().click();
                }, 300);
            }
            $(hash).show();
            var name = hash.split('#')[1];
            Common._currentPage = {name: name, $dom: $(hash)};
            $('a[href="' + hash + '"]').click();
            if (hash != "#product-list-wrap") {
                $('#product-list-wrap').hide();
            }
        }
        else {
            $('#sale').click();
        }
        window.scrollTo(0, $('.gn-body')[0].offsetTop);
    };
    initPage();

    /*个人中心*/
    /* me-nav click */
    $('.m-wrap').hide();
    var $meNav = $('#me-nav');
    $meNav.find('li').click(function (e) {
        var $li = $(e.target).closest('li');
        $meNav.find('li.active').removeClass('active');
        $('.m-wrap').hide();
        $li.addClass('active');
        var target = $li.attr('data-target');
        $('#' + target).show(0, null, function () {
            $meNav.trigger('view.load', target);
        });
    });

    function initOrder() {
        if (!User.userInfo) {
            $('#sale').click();
            return setTimeout(needLogin, 300);
        }
        var userName = User.userInfo.username;

        OrderService.getOrderList(userName, 5, 1).done(function (resp) {
            OrderService.renderOrderList(resp);
        });
    }

    function initInvoice() {
        if (!User.userInfo) {
            $('#sale').click();
            return setTimeout(needLogin, 300);
        }
        var userId = User.userInfo.uid;
        InvoiceService.getInvoiceList(userId, 10, 1).done(function (resp) {
            InvoiceService.renderInvoiceList(resp);
        });
    }

    function initMessageList() {
        if (!User.userInfo) {
            $('#sale').click();
            return setTimeout(needLogin, 300);
        }
        var userId = User.userInfo.uid;
        MessageService.getMessageList(userId, 5, 1).done(function (resp) {
            MessageService.renderMessageList(resp);
        });
    }

    $meNav.bind('view.load', function (e, target) {
        if (target == 'me-order-wrap') {
            return initOrder();
        }
        if (target == 'me-invoice-wrap') {
            return initInvoice();
        }
        if (target == 'me-message-wrap') {
            return initMessageList();
        }
    });
    $('#me-invoice-wrap').find('.m-h-right').click(function () {
        $('#me-invoice-wrap').hide();
        $('#add-invoice').show();
    });

    $('#add-invoice').find('.m-h-right').click(function () {
        $('#add-invoice').hide();
        $('#me-invoice-wrap').show();
        InvoiceService.clearEditData();
    });

    $('.js-btn-commit').click(function () {
        var suggest = $('.js-suggest').val();
        if (!User.userInfo) {
            $('#sale').click();
            return setTimeout(needLogin, 300);
        }
        if (!suggest || suggest.length < 1) {
            return Alert.show('未输入任何信息', 'error');
        }
        var userId = User.userInfo.uid;
        var param = {
            uid: userId,
            suggest: suggest
        };
        Api.post('/personal/suggest', param).done(function (resp) {
            $('.js-suggest').val('');
            Alert.show('提交成功', 'success');
        });
    });

    var verifyCode = '';//保存验证码
    var $gainCodeBtn = $('.js-rp-gain-code');
    $gainCodeBtn.click(function () {
        if ($gainCodeBtn.hasClass('disabled')) {
            return;
        }
        var phone = $('.js-rp-phone').val();
        var errMsg = Validator.checkPhone(phone);
        if (errMsg.code) {
            return Alert.show(errMsg.msg, 'error');
        }
        $gainCodeBtn.addClass('disabled').html('已发送');
        var timer = 60;
        var tim = setInterval(function () {
            timer--;
            $gainCodeBtn.html('重新发送' + timer + 's');
            if (timer == 0) {
                $gainCodeBtn.removeClass('disabled').html('获取验证码');
                clearInterval(tim);
            }
        }, 1000);
        Api.get('/personal/identifycode?mobile=' + phone).done(function (resp) {
            Alert.show('验证码已发送到您的手机,请注意查收', 'success');
            verifyCode = resp;
        }).fail(function (err) {
            Alert.show('获取验证码失败,请重新获取', 'error');
        });
    });

    $('.js-i-save').click(function () {
        var titleName = $('.js-titleName').val();
        var bank = $('.js-bank').val();
        var account = $('.js-account').val();
        var tax = $('.js-tax').val();
        var companyTel = $('.js-companyTel').val();
        var receiver = $('.js-receiver').val();
        var receiverTel = $('.js-receiverTel').val();
        var address = $('.js-address').val();
        if (titleName.length < 1) {
            return Alert.show('开票抬头为必填项', 'error');
        }
        if (receiver.length < 1) {
            return Alert.show('签收人为必填项', 'error');
        }
        if (receiverTel.length < 1) {
            return Alert.show('签收人电话为必填项', 'error');
        }
        if (address.length < 1) {
            return Alert.show('地址为必填项', 'error');
        }
        var err = Validator.checkPhone(receiverTel);
        if (err) {
            return Alert.show(err.msg, 'error');
        }
        if (InvoiceService.currentEditInvoice) {
            var item = InvoiceService.currentEditInvoice;
            item.titlename = titleName;
            item.bank = bank;
            item.account = account;
            item.companytel = companyTel;
            item.custname = receiver;
            item.receivertel = receiverTel;
            item.tax = tax;
            item.uid = User.userInfo.uid;
            Api.post('personal/updateinvoice', item).done(function (resp) {
                $('[data-target="me-invoice-wrap"]').click();
                Alert.show('更新开票信息成功', 'success');
            }).fail(function () {
                Alert.show('更新开票信息失败', 'error');
            });
        } else {
            var param = {
                titlename: titleName,
                bank: bank,
                account: account,
                companytel: companyTel,
                receiver: receiver,
                custname: receiver,
                receivertel: receiverTel,
                tax: tax,
                uid: User.userInfo.uid
            };
            Api.post('personal/addinvoice', param).done(function (resp) {
                $('[data-target="me-invoice-wrap"]').click();
                Alert.show('添加开票信息成功', 'success');
            }).fail(function () {
                Alert.show('添加开票信息失败', 'error');
            });
        }
    });

    $('.js-rp-confirm').click(function () {
        var phone = $('.js-rp-phone').val();
        var code = $('.js-rp-code').val();
        var pwd = $('.js-rp-pwd').val();
        var qPwd = $('.js-rp-qPwd').val();
        var errMsg = Validator.checkPhone(phone);
        if (errMsg.code) {
            return Alert.show(errMsg.msg, 'error');
        }
        if (code.length == 0) {
            return Alert.show('请输入验证码', 'error');
        }
        if (code != verifyCode) {
            return Alert.show('验证码不正确', 'error');
        }
        if (pwd != qPwd) {
            return Alert('两次密码不一致', 'error');
        }
        Api.post('/personal/changepw?username=' + phone + '&pw=' + pwd + '&identifyCode=' + code).done(function (resp) {
            Alert.show('修改密码成功', 'success');
        }).fail(function () {
            Alert.show('修改密码失败', 'error');
        });
    });

    var $orderPage = $('#me-order-wrap');
    $orderPage.find('.js-prev').click(function () {
        var userId = User.userInfo.uid;
        var page = OrderService.currentPage - 1;
        if (page < 1) {
            return;
        }
        OrderService.getOrderList(userId, 5, page).done(function (resp) {
            OrderService.renderOrderList(resp);
        });
    });
    $orderPage.find('.js-next').click(function () {
        var userId = User.userInfo.uid;
        var page = OrderService.currentPage + 1;
        var totalPage = OrderService.totalPage;
        if (page > totalPage && totalPage != 1) {
            return;
        }
        OrderService.getOrderList(userId, 5, page).done(function (resp) {
            OrderService.renderOrderList(resp);
        });
    });

    $('.js-m-prev').click(function () {
        var userId = User.userInfo.uid;
        var page = MessageService.currentPage - 1;
        if (page < 1) {
            return;
        }
        MessageService.getMessageList(userId, 5, page).done(function (resp) {
            MessageService.renderMessageList(resp);
        });
    });
    $('.js-m-next').click(function () {
        var userId = User.userInfo.uid;
        var page = MessageService.currentPage + 1;
        MessageService.getMessageList(userId, 5, page).done(function (resp) {
            MessageService.renderMessageList(resp);
        });
    });

    var data = {ordPreOrder: null};
    $(window).on('view.quoteInit', function (e, qid) {
        $('#me-order-wrap').hide();
        var Q = $('#me-quote-wrap');
        Q.show();
        Q.attr('data-qid', qid);
        Api.get('/personal/selectquo?qid=' + qid).done(function (resp) {
            var quote = resp.quoQuotation;
            data.ordPreOrder = resp.ordPreOrder;
            Q.find('.js-q-id').html(quote.oid);
            Q.find('.js-q-time').html(quote.createdateString);
            Q.find('.js-q-status').html(Api.quoteStatusFormat(quote.status));
            var sel = $('#q-sel');
            if (quote.status > 0) {
                Q.find('.js-q-confirm').hide();
                Q.find('textarea.js-q-fb').hide();
                Q.find('p.js-q-fb').show().html(quote.remark);
                sel.hide();
                Q.find('.js-q-info').html(quote.receiver + ' ' + quote.address).show();
            } else {
                Q.find('textarea.js-q-fb').show();
                Q.find('p.js-q-fb').hide();
                sel.show();
                Q.find('.js-q-info').hide();
            }
            var wrap = Q.find('.q-p-list');
            var tmp = wrap.find('.q-p-item.template').clone();
            wrap.find('.q-p-item').remove();//清理数据
            wrap.append(tmp);
            $.each(resp.ordPreOrder, function (i, item) {
                var $dom = tmp.clone().removeClass('template');
                $dom.find('.js-q-p-id').html(item.poid);
                $dom.find('.js-q-p-name').html(item.productname);
                $dom.find('.js-q-p-price').html(Api.moneyFormat(item.price, 2, '￥'));
                $dom.find('.js-q-price').html(Api.moneyFormat(item.quoPrice || item.price, 2, '￥'));
                $dom.find('.js-q-qty').html(item.qty);
                $dom.find('.js-q-p-stock').html(item.stock);
                wrap.append($dom);
            });
        });
        var uid = User.userInfo.uid;
        InvoiceService.getInvoiceList(uid).done(function (resp) {
            var sel = $('#q-sel');
            var opt = sel.find('.js-q-opt.template').clone();
            sel.find('.js-q-opt').remove();//清理数据
            sel.append(opt);
            $.each(resp, function (i, item) {
                var $o = opt.clone().removeClass('template');
                $o.val(item.iid);
                $o.html(item.titlename + ' ' + item.address);
                sel.append($o);
            });
        });
    });

    $('.js-q-confirm').click(function () {
        var invoice = $('#q-sel').val();
        if (!invoice || invoice == '') {
            return Alert.show('请选择开票消息,若无开票信息可在个人中心,开票信息中添加!', 'error');
        }
        var req = {
            iid: invoice,
            jianyi: $('js-q-fb').val(),
            qid: $('#me-quote-wrap').attr('data-qid'),
            uid: User.userInfo.uid,
            ordpreorderid: []
        };
        $.each(data.ordPreOrder, function (i, order) {
            var o = {
                poid: order.poid,
                quoPrice: order.quoPrice,
                qty: order.qty
            };
            req.ordpreorderid.push(o);
        });
        Api.post('/personal/updatequo', req).done(function (resp) {
            Alert.show('确认报价成功', 'success');
        }).fail(function (err) {
            Alert.show('确认报价失败', 'error');
        });
    });
});
