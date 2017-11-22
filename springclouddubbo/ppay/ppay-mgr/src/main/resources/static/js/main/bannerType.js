var app = angular.module('mainApp', []);

app.controller('mainCtrl', function ($scope, $http) {
    $scope.massgae = "提示信息";
    $scope.dataList = [];  // 主画面检索结果集合
    $scope.dataEdit = [];  //编辑画面的结果集合
    $scope.dataCreate = [];  //新建画面的结果集合
    $scope.dataView = [];  //预览画面的结果集合
    $scope.dataDelete = [];  //删除画面的结果集合

    //分页相关参数
    $scope.pageSize = 50;//每页显示记录个数
    $scope.pageNo = 1;//选中页数
    $scope.totalPage = 1;//总页数
    $scope.totalCount = 1;//记录总数
    //每页显示记录数
    $scope.pageSizeList = [50,100,150,200];
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
        $scope.getBannerType();
        resetPage();

    };

    // 修改TODO getBannerType --> 变换成自己的方法
    // 主画面的一览List取得,初期化以及查询按钮
    $scope.getBannerType = function () {

        var data = {
            pageSize: $scope.pageSize,
            pageNo: $scope.pageNo,
            bannerTypeNameSeach:$("input[name='bannerTypeNameSeach']").val()  //查询条件
        }

        $http({
            method: "post",
            url: "bannerType/search",
            headers: {
                "dataType": "json"
            },
            data: data
        }).then(function onSuccess(response) {
        	//修改TODO response.data 数据集-->数据集中的字段（data）
        	var obj = angular.fromJson(response.data.data);
            $scope.dataList = obj.voList;
            $scope.totalPage = obj.totalPage;//总页数
            $scope.totalCount = obj.totalCount;//总条数

            //查看行的结果集合
            $scope.dataCreate = response.data.data[0];
            resetPage();
        }).catch(function onError(response) {
        	// 修改TODO getMenu --> getBannerType
        	console.log('bannerTypeList.js的BannerType: 执行错误  ');
        });
    };

    // 分页控件，不用做任何处理   禁止修改
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
                $scope.getBannerType();
            }
        });

    // 一览中存在checkbox，点击头部的checkbox，一览全部选中。
    // 写法参考附件,功能附件
    $('#main-modal-list th input:checkbox').on('click' , function(){
        var that = this;
        $(this).closest('table').find('tr > td:first-child input:checkbox')
            .each(function(){
                this.checked = that.checked;
                $(this).closest('tr').toggleClass('selected');
            });

    });

    // 打开预览查看画面
    $scope.viewModal = function (beanRow) {
        $scope.dataView = beanRow;
        $("#modal-view").modal();
    };

    // 打开新建画面
    $scope.createModal = function (beanRow) {
        $("#modal-create").modal();
    };

    // 打开编辑画面
    $scope.editModal = function (beanRow) {
    	// 设定画面值
    	$scope.dataEdit = beanRow;
        $("#modal-edit").modal();
    };

    // 打开删除画面
    $scope.deleteModal = function (beanRow) {
        $scope.dataDelete = beanRow;
        $("#modal-delete").modal();
    };

    // 打开批量删除画面
    $scope.deleteBatchModal = function () {
        $("#modal-delete-rows").modal();
    };


    // 添加画面的添加处理功能
    $scope.createRow = function (beanRow, validate) {
        var newRow = new Object();
        newRow = beanRow;
        $http({
            method: "post",
            url: "bannerType/create",
            headers: {
                "dataType": "json"
            },
            data: newRow
        }).then(function onSuccess(response) {
            $scope.getBannerType();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-create").modal('hide');
    };

    // 更新画面更新处理功能
    $scope.updateRow = function (beanRow, validate) {
        $http({
            method: "post",
            url: "bannerType/update",
            headers: {
                "dataType": "json"
            },
            data: beanRow
        }).then(function onSuccess(response) {
            $scope.getBannerType();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-edit").modal('hide');
    };

    // 删除画面的删除处理功能
    $scope.deleteRow = function (beanRow) {
        $http({
            method: "post",
            url: "bannerType/delete",
            headers: {
                "dataType": "json"
            },
            data: beanRow
        }).then(function onSuccess(response) {
            $scope.getBannerType();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-delete").modal('hide');
    };

    // 批量删除处理
    $scope.deleteBatchRows = function () {

        //objs：需要遍历的集合
        //data:遍历时当前的数据
        //index:遍历时当前索引
        //array:需要遍历的集合，每次遍历时都会把objs原样的传一次。
    	var requestParam = "";
        angular.forEach($scope.dataList, function(data,index,array){
        	//data等价于array[index]
        	if(data.checked){
        		requestParam = requestParam + data.id + ",";
        	}
        });

        $http({
            method: "post",
            url: "bannerType/deleteBatchRows",
            headers: {
                "dataType": "json"
            },
            data: requestParam
        }).then(function onSuccess(response) {
            $scope.getBannerType();
            $("#warning-msg").fadeIn();
            window.setTimeout(function () {
                $("#warning-msg").fadeOut();
            }, 1500);
        }).catch(function onError(response) {

        });
        $("#modal-delete-rows").modal('hide');
    };

    //
});