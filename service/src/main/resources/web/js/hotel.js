/**
 * Created by Administrator on 2015-3-21.
 */

var RestUrl = "http://10.16.52.103:4567";
var HotelId = 2;

var hotelModule = angular.module('Hotel', []);

function newOrder(bidTimeout, location, price, star, type,
                  userName, userBidTimes, userBidReturnTimes, userGender,
                  createTime, dealPrice, expiretime, extraBidTimeout) {
    this.bidTimeout = bidTimeout;
    this.location = location;
    this.price = price;
    this.star = star;
    this.type = type;
    this.userName = userName;
    this.userBidTimes = userBidTimes;
    this.userBidReturnTimes = userBidReturnTimes;
    this.userGender = userGender;
    this.creatTime = createTime;
    this.dealPrice = dealPrice;
    this.expiretime = expiretime;
    this.extraBidTimeout = extraBidTimeout;
}


hotelModule.controller('HotelCtrl', function ($rootScope, $scope, $http) {
    $scope.orders = []
    $http.get(RestUrl + "/hotel/orderlist" + "?hotelid=" + HotelId)
        .success(function (data, status, headers, config) {

            for (var i = 0; i < data.length; i++) {
                var d = data[i];
                $scope.orders.push(new newOrder(d.bidTimeout, d.hotelRequest.location, d.hotelRequest.pice,
                    d.hotelRequest.star, d.hotelRequest.type, d.user.name, d.user.bidTimes, d.user.bidReturnTimes,
                    d.user.gender, d.createTime, d.dealPrice, d.expiretime, d.extraBidTimeout));
            }
        });


    $scope.getOrder = function (orderId) {
        alert(orderId);
    }

    $scope.addPrice = 0;

});