/**
 * Created by hyj on 15-3-21.
 */
mobileModule.controller('MobileController', function ($scope) {

    $scope.init = function() {
        // index.html
        $scope.locations = [ "上海 - 浦东新区", "上海 - 长宁区" ];
        $scope.location = $scope.locations[0];
        $scope.levels = [ "二星以下/经济", "三星级/舒适", "四星级/高档", "五星级/豪华"];
        $scope.level = $scope.levels[0]
        $scope.expire = 3;

        $('price-slider').slider({min: 10, max: 5000, step: 10,
            orientation: "horizontal", handle: "round"});
    };

    $scope.init();
});
