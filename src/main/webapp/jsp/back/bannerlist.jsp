<%@page pageEncoding="UTF-8" contentType="text/html;UTF-8" isELIgnored="false" %>
<script type="text/javascript">
    $(function () {
        $("#bannerInfo").jqGrid(
            {
                height: 200,
                url: "${pageContext.request.contextPath}/banner/showAllBanners",
                datatype: "json",
                colNames: ['ID', '标题', '图片', '超链接', '创建时间', '描述', '状态'],
                colModel: [
                    {name: 'id', hidden: true},
                    {name: 'title', align: "center", editable: true, editoptions: {required: true}},
                    {
                        name: 'url', align: "center", formatter: function (data) {
                            return "<img style='width: 50px' src='" + data + "'/>"
                        }, editable: true, edittype: "file", editoptions: {enctype: "mutipart/form-data"}
                    },
                    {name: 'href', align: "center", editable: true, editoptions: {required: true}},
                    {name: 'createDate', align: "center"},
                    {name: 'description', align: "center", editable: true, editoptions: {required: true}},
                    {
                        name: 'status', align: "center", formatter: function (data) {
                            if (data == "1") {
                                return "展示";
                            } else return "冻结";
                        }, editable: true, edittype: "select", editoptions: {value: "1:展示;2:冻结"}
                    }
                ],
                rowNum: 4,
                rowList: [4, 8, 10],
                pager: '#bannerPage',
                mtype: "post",
                viewrecords: true,
                styleUI: "Bootstrap",
                autowidth: true,
                multiselect: true,
                editurl: "${pageContext.request.contextPath}/banner/edit"
            });
        $("#bannerInfo").jqGrid('navGrid', '#bannerPage',
            {
                edit: true, add: true, del: true,
                edittext: "编辑", addtext: "添加", deltext: "删除"
            },
            {
                closeAfterEdit: true,
                beforeShowForm: function (frm) {
                    frm.find("#url").attr("disabled", true);
                }
            }, {
                closeAfterAdd: true,
                reloadAfterSubmit: true,
                afterSubmit: function (response, postData) {
                    var bannerId = response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/banner/upload",
                        dataType: "json",
                        type: "post",
                        data: {bannerId: bannerId},
                        fileElementId: "url",
                        success: function (data) {

                            // 重新载入表格
                            $("#bannerInfo").trigger("reloadGrid");
                            // 上传成功
                            alert("添加成功！");
                        }
                    });
                    return postData;
                }
            }, {
                closeAfterDel: true
            }
        );
    });
</script>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">轮播图管理</a>
        </div>
    </div>
</nav>
<ul class="nav nav-tabs">
    <li class="active">
        <a href="#">轮播图信息</a></li>
    <li>
</ul>
<div>
    <table id="bannerInfo"></table>
    <div id="bannerPage"></div>
</div>

