var app = angular.module('mainApp', []);

app.controller('mainCtrl', function ($scope, $http) {
    $scope.massgae = "提示信息";
    $scope.roleList = [];
    $scope.roleMenuList = [];
    $scope.roleCreate = [];
    $scope.roleView = [];
    $scope.roleEdit = [];
    $scope.roleDelete = [];
    $scope.selectRole = "";
    $scope.role = "";

    //分页相关参数
    $scope.pageSize = 4;//每页显示记录个数
    $scope.pageNo = 1;//选中页数
    $scope.totalPage = 1;//总页数
    $scope.totalCount = 1;//记录总数
    //每页显示记录数
    $scope.pageSizeList = [1,2,3,4,5];
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
        $scope.getRole();
        resetPage();

    };
    $scope.getRole = function (role) {

        var data = {
            pageSize: $scope.pageSize,
            pageNo: $scope.pageNo
        }

        $http({
            method: "post",
            url: "getRoleList",
            headers: {
                "dataType": "json"
            },
            data: data
        }).then(function onSuccess(response) {
            $scope.roleList = response.data.data.roleList;
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
                $scope.getRole();
            }
        });

    //$scope.getRole();

    $('#modal-edit th input:checkbox').on('click' , function(){
        var that = this;
        $(this).closest('table').find('tr > td:first-child input:checkbox')
            .each(function(){
                this.checked = that.checked;
                $(this).closest('tr').toggleClass('selected');
            });

    });

    $scope.viewModal = function (role) {
        $scope.roleView = role;
        $("#modal-view").modal();
    };

    $scope.createModal = function (role) {
        //$scope.cleanUser($scope.userCreate);
        $("#modal-create").modal();
    };

    $scope.editModal = function (role) {
        $scope.selectRole = role;
        $scope.roleMenuList = role.roleMenuList;

        $("#modal-edit").modal();
    };

    $scope.deleteModal = function (role) {
        $scope.selectRole = role;
        // $scope.roleDelete.username = role.username;
        // $scope.userDelete.id = user.userId;
        $("#modal-delete").modal();
    };

    $scope.createRole = function (role, validate) {
        var newRole = new Object();
        newRole.rolename = role.roleName;
        $http({
            method: "post",
            url: "createRole",
            headers: {
                "dataType": "json"
            },
            data: newRole
        }).then(function onSuccess(response) {
            $scope.getRole();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-create").modal('hide');
    };

    $scope.updateRoleMenu = function () {
        var tempMenuList = [];
        angular.copy($scope.roleMenuList, tempMenuList);
        var roleMenu = new Object();
        roleMenu.roleId = $scope.selectRole.id;
        roleMenu.menuList = tempMenuList;
        $http({
            method: "post",
            url: "updateRoleMenu",
            headers: {
                "dataType": "json"
            },
            data: roleMenu
        }).then(function onSuccess(response) {
            $scope.getRole();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-edit").modal('hide');
    };

    $scope.deleteRole = function (role) {
        var delRole = new Object();
        delRole.id = role.id;
        $http({
            method: "post",
            url: "deleteRole",
            headers: {
                "dataType": "json"
            },
            data: delRole
        }).then(function onSuccess(response) {
            $scope.getRole();
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