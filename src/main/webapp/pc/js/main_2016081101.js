///<reference path="jquery.d.ts" />
var AppConfig = (function () {
    function AppConfig() {
        this.baseUrl = '';
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
            return { code: -1, msg: '手机号码为空' };
        }
        if (!this.isPhone(s)) {
            return { code: -1, msg: '手机号码格式不正确' };
        }
        return { code: 0, msg: 'ok' };
    };
    Validator.checkPwd = function (s) {
        if (s.length < 6) {
            return { code: -1, msg: '密码为小于6位的字母或数字' };
        }
        return { code: 0, msg: 'ok' };
    };
    Validator.phoneRegExp = /^1[34578]\d{9}$/;
    Validator.pwdRegExp = /^(?!\d+$)(?![^a-zA-Z]){6,18}$/;
    return Validator;
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
            _self._currentPage = { name: newHash, $dom: $('#' + newHash) };
            _self._currentPage.$dom.show();
        };
    };
    Common.gnNavBarClick = function () {
        var _self = this;
        $('#gn-nav-bar').on('click', 'a', function (e) {
            var $target = $(e.target);
            var id = $target.attr('id');
            if (_self._activeNav && _self._activeNav.name != id) {
                _self._activeNav.$dom.removeClass('active');
            }
            if (id == 'xunjia' || id == 'about') {
                $('#search-groups').hide();
            }
            else {
                $('#search-groups').show();
            }
            _self._activeNav = { name: id, $dom: $target };
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
    ;
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
    ;
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
            $imgList.animate({ "left": -(maxWidth / childNum) * pageIndex }, 200);
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
        if (type === void 0) { type = 1; }
        if (rows === void 0) { rows = 20; }
        if (page === void 0) { page = 1; }
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
            $('#userName').html(resp.username).show();
        });
    };
    User.signIn = function (user) {
        return Api.post('/personal/register', user);
    };
    return User;
}());
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
    function needLogin() {
        Alert.show('请先登录', 'warning');
        setTimeout(function () {
            $('#loginModal').modal('show');
        }, 1000);
    }
    //隐藏登陆框
    $('#loginModal').on('hidden.bs.modal', function () {
        $('#registerForm').addClass('hidden');
        $('#loginForm').removeClass('hidden');
    });
    $('#recommend').click(function () {
        $('#product-list').addClass('sale');
        ProductList.clearViewData();
        ProductList.init(2);
    });
    $('#sale').click(function () {
        $('#product-list').removeClass('sale');
        ProductList.clearViewData();
        ProductList.init(1);
    });
    $('#product-center').click(function () {
        $('#product-list').addClass('sale');
        ProductList.clearViewData();
        ProductList.init(1);
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
            $('#userName').html(resp.username).show();
        }).fail(function (err) {
            Alert.show(err.message, "danger");
        });
    });
    $('#js-gain-code').click(function () {
        var phone = $('#registerForm').find('[name="phone"]').val();
        var err = Validator.checkPhone(phone);
        if (err.code) {
            return Alert.show(err.msg, 'error');
        }
        var timer = 60;
        User.gainCode(phone).then(function (data) {
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
        }, function () {
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
        User.signIn(user).then(function (resp) {
            Alert.show('注册成功', 'success');
        }, function (error) {
            Alert.show(error.message, 'error');
        });
    });
    $('#commit-req').click(function () {
        if (!User.isLogin()) {
            return needLogin();
        }
        var demand = {
            brandname: $('#productBrand').val(),
            productname: $('#productName').val(),
            qty: $('#productNum').val(),
            modelname: $('#productModel').val(),
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
        Api.post('/req/product-req', demand).done(function (resp) {
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
                $progressBar.attr({ value: e.loaded, max: e.total });
                $progressBar.show();
            }
        }
        function errorHandler(e) {
            Alert.show(e.message, 'error');
        }
        function successHandler(resp) {
            var param = {
                uid: User.userInfo.uid,
                fileid: resp.result
            };
            Api.post('/req/product-req', param).done(function (res) {
                $progressBar.hide();
                $uploadBtn.show();
                Alert.show('上传成功', 'success');
            });
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
        $('#productModel').val('');
    });
    var initPage = function () {
        Common.registerEvents();
        var hash = window.location.hash;
        if (hash) {
            $(hash).show();
            var name = hash.split('#')[1];
            Common._currentPage = { name: name, $dom: $(hash) };
            $('a[href="' + hash + '"]')[0].click();
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
});
