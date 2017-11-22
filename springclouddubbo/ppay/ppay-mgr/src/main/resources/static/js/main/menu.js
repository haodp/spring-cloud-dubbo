/**
 * 菜单展开高亮逻辑
 * Created by xinhc on 2017/4/5.
 */
$(function () {
    var currentLocation = window.location.href;
    var start = currentLocation.lastIndexOf("/") + 1;
    var link = currentLocation.substr(start, currentLocation.length);
    var linkList = $("#menuList").find("a");
    $.each(linkList, function (index, element) {
        if (($(element).attr("href").indexOf(link) != -1)) {
            var $parentNode = $(element).parent().addClass("active")
                .parent().parent().addClass("active open");
        }
    });
});
