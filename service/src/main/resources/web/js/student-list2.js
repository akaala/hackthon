/**
 * Created by yj.huang on 14-8-21.
 */
studentModule.controller('StudentList2Controller', function ($scope) {

    $scope.addStudent = function() {
        var newStudent = {name: $scope.newName, age: $scope.newAge, gendor: $scope.newGender};
        $scope.students.push(newStudent);
    }

//    $scope.init = function() {
//        $scope.students = [
//            {name: "Tom", age: "30", gendor: 0},
//            {name: "Mary", age: "25", gendor: 1},
//            {name: "Jeff", age: "23", gendor: 0},
//            {name: "Sam", age: "32", gendor: 0},
//            {name: "William", age: "18", gendor: 0}
//        ]
//    };

    $scope.init();
});