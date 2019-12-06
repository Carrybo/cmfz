<%@page pageEncoding="UTF-8" contentType="text/html;UTF-8" isELIgnored="false" %>
<script type="text/javascript">
    $(function () {
        $("#guruTable").jqGrid(
            {
                height: 200,
                url: "${pageContext.request.contextPath}/guru/showAllGurus",
                datatype: "json",
                colNames: ['ID', '姓名', '法号', '头像', '状态'],
                colModel: [
                    {name: 'id', hidden: true},
                    {name: 'name', align: "center", editable: true, editoptions: {required: true}},
                    {name: 'nickName', align: "center", editable: true, editoptions: {required: true}},
                    {
                        name: 'photo', align: "center", formatter: function (data) {
                            return "<img style='width: 50px' src='" + data + "'/>"
                        }, editable: true, edittype: "file", editoptions: {enctype: "mutipart/form-data"}
                    },
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
                pager: '#guruPage',
                mtype: "post",
                viewrecords: true,
                styleUI: "Bootstrap",
                autowidth: true,
                multiselect: true,
                editurl: "${pageContext.request.contextPath}/guru/edit"
            });
        $("#guruTable").jqGrid('navGrid', '#guruPage',
            {
                edit: true, add: true, del: true,
                edittext: "编辑", addtext: "添加", deltext: "删除"
            },
            {
                closeAfterEdit: true,
                beforeShowForm: function (frm) {
                    frm.find("#photo").attr("disabled", true);
                }
            }, {
                closeAfterAdd: true,
                reloadAfterSubmit: true,
                afterSubmit: function (response, postData) {
                    var guruId = response.responseJSON.guruId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/guru/upload",
                        dataType: "json",
                        type: "post",
                        data: {guruId: guruId},
                        fileElementId: "photo",
                        success: function (data) {

                            // 重新载入表格
                            $("#guruTable").trigger("reloadGrid");
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
    <table id="guruTable"></table>
    <div id="guruPage"></div>
</div>

