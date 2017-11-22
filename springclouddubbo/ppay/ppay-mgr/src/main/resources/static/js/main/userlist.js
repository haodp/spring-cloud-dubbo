var app = angular.module('userApp', []);

app.controller('userCtrl', function ($scope, $http) {
    $scope.massgae = "提示信息";
    $scope.userList = [];
    $scope.userSearch = [];
    $scope.userCreate = [];
    $scope.userView = [];
    $scope.userEdit = [];
    $scope.userDelete = [];
    $scope.user = "";

    //分页相关参数
    $scope.pageSize = 4;//每页显示记录个数
    $scope.pageNo = 1;//选中页数
    $scope.totalPage = 1;//总页数
    $scope.totalCount = 1;//记录总数
    $scope.pageSizeList = [1, 2, 3, 4, 5];
    //分页参数结束
    $scope.genderList = [{
        value: 1,
        genderName: '男'
    }, {
        value: 2,
        genderName: '女'
    }];

    $http({
        method: "GET",
        url: "getRoleListForUser",
        headers: {
            "dataType": "json"
        },
        data: {}
    }).then(function onSuccess(response) {
        // Handle success
        $scope.roleList = response.data.data;
        $scope.userCreate.selectRole = $scope.roleList[0];
    }).catch(function onError(response) {

    });

    //重置分页
    resetPage = function () {
        var pageNo = parseInt($scope.pageNo);
        var totalPages = parseInt($scope.totalPage);

        $('.pagination').jqPaginator('option', {
            currentPage: pageNo,
            totalPages: totalPages
        });
    };

    //输入框自动提示
    initAutocomplete = function (userList) {
        var usernameList = [];
        angular.forEach(userList, function (user) {
            usernameList.push(user.username);
        });

        $("#searchUsername").autocomplete({
            source: usernameList
        });
    };

    $scope.changePageSize = function (pageSize) {
        $scope.pageNo = 1;
        $scope.getUser();
        resetPage();
    };

    $scope.searchUser = function () {
        $scope.pageNo = 1;
        $scope.getUser();
    };

    $scope.getUser = function (user) {
        var username = $scope.userSearch.username;
        var userInfo = new Object();
        userInfo.pageSize = $scope.pageSize;
        userInfo.pageNo = $scope.pageNo;
        if (!angular.isUndefined(username)){
            userInfo.username = username;
        }

        $http({
            method: "post",
            url: "getUserList",
            headers: {
                "dataType": "json"
            },
            data: userInfo
        }).then(function onSuccess(response) {
            $scope.userList = response.data.data.userList;
            $scope.totalPage = response.data.data.totalPage;//总页数
            $scope.totalCount = response.data.data.totalCount;//总条数
            resetPage();
            initAutocomplete($scope.userList);


        }).catch(function onError(response) {

        });
    };

    //分页
    $('.pagination').jqPaginator(
        {
            totalPages: 10,
            visiblePages: 5,
            currentPage: 1,
            activeClass: 'active',
            first: '<li class="first"><a href="javascript:void(0);">首页</a></li>',
            prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
            next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
            last: '<li class="last"><a href="javascript:void(0);">末页</a></li>',
            page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
            onPageChange: function (num) {
                $scope.pageNo = num;
                console.log(num);
                $scope.getUser();
            }
        });

    $scope.viewUserModal = function (user) {
        $scope.userView = user;
        $("#modal-view").modal();
    };

    $scope.createUserModal = function (user) {
        //$scope.cleanUser($scope.userCreate);
        $("#modal-create").modal();
    };

    $scope.editUserModal = function (user) {
        var tempUser = new Object();
        angular.copy(user, tempUser);
        $scope.userEdit = tempUser;
        for (var i = 0; i < $scope.genderList.length; i++) {
            var gender = $scope.genderList[i];
            if (gender.value == user.genderId) {
                $scope.userEdit.selectGender = $scope.genderList[i];
                break;
            }
        }

        for (var i = 0; i < $scope.roleList.length; i++) {
            var role = $scope.roleList[i];
            if (role.id == user.roleId) {
                $scope.userEdit.selectRole = $scope.roleList[i];
                break;
            }
        }

        $("#modal-edit").modal();
    };

    $scope.deleteUserModal = function (user) {
        $scope.userDelete.username = user.username;
        $scope.userDelete.id = user.userId;
        $("#modal-delete").modal();
    };

    $scope.createUser = function (user, validate) {
        var newUser = new Object();
        newUser.username = user.username;
        newUser.password = user.password;
        newUser.realName = user.realName;
        newUser.gender = user.selectGender.value;
        newUser.role = user.selectRole.id;
        newUser.phone = user.phone;
        newUser.email = user.email;
        $http({
            method: "post",
            url: "createUser",
            headers: {
                "dataType": "json"
            },
            data: newUser
        }).then(function onSuccess(response) {
            $scope.getUser();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-create").modal('hide');
    };

    $scope.editUser = function (user, validate) {
        var newUser = new Object();
        newUser.id = user.userId;
        newUser.username = user.username;
        newUser.realName = user.realName;
        newUser.gender = user.selectGender.value;
        newUser.role = user.selectRole.id;
        newUser.phone = user.phone;
        newUser.email = user.email;
        $http({
            method: "post",
            url: "updateUser",
            headers: {
                "dataType": "json"
            },
            data: newUser
        }).then(function onSuccess(response) {
            $scope.getUser();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-edit").modal('hide');
    };

    $scope.deleteUser = function (user) {
        var delUser = new Object();
        delUser.id = user.id;
        delUser.username = user.username;
        $http({
            method: "post",
            url: "deleteUser",
            headers: {
                "dataType": "json"
            },
            data: delUser
        }).then(function onSuccess(response) {
            $scope.getUser();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-delete").modal('hide');
    };

    $scope.cleanUser = function (user) {
        user.username = "";
        user.realName = '';
        user.password = "";
        user.phone = "";
        user.email = "";
    };

    //
});