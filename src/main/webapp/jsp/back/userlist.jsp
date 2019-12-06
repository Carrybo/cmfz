<%@page pageEncoding="UTF-8" contentType="text/html;UTF-8" isELIgnored="false" %>
<script type="text/javascript">
    $(function () {
        $("#userTable").jqGrid(
            {
                height: 200,
                url: "${pageContext.request.contextPath}/user/findAll",
                datatype: "json",
                colNames: ['ID', '姓名', '电话', '昵称', '性别', '个性签名', '地址', '头像', '注册时间', '状态', '操作'],
                colModel: [
                    {name: 'id', hidden: true},
                    {name: 'name', align: "center"},
                    {name: 'tel', align: "center"},
                    {name: 'nickName', align: "center"},
                    {name: 'sex', align: "center"},
                    {name: 'sign', align: "center"},
                    {name: 'address', align: "center"},
                    {
                        name: 'photo', align: "center", formatter: function (data) {
                            return "<img style='width: 50px' src='" + data + "'/>"
                        }
                    },
                    {name: 'registDate', align: "center"},
                    {name: 'status', align: "center"},
                    {
                        name: 'options', align: "center", formatter: function (cellvalue, options, rowObject) {
                            return "<butten class='btn btn-danger' onclick=\"updateStatus('" + rowObject.id + "')\">修改状态</butten>";
                        }
                    }
                ],
                rowNum: 4,
                rowList: [4, 8, 10],
                pager: '#userPage',
                mtype: "post",
                viewrecords: true,
                styleUI: "Bootstrap",
                autowidth: true,
                editurl: "${pageContext.request.contextPath}/banner/edit"
            });
        $("#userTable").jqGrid('navGrid', '#userPage',
            {
                edit: false, add: false, del: false,
            }
        );
    });

    function updateStatus(userId) {
        $.post("${pageContext.request.contextPath}/user/updateStatus", "userId=" + userId, function (request) {
            alert(request.status);
            $("#userTable").trigger("reloadGrid");
        });
    }
</script>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">用户管理</a>
        </div>
    </div>
</nav>
<ul class="nav nav-tabs">
    <li class="active">
        <a href="#">用户信息</a></li>
    <li>
</ul>
<div>
    <table id="userTable"></table>
    <div id="userPage"></div>
</div>

