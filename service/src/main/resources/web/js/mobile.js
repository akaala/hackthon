/**
 * Created by hyj on 15-3-21.
 */
var RESTFUL_API = "http://10.16.52.103:4567";
var USER_ID = Math.floor((Math.random() * 10) % 5 + 1);
var PAGES = {
    NEW_ORDER: "newOrder",
    ORDER_LIST: "orderList",
    ORDER_DASHBOARD: "orderDashboard",
    ORDER_DETAIL: "orderDetail;"
};
var LOCATIONS = [ {name: "上海-徐家汇", x: 31.0930, y: 121.2651}, {name: "上海-虹桥", x:31.1153, y:121.2011},
    {name:"上海-人民广场", x:31.1339, y:121.2820}];
var REFRESH_INTEVAL_MILLISECONDS = 1000;
var currentOrder;

function showOrderConfirmDialog(hotelId)
{
    console.log("show order confirm dialog");
    $("#orderConfirmDialog").modal({
        backdrop:false,
        keyboard:true,
        show:true}
    );

    if (!currentOrder.bidMap || !currentOrder.bidMap[hotelId])
        return;

    var scope = angular.element($("#orderConfirmDialog")).scope();
    scope.$apply(function(){
        var hotel = currentOrder.bidMap[hotelId];
        scope.bidToConfirm = hotel[hotel.length - 1];
        console.log("bid to confirm: ", scope.bidToConfirm);
    })
}

mobileModule.controller('RootController', function ($rootScope, $scope, $http, $interval) {
    $rootScope.userId = USER_ID;

    $rootScope.pages = PAGES;

    // Set initial page
    $rootScope.page = PAGES.NEW_ORDER;

    // Refresh map periodically
    $interval ( function ( )
    {
        if ($scope.map && $rootScope.currentOrder && $rootScope.currentOrder.status == 'inbid' && $rootScope.page == PAGES.ORDER_DASHBOARD) {
            console.log("Refreshing map with order: ", $rootScope.currentOrder);

            $http.get(RESTFUL_API + "/order/" + $rootScope.currentOrder.orderid).success(function(data) {
                $rootScope.currentOrder = data;
                currentOrder = data;
                $rootScope.nBids = 0;
                console.log("result of refresh map. order: ", data);
                $rootScope.map.clearOverlays();
                for (var hotelId in $rootScope.currentOrder.bidMap) {
                    var hotel = $rootScope.currentOrder.bidMap[hotelId];
                    var bid = hotel[hotel.length - 1];
                    var point = new BMap.Point( bid.hotel.lng, bid.hotel.lat);

                    $rootScope.nBids++;
                    // DEBUG
                    console.log("add marker at: ", bid.hotel.lng, bid.hotel.lat, " nbids: ", $rootScope.nBids);

                    var marker = new BMap.Marker(point);  //创建标注
                    $rootScope.map.addOverlay(marker);

                    var infoWindow1 = new BMap.InfoWindow(bid.hotel.name + " 出价: " + ($rootScope.currentOrder.hotelRequest.price + bid.extraPrice) + " 元。" +
                    "<button class='btn btn-default' type='button' class='btn btn-primary' onclick='showOrderConfirmDialog("+hotelId+")'>就住这家！</button>");
                    marker.addEventListener("click", function(){this.openInfoWindow(infoWindow1);});

                }
            });

            $scope.title = "竞价中:";
        } else if ($rootScope.currentOrder && $rootScope.currentOrder.status == 'done') {
            $scope.title = "已成交：" + $rootScope.currentOrder.winningBid.location
                + "，成交价: " + $rootScope.currentOrder.dealPrice + " 元";
        }
    } , REFRESH_INTEVAL_MILLISECONDS );

    $rootScope.goto = function(page) {
        $rootScope.page = page;
    }

    // Create map for the first time
    $rootScope.createMap = function() {
        if ($rootScope.currentOrder)
        {
            var map = new BMap.Map("allMap");           // 创建Map实例
            var locationReq = $rootScope.currentOrder.hotelRequest.location;
            var location = null;
            for (var i = 0; i < LOCATIONS.length; i++) {
                if (locationReq == LOCATIONS[i].name) {
                    location = LOCATIONS[i];
                    break;
                }
            }
            // var point = new BMap.Point(116.404, 39.915);
            console.log("center map at: ", location.y, location.x);
            var point = new BMap.Point(location.y, location.x);
            map.centerAndZoom(point,12);                 // 初始化地图,设置中心点坐标和地图级别。
            map.addControl(new BMap.ZoomControl());      //添加地图缩放控件
            $rootScope.map = map;
        }
    }


});

mobileModule.controller('NewOrderController', function ($rootScope, $scope, $http) {

    $scope.sendOrder = function()
    {
        // http://10.16.52.103:4567/order/buy?userid=1&price=300&star=5&place=上海-浦东&type=1
        $http.get(RESTFUL_API + "/order/userbid",
            {
                params:
                {
                    userid: USER_ID,
                    star: $scope.star.id,
                    place: $scope.location.name,
                    type: $scope.type,
                    timeout: $scope.expire,
                    price: $scope.bidPrice
                }
            }).success(function(data) {
                var order = data;
                currentOrder = order;
                $rootScope.currentOrder = order;
                $rootScope.goto(PAGES.ORDER_DASHBOARD);
                $rootScope.createMap();
                console.log("result of send order. order id: ", order);
            });
    }

    $scope.refreshBidRange = function()
    {
        // http://10.16.52.103:4567/order/probability?userid=1&star=5&place=%E4%B8%8A%E6%B5%B7-%E6%B5%A6%E4%B8%9C&type=1&timeout=60
        console.log("refersh bid range. location: ", $scope.location);
        $http.get(RESTFUL_API + "/order/probability",
        {
            params:
            {
                userid: USER_ID,
                star: $scope.star.id,
                place: $scope.location.name,
                type: $scope.type,
                timeout: $scope.expire
            }
        }).success(function(data) {
            var min = data[0].price;
            var max = data[data.length - 1].price;
            var step = data[1].price - data[0].price;

            $scope.probabilities = {};
            for (var i = 0; i < data.length; i++)
            {
                $scope.probabilities[data[i].price] = Math.floor(data[i].probability * 100);
            }

            var parent = $(".slider").parent();
            $(".slider").remove();
            $('#price-slider').empty();
            parent.append("<div id='price-slider'></div>")

            $scope.bidPrice = min;
            $('#price-slider').slider({min: min, max: max, step: step, value: 0.8*max,
                orientation: "horizontal", handle: "round"}).on('slide', function(ev){
                    console.log("price is ", ev.value);
                    $scope.bidPrice = ev.value;
                    $scope.$apply();
                });

            console.log("result of refresh bid range: ", data);
        });
    }

    $scope.initDates = function() {
        var nowTemp = new Date();
        var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

        var checkin = $('#dpd1').datepicker({
            onRender: function(date) {
                return date.valueOf() < now.valueOf() ? 'disabled' : '';
            }
        }).on('changeDate', function(ev) {
                if (ev.date.valueOf() > checkout.date.valueOf()) {
                    var newDate = new Date(ev.date)
                    newDate.setDate(newDate.getDate() + 1);
                    checkout.setValue(newDate);
                }
                checkin.hide();
                $('#dpd2')[0].focus();
            }).data('datepicker');
        var checkout = $('#dpd2').datepicker({
            onRender: function(date) {
                return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
            }
        }).on('changeDate', function(ev) {
                checkout.hide();
            }).data('datepicker');
    }

    $scope.init = function() {
        // index.html
        $scope.locations = LOCATIONS;
        $scope.location = $scope.locations[0];
        $scope.stars = [ {id: 1, name: "二星级以下"}, {id: 2, name: "二星级"}, {id: 3, name: "三星级"}, {id: 4, name: "四星级"}, {id: 5, name: "五星级"}];
        $scope.star = $scope.stars[0];
        $scope.types = [ {id: 1, name: "经济型"}, {id: 2, name: "商旅型"}];
        $scope.type = 1;
        $scope.expire = 3;
        $scope.probabilities = {};

        $scope.initDates();

        $scope.bidPrice = 10;
        $('#price-slider').slider({min: 10, max: 5000, step: 10, value: 4800,
            orientation: "horizontal", handle: "round"}).on('slide', function(ev){
                console.log("price is ", ev.value);
                $scope.bidPrice = ev.value;
                $scope.$apply();
            });

        $http.get(RESTFUL_API + "/order/counts").success(function(data) {
            console.log("stats:", data);

            $scope.stats = data;
        });
    };

    $scope.init();
});

mobileModule.controller('OrderListController', function ($rootScope, $scope, $http) {
    $scope.init = function() {
        $http.get(RESTFUL_API + "/user/orders",
            {
                params:
                {
                    userid: USER_ID
                }
            }).success(function(data) {
                console.log("order list loaded: ", data);
                $scope.orderHistory = data;
                for (var i = 0; i < data.length; i++)
                {
                    var order = data[i];
                    order.date = new Date(order.createTime);
                    order.cstatus = (order.status == "success") ? "已成交" : "已流单";
                }
            });
    }

    $scope.init();

    $scope.openOrder = function(order) {
        if (order.status == '进行中') {
            $rootScope.goto(PAGES.ORDER_DASHBOARD);
        } else if (order.status == "还价中") {
            // TODO a hotel with a defiend price
        } else if (order.status == "已完成") {
            $rootScope.goto(PAGES.ORDER_DETAIL);
        }
    }


});

mobileModule.controller('OrderDashboardController', function ($rootScope, $scope, $http) {
    $scope.confirmOrder = function(bid) {
        $http.get(RESTFUL_API + "/order/confirm",
            {
                params:
                {
                    orderid: $scope.currentOrder.orderid,
                    hotelbidid: bid.bidId
                }
            }).success(function(data) {
                console.log("result of confirm order. order id: ", data);
                $("#allMap").remove();
                $rootScope.goto(PAGES.ORDER_LIST);
            });
    }

    $scope.init = function() {

    }

    $scope.init();
});
