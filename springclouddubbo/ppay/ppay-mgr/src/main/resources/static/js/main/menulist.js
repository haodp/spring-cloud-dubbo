var app = angular.module('mainApp', []);

app.controller('mainCtrl', function ($scope, $http) {
    $scope.massgae = "提示信息";
    $scope.menuList = [];
    $scope.parentMenuList = [];
    $scope.menuCreate = [];
    $scope.menuView = [];
    $scope.menuEdit = [];
    $scope.menuDelete = [];
    $scope.selectMenu = "";
    $scope.menu = "";

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
        $scope.getMenu();
        resetPage();

    };
    $scope.getParentMenu = function () {
        var data = {};
        $http({
            method: "post",
            url: "getAllMenuList",
            headers: {
                "dataType": "json"
            },
            data: data
        }).then(function onSuccess(response) {
            $scope.parentMenuList = response.data.data.menuList;
            var nonParentMenu = {
                menuId: 0,
                menuname: "无父菜单"
            }
            $scope.parentMenuList.unshift(nonParentMenu);
            $scope.menuCreate.selectParentMenu = $scope.parentMenuList[0];
        }).catch(function onError(response) {

        });
    };

    $scope.getMenu = function () {

        var data = {
            pageSize: $scope.pageSize,
            pageNo: $scope.pageNo
        }

        $http({
            method: "post",
            url: "getMenuList",
            headers: {
                "dataType": "json"
            },
            data: data
        }).then(function onSuccess(response) {
            $scope.menuList = response.data.data.menuList;
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
                $scope.getMenu();
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

    $scope.createModal = function (role) {
        //$scope.cleanUser($scope.userCreate);
        $("#modal-create").modal();
    };

    $scope.editModal = function (menu) {
        $scope.selectMenu = menu;
        var tempMenu = new Object();
        angular.copy(menu, tempMenu);
        $scope.menuEdit = tempMenu;
        $("#modal-edit").modal();
    };

    $scope.deleteModal = function (menu) {
        $scope.menuDelete = menu;
        $("#modal-delete").modal();
    };

    $scope.createMenu = function (menu, validate) {
        var newMenu = new Object();
        newMenu.parentId = $scope.menuCreate.selectParentMenu.menuId;
        newMenu.menuname = $scope.menuCreate.menuname;
        newMenu.action = $scope.menuCreate.action;
        newMenu.sort = $scope.menuCreate.menuSort;
        $http({
            method: "post",
            url: "createMenu",
            headers: {
                "dataType": "json"
            },
            data: newMenu
        }).then(function onSuccess(response) {
            $scope.getMenu();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-create").modal('hide');
    };

    $scope.updateMenu = function (menu, validate) {
        var updateMenu = new Object();
        updateMenu.id = menu.menuId;
        updateMenu.menuname = menu.menuname;
        updateMenu.action = menu.action;
        updateMenu.sort = menu.sort;
        $http({
            method: "post",
            url: "updateMenu",
            headers: {
                "dataType": "json"
            },
            data: updateMenu
        }).then(function onSuccess(response) {
            $scope.getMenu();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-edit").modal('hide');
    };

    $scope.deleteMenu = function (menu) {
        var delMenu = new Object();
        delMenu.id = menu.menuId;
        $http({
            method: "post",
            url: "deleteMenu",
            headers: {
                "dataType": "json"
            },
            data: delMenu
        }).then(function onSuccess(response) {
            $scope.getMenu();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-delete").modal('hide');
    };

    $scope.getParentMenu();
    //
});