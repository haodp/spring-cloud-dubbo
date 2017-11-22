var app = angular.module('mainApp', []);

app.controller('mainCtrl', function ($scope, $http) {
    $scope.massgae = "提示信息";
    $scope.permissionList = [];
    $scope.menuList = [];
    $scope.permCreate = [];
    $scope.permView = [];
    $scope.permEdit = [];
    $scope.permDelete = [];
    $scope.selectPerm = "";
    $scope.permission = "";

    //分页相关参数
    $scope.pageSize = 4;//每页显示记录个数
    $scope.pageNo = 1;//选中页数
    $scope.totalPage = 1;//总页数
    $scope.totalCount = 1;//记录总数
    //每页显示记录数
    $scope.pageSizeList = [1, 2, 3, 4, 5];
    //分页参数结束

    resetPage = function () {
        var pageNo = parseInt($scope.pageNo);
        var totalPages = parseInt($scope.totalPage);

        $('.pagination').jqPaginator('option', {
            currentPage: pageNo,
            totalPages: totalPages
        });
    };

    $scope.changePageSize = function (pageSize) {
        $scope.pageNo = 1;
        $scope.getPermission();
        resetPage();

    };

    $scope.getMenuList = function () {
        var data = {};
        $http({
            method: "post",
            url: "getAllMenuList",
            headers: {
                "dataType": "json"
            },
            data: data
        }).then(function onSuccess(response) {
            $scope.menuList = response.data.data.menuList;
            $scope.permCreate.selectMenu = $scope.menuList[0];
        }).catch(function onError(response) {

        });
    };

    $scope.getPermission = function () {

        var data = {
            pageSize: $scope.pageSize,
            pageNo: $scope.pageNo
        }

        $http({
            method: "post",
            url: "getPermissionList",
            headers: {
                "dataType": "json"
            },
            data: data
        }).then(function onSuccess(response) {
            $scope.permissionList = response.data.data.permissionList;
            $scope.totalPage = response.data.data.totalPage;//总页数
            $scope.totalCount = response.data.data.totalCount;//总条数
            resetPage();
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
                $scope.getPermission();
            }
        });

    //$scope.getRole();

    $('#modal-edit th input:checkbox').on('click', function () {
        var that = this;
        $(this).closest('table').find('tr > td:first-child input:checkbox')
            .each(function () {
                this.checked = that.checked;
                $(this).closest('tr').toggleClass('selected');
            });

    });

    $scope.viewModal = function (role) {
        $scope.roleView = role;
        $("#modal-view").modal();
    };

    $scope.createModal = function (perm) {
        //$scope.cleanUser($scope.userCreate);
        $("#modal-create").modal();
    };

    $scope.editModal = function (perm) {
        $scope.selectPerm = perm;
        var tempPerm = new Object();
        angular.copy(perm, tempPerm);
        $scope.permEdit = tempPerm;
        $("#modal-edit").modal();
    };

    $scope.deleteModal = function (perm) {
        $scope.permDelete = perm;
        $("#modal-delete").modal();
    };

    $scope.createPerm = function (perm, validate) {
        var newPerm = new Object();
        newPerm.menuId = $scope.permCreate.selectMenu.menuId;
        newPerm.permname = $scope.permCreate.permname;
        newPerm.action = $scope.permCreate.action;
        $http({
            method: "post",
            url: "createPermission",
            headers: {
                "dataType": "json"
            },
            data: newPerm
        }).then(function onSuccess(response) {
            $scope.getPermission();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-create").modal('hide');
    };

    $scope.updatePerm = function (perm, validate) {
        var updatePerm = new Object();
        updatePerm.id = perm.id;
        updatePerm.permname = perm.permname;
        updatePerm.action = perm.action;
        $http({
            method: "post",
            url: "updatePermission",
            headers: {
                "dataType": "json"
            },
            data: updatePerm
        }).then(function onSuccess(response) {
            $scope.getPermission();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-edit").modal('hide');
    };

    $scope.deletePerm = function (perm) {
        var delPerm = new Object();
        delPerm.id = perm.id;
        $http({
            method: "post",
            url: "deletePermission",
            headers: {
                "dataType": "json"
            },
            data: delPerm
        }).then(function onSuccess(response) {
            $scope.getPermission();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-delete").modal('hide');
    };

    $scope.getMenuList();
    //
});