/**
 * Created by Administrator on 2015-3-21.
 */

var RestUrl = "http://192.168.255.16:4567";


var hotelModule = angular.module('Hotel', ['angular-jqcloud', 'n3-pie-chart','mgcrea.ngStrap']);

function newOrder(orderid, status,bidTimeout, location, price, star, type,
                  userName, userBidTimes, userBidReturnTimes,
                  income, birth, userGender,
                  createTime, dealPrice, expiretime, extraBidTimeout, cloud,
                  pieOption, pieData, gaugeOption, gaugeData) {
    this.orderid = orderid;
    this.status = status;
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
    this.isCollapsed = true;
    this.addPrice = 0;
}

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        if (str.indexOf("&") != -1) {
            strs = str.split("&");
            for (var i = 0; i < strs.length; i++) {
                theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
            }
        } else {
            theRequest[str.split("=")[0]] = unescape(str.split("=")[1]);
        }
    }
    return theRequest;
}


hotelModule.controller('HotelCtrl', function ($rootScope, $scope, $http, $location) {
    //var HotelId = Math.ceil(Math.random()*5);

    var Request = new Object();
    Request = GetRequest();

    $scope.HotelId = Request['id']

    $scope.orders = []
    $http.get(RestUrl + "/hotel/orders" + "?hotelid=" + HotelId)
        .success(function (data, status, headers, config) {

            for (var i = 0; i < data.length; i++) {
                var d = data[i];
                console.log(d);
                var cloud = buildCloud(d.user.tags);
                var pieOption = {thickness: 20};
                var pieData = buildPieData(d.user.favorites);

                var gaugeOption =  {thickness: 20, mode: "gauge", total: 100};
                var gaugeDate = buildGaugeData(d.user.bidThanOthers);

                $scope.orders.push(new newOrder(d.orderid, buildStatus(d.status), d.bidTimeout, d.hotelRequest.location, d.hotelRequest.price,
                    d.hotelRequest.star, d.hotelRequest.type, d.user.name, d.user.bidTimes, d.user.bidReturnTimes,
                    d.user.income, d.user.birth,
                    d.user.gender, d.createTime, d.dealPrice, d.expiretime, d.extraBidTimeout, cloud,
                    pieOption, pieData, gaugeOption, gaugeDate));
            }
            console.log($scope.orders)
        });


    $scope.getOrder = function (order) {
        $http.get(RestUrl + "/order/hotelbid" + "?hotelid=" + HotelId + "&orderid=" + order.orderid
        + "&extra=" + order.addPrice + "&comment=" + "No comment Now!");
    }

    $scope.addPrice = 0;



    var buildCloud = function (tags) {
        var t = [];
        for ( var p in tags ) {
            var data ={};
            data.text =p;
            data.weight = tags[p];
            t.push(data);
        }
        return t;
    }

    var buildPieData = function(tags) {
        var t = [];
        var i = 1;
        for ( var p in tags) {
            var data ={};
            data.label =p;
            data.value = tags[p];

            if (i ==1) {
                data.color = "#1f77b4"

            } else if (i ==2) {
                data.color =  "#ff7f0e"

            } else {
                data.color =  "#2ca02c"

            }
            i++
            t.push(data);
        }
        return t;
    }

    var buildStatus = function(s) {
        if (s == "inbid") {
            return "竞价中"
        } else if (s == "extra") {
            return "议价中"
        } else if (s == "success") {
            return "成交"
        } else if (s == "fail") {
            return "流单"
        }
    }
    var buildGaugeData = function (data) {
        return [
            {label: "忠诚度超过其他用户比例", value: (data *100).toFixed(2), color: "#d62728", suffix: "%"}
        ];
    }


});