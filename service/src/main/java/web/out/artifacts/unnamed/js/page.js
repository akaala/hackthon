/**
 * Created by yj.huang on 14-8-22.
 */
/**
 * Created by yj.huang on 14-8-21.
 */
studentModule.controller('PageController', function ($scope, DataLoadService) {



    $scope.init = function() {
        $scope.students = DataLoadService.getStudents();
    };

    $scope.init();
});