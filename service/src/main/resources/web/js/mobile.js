/**
 * Created by hyj on 15-3-21.
 */
mobileModule.controller('RootController', function ($rootScope, $scope, $http) {
    $rootScope.page = "newOrder";
});

mobileModule.controller('NewOrderController', function ($rootScope, $scope, $http) {

    $scope.sendOrder = function()
    {
        $rootScope.page = "orderList";
    }

    $scope.refreshBidRange = function()
    {
        console.log("refersh bid range. location: ", $scope.location);
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
            [{date: new Date(), status: "完成", finalPrice: "1200", hotel: "银行大酒店"}
            ,{date: new Date(), status: "完成", finalPrice: "1200", hotel: "银行大酒店"}];
    }

    $scope.init();
});
