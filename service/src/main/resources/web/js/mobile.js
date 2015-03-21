/**
 * Created by hyj on 15-3-21.
 */
mobileModule.controller('MobileController', function ($scope, DataLoadService) {

    $scope.addStudent = function() {

    }

    $scope.init = function() {
        $scope.locations = [ "上海 - 浦东新区", "上海 - 长宁区" ];
    };

    $scope.init();
});