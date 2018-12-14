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
                            <form role="form" method="post" action="/weixin_sell/seller/category/save">
                                <div class="form-group">
                                    <label>名称</label>
                                    <input name="categoryName" type="text" class="form-control" value="${(productCategory.categoryName)!''}"/>
                                </div>
                                <div class="form-group">
                                    <label>类目编号</label>
                                    <input name="categoryType" type="number" class="form-control" value="${(productCategory.categoryType)!''}"/>
                                </div>
                                <input hidden type="text" name="categoryId" value="${(productCategory.categoryId)!''}">
                                <button type="submit" class="btn btn-default">保存</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
