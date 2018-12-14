<html>
    <#include "../common/header.ftl">
    <body>
        <div id="wrapper" class="toggled">
            <#--边栏sidebar-->
            <#include "../common/nav.ftl">
            <#--主要内容-->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>类目id</th>
                                    <th>名称</th>
                                    <th>类目编号</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                            <#list productCategoryList as productCategory>
                            <tr>
                                <td>${productCategory.categoryId}</td>
                                <td>${productCategory.categoryName}</td>
                                <td>${productCategory.categoryType}</td>
                                <td>${productCategory.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                                <td>${productCategory.updateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                                <td><a href="/weixin_sell/seller/category/index?categoryId=${productCategory.categoryId}">修改</a></td>
                            </tr>
                            </#list>
                                </tbody>
                            </table>
                        </div>

                    <#--分页-->
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
