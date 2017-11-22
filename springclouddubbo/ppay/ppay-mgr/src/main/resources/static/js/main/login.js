/**
 * Created by xinhc on 2017/4/6.
 */
var app = angular.module('mainApp', []);

app.controller('mainCtrl', function ($scope, $http) {
    $scope.loginUser = new Object();
    $scope.loginErrorMsg = "";
    $scope.userLogin = function (loginUser) {
        var userInfo = new Object();
        userInfo.username = loginUser.username;
        userInfo.password = loginUser.password;
        userInfo.remember = loginUser.remember;
        $http({
            method: "post",
            url: "userLogin",
            headers: {
                "dataType": "json"
            },
            data: userInfo
        }).then(function onSuccess(response) {
            if (response.data.code == 1) {
                $scope.loginErrorMsg = response.data.msg;
                $("#loginError").fadeIn();
                window.setTimeout(function () {
                    $("#loginError").fadeOut();
                }, 2000);
            } else if (response.data.code == 0) {
                window.location.href = "index.html";
            }

        }).catch(function onError(response) {

        });

    };
    //
});