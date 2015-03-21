/**
 * Created by yj.huang on 14-8-22.
 */
angular.module("DataLoad", []).factory("DataLoadService", [
    function () {
        return {
            getStudents: function() {
                return [
                    {name: "Tom", age: "30", gendor: 0},
                    {name: "Mary", age: "25", gendor: 1},
                    {name: "Jeff", age: "23", gendor: 0},
                    {name: "Sam", age: "32", gendor: 0},
                    {name: "William", age: "18", gendor: 0}
                ];
            }
        }
    }
]);