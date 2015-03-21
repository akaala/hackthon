/**
 * Created by yj.huang on 14-8-21.
 */
studentModule.controller('StudentListController', function ($scope, DataLoadService) {

    $scope.addStudent = function() {
        var newStudent = {name: $scope.newName, age: $scope.newAge, gendor: $scope.newGender};
        $scope.students.push(newStudent);
    }

    $scope.init = function() {
        $scope.students = DataLoadService.getStudents();
    };

    $scope.init();
});