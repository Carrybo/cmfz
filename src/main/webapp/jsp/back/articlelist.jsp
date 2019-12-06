<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    $(function () {
        // 创建JqGrid表格
        $("#articleTable").jqGrid(
            {
                url: "${pageContext.request.contextPath}/article/showAllArticle",
                datatype: "json",
                height: 200,
                colNames: ['id', '标题', '状态', '封面', '作者', '发布日期', '操作'],
                colModel: [
                    {name: 'id', hidden: true},
                    {name: 'title', align: "center", editable: true},
                    {
                        name: 'status', align: "center", formatter: function (data) {
                            if (data == "1") {
                                return "展示";
                            } else return "冻结";
                        }
                    },
                    {
                        name: 'articleImg', align: "center", editable: true, formatter: function (value, options, row) {
                            return "<img style='width: 50px' src='" + row.photo + "'/>"
                        }, editable: true, edittype: "file", editoptions: {enctype: "mutipart/form-data"}
                    },
                    {name: 'author', align: "center", editable: true},
                    {name: 'publishDate', align: "center", editable: true},
                    {
                        name: 'option', align: "center", formatter: function (cellvalue, options, rowObject) {
                            return "<a href='javascript:void(0)'><span class='glyphicon glyphicon-th-list' onclick=\"openModal('" + rowObject.id + "')\"></span></a>";
                        }
                    },
                ],
                rowNum: 4,
                rowList: [4, 8, 10],
                pager: '#articlePage',
                viewrecords: true,
                multiselect: false,
                autowidth: true,
                styleUI: "Bootstrap",
                editurl: "${pageContext.request.contextPath}/article/edit"
            });
        $("#articleTable").jqGrid('navGrid', '#articlePage', {
            edit: false, add: false, del: true, search: false,
            deltext: "删除"
        });
    })

    // 打开模态框
    function openModal(articleId) {
        // 清楚表单内数据
        $("#myForm")[0].reset();
        // 获取所属上师
        $.post('${pageContext.request.contextPath}/guru/getAllGuru', function (request) {
            var option = "<option value='0'>通用文章</option>";
            request.forEach(function (guru) {
                option += "<option value='" + guru.id + "'>" + guru.nickName + "</option>";
            })
            $("#guruList").html(option);
        }, 'json');
        if (typeof (articleId) != "undefined") {
            // 获取更新文章信息
            $.post('${pageContext.request.contextPath}/article/findOne', 'id=' + articleId, function (request) {
                // kindeditor 提供数据回显方法
                KindEditor.html("#editor_id", request.content);
                // 标题回显
                $("#title").val(request.title);
                // 状态回显
                $("#status").val(request.status);
                // 上师回显
                $("#guruList").val(request.guruId);
                // 同步editor文本域数据
                editor.sync();
            })
            $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>" +
                "<button type=\"button\" class=\"btn btn-primary\" onclick=\"updateArticle('" + articleId + "')\">修改</button>")
        } else {
            // kindeditor 提供数据回显方法 通过""设置空串
            KindEditor.html("#editor_id", "");
            $("#modal_foot").html("<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>" +
                "<button type=\"button\" class=\"btn btn-primary\" onclick=\"addArticle()\">添加</button>")
        }
        $("#myModal").modal("show");
    }

    // 添加文章
    function addArticle() {
        $.ajaxFileUpload({
                url: "${pageContext.request.contextPath}/article/addArticle",
                dataType: "json",
                type: "post",
                fileElementId: "inputfile",
                data: {
                    title: $("#title").val(),
                    status: $("#status").val(),
                    guruId: $("#guruList").val(),
                    content: $("#editor_id").val()
                },
                success: function (data) {
                    alert(data.status)
                    $("#articleTable").trigger("reloadGrid");
                }
            }
        )
        // 隐藏模态框
        $("#myModal").modal("hide");
    }

    // 更新文章
    function updateArticle(articleId) {
        $.ajaxFileUpload({
                url: "${pageContext.request.contextPath}/article/addArticle",
                dataType: "json",
                type: "post",
                fileElementId: "inputfile",
                data: {
                    id: articleId,
                    title: $("#title").val(),
                    status: $("#status").val(),
                    guruId: $("#guruList").val(),
                    content: $("#editor_id").val()
                },
                success: function (data) {
                    alert(data.status)
                    $("#articleTable").trigger("reloadGrid");
                }
            }
        )
        // 隐藏模态框
        $("#myModal").modal("hide");
    }

</script>
<div class="panel-header">
    <h4>文章管理</h4>
</div>
<ul id="myTab" class="nav nav-tabs">
    <li class="active">
        <a href="#home" data-toggle="tab">
            文章列表
        </a>
    </li>
    <li><a href="javascript:void(0);" onclick="openModal()">添加文章</a></li>
</ul>
<div id="myTabContent" class="tab-content">
    <div class="tab-pane fade in active" id="home">
        <table id="articleTable"></table>
        <div id="articlePage"></div>
    </div>
</div>

