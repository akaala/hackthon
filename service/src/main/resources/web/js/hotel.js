/**
 * Created by Administrator on 2015-3-21.
 */

var RestUrl = "http://10.16.52.103:4567";
var HotelId = 2;

var hotelModule = angular.module('Hotel', ['angular-jqcloud', 'n3-pie-chart','mgcrea.ngStrap']);

function newOrder(orderid, bidTimeout, location, price, star, type,
                  userName, userBidTimes, userBidReturnTimes,
                  income, birth, userGender,
                  createTime, dealPrice, expiretime, extraBidTimeout, cloud,
                  pieOption, pieData, gaugeOption, gaugeData) {
    this.orderid = orderid;
    this.bidTimeout = bidTimeout;
    this.location = location;
    this.price = price;
    this.star = star;
    this.type = type;
    this.userName = userName;
    this.userBidTimes = userBidTimes;
    this.userBidReturnTimes = userBidReturnTimes;
    this.income = income;
    this.birth = birth;
    this.userGender = userGender;
    this.creatTime = createTime;
    this.dealPrice = dealPrice;
    this.expiretime = expiretime;
    this.extraBidTimeout = extraBidTimeout;
    this.cloud = cloud;
    this.pieOption = pieOption;
    this.pieData = pieData;
    this.gaugeOption = gaugeOption;
    this.gaugeData = gaugeData;
}


hotelModule.controller('HotelCtrl', function ($rootScope, $scope, $http) {
    $scope.orders = []
    $http.get(RestUrl + "/hotel/orders" + "?hotelid=" + HotelId)
        .success(function (data, status, headers, config) {

            for (var i = 0; i < data.length; i++) {
                //console.log(data);
                var d = data[i];
                var cloud = buildCloud();
                var pieOption = {thickness: 40};
                var pieData = buildPieData();

                var gaugeOption =  {thickness: 20, mode: "gauge", total: 100};
                var gaugeDate = buildGaugeData();

                $scope.orders.push(new newOrder(d.orderid, d.bidTimeout, d.hotelRequest.location, d.hotelRequest.price,
                    d.hotelRequest.star, d.hotelRequest.type, d.user.name, d.user.bidTimes, d.user.bidReturnTimes,
                    d.user.income, d.user.birth,
                    d.user.gender, d.createTime, d.dealPrice, d.expiretime, d.extraBidTimeout, cloud,
                    pieOption, pieData, gaugeOption, gaugeDate));
            }
            console.log($scope.orders)
        });


    $scope.getOrder = function (orderId) {
        $http.get(RestUrl + "/order/hotelbid" + "?hotelid=" + HotelId + "&orderid=" + orderId
        + "&extra=" + $scope.addPrice + "&comment=" + "No comment Now!");
    }

    $scope.addPrice = 0;

    var buildCloud = function () {
        return [{text: "Lorem", weight: 13},
            {text: "Ipsum", weight: 10.5},
            {text: "Dolor", weight: 9.4},
            {text: "Sit", weight: 8},
            {text: "Amet", weight: 6.2},
            {text: "Consectetur", weight: 5},
            {text: "Adipiscing", weight: 5},
            {text: "Elit", weight: 5}]
    }

    var buildPieData = function() {
        return [
            {label: "One", value: 11, color: "#1f77b4"},
            {label: "Two", value: 22, color: "#ff7f0e"},
            {label: "Three", value: 33, color: "#2ca02c"}
        ];
    }

    var buildGaugeData = function () {
        return [
            {label: "比其他用户忠诚", value: 78, color: "#d62728", suffix: "%"}
        ];
    }
});