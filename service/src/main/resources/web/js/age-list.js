/**
 * Created by yj.huang on 14-8-22.
 */
/**
 * Created by yj.huang on 14-8-21.
 */
studentModule.controller('AgeListController', function ($scope, $http) {
    $scope.init = function() {
//        $scope.students = [
//            {name: "Tom", age: "30", gendor: 0},
//            {name: "Mary", age: "25", gendor: 1},
//            {name: "Jeff", age: "23", gendor: 0},
//            {name: "Sam", age: "32", gendor: 0},
//            {name: "William", age: "18", gendor: 0}
//        ]

        $scope.age2StudentNames = {};
        for (var i = 0; i < $scope.students.length;i++) {
            var category;
            var age = $scope.students[i].age;
            if (age < 10) {
                category = "0-10";
            } else if (age < 20) {
                category = "10-20";
            } else if (age < 30) {
                category = "20-30";
            } else {
                category = ">30";
            }

            if (undefined == $scope.age2StudentNames[category]) {
                $scope.age2StudentNames[category] = $scope.students[i].name;
            } else {
                $scope.age2StudentNames[category] += "," + $scope.students[i].name;
            }
        }
    };

    $scope.init();
});