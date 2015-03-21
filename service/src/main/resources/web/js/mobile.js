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
}

mobileModule.controller('RootController', function ($rootScope, $scope, $http) {
    $rootScope.pages = PAGES;
    $rootScope.page = PAGES.NEW_ORDER;

    $rootScope.goto = function(page) {
        $rootScope.page = page;
    }
});

mobileModule.controller('NewOrderController', function ($rootScope, $scope, $http) {

    $scope.sendOrder = function()
    {
        // http://10.16.52.103:4567/order/buy?userid=1&price=300&star=5&place=上海-浦东&type=1
        $http.get(RESTFUL_API + "/order/buy",
            {
                params:
                {
                    userid: USER_ID,
                    star: $scope.star.id,
                    place: $scope.location,
                    type: $scope.type,
                    timeout: $scope.expire,
                    price: $scope.bidPrice
                }
            }).success(function(data) {
                var order = data;
                $rootScope.currentOrderInDashboard = order;
                $rootScope.goto(PAGES.ORDER_DASHBOARD);
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
                place: $scope.location,
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
            $('#price-slider').slider({min: min, max: max, step: step,
                orientation: "horizontal", handle: "round"}).on('slide', function(ev){
                    console.log("price is ", ev.value);
                    $scope.bidPrice = ev.value;
                    $scope.$apply();
                });

            console.log("result of refresh bid range: ", data);
        });
    }

    $scope.init = function() {
        // index.html
        $scope.locations = [ "上海 - 浦东新区", "上海 - 长宁区" ];
        $scope.location = $scope.locations[0];
        $scope.stars = [ {id: 1, name: "二星级以下"}, {id: 2, name: "二星级"}, {id: 3, name: "三星级"}, {id: 4, name: "四星级"}, {id: 5, name: "五星级"}];
        $scope.star = $scope.stars[0];
        $scope.types = [ {id: 1, name: "经济型"}, {id: 2, name: "商旅型"}];
        $scope.type = 1;
        $scope.expire = 3;
        $scope.probabilities = {};

        $scope.bidPrice = 10;
        $('#price-slider').slider({min: 10, max: 5000, step: 10,
            orientation: "horizontal", handle: "round"}).on('slide', function(ev){
                console.log("price is ", ev.value);
                $scope.bidPrice = ev.value;
                $scope.$apply();
            });
    };

    $scope.init();
});

mobileModule.controller('OrderListController', function ($rootScope, $scope, $http) {
    $scope.init = function() {
        $scope.orderHistory =
            [{date: new Date(), status: "进行中", finalPrice: "1200", hotel: "银行大酒店"}
            ,{date: new Date(), status: "完成", finalPrice: "1200", hotel: "银行大酒店"}];
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
