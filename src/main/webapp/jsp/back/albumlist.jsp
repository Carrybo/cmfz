<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<script>
    $(function () {
        // 创建父级JqGrid表格
        $("#albumTable").jqGrid(
            {
                url: "${pageContext.request.contextPath}/album/findAll",
                datatype: "json",
                height: 200,
                colNames: ['id', '标题', '封面', '作者', '播音', '集数', '描述', '发布日期', '分数', '状态'],
                colModel: [
                    {name: 'id', hidden: true},
                    {name: 'title', align: "center", editable: true},
                    {
                        name: 'url', align: "center", editable: true, formatter: function (value, options, row) {
                            return "<img style='width: 50px' src='" + row.photo + "'/>"
                        }, editable: true, edittype: "file", editoptions: {enctype: "mutipart/form-data"}
                    },
                    {name: 'author', align: "center", editable: true},
                    {name: 'broadcast', align: "center", editable: true},
                    {name: 'count', align: "center"},
                    {name: 'description', align: "center", editable: true},
                    {name: 'publishDate'},
                    {name: 'score', align: "center", editable: true},
                    {
                        name: 'status', align: "center", editable: true, formatter: function (data) {
                            if (data == "1") {
                                return "展示";
                            } else return "冻结";
                        }, editable: true, edittype: "select", editoptions: {value: "1:展示;2:冻结"}
                    }
                ],
                rowNum: 4,
                rowList: [4, 8, 10],
                pager: '#albumPage',
                viewrecords: true,
                multiselect: false,
                // 开启多级表格支持
                subGrid: true,
                autowidth: true,
                styleUI: "Bootstrap",
                editurl: "${pageContext.request.contextPath}/album/edit",
                // 重写创建子表格方法
                subGridRowExpanded: function (subgrid_id, row_id) {
                    addTable(subgrid_id, row_id);
                },
                // 删除表格方法
                subGridRowColapsed: function (subgrid_id, row_id) {

                }
            });
        $("#albumTable").jqGrid('navGrid', '#albumPage', {
                edit: true, add: true, del: true, search: false,
                edittext: "编辑", addtext: "添加", deltext: "删除"
            },
            {
                closeAfterEdit: true,
                beforeShoForm: function (frm) {
                    frm.find("#url").attr("disabled", true);
                }
            }, {
                closeAfterAdd: true,
                reloadAfterSubmit: true,
                afterSubmit: function (response, postData) {
                    var albumId = response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/album/upload",
                        dataType: "json",
                        type: "post",
                        data: {albumId: albumId},
                        fileElementId: "url",
                        success: function (data) {

                            // 重新载入表格
                            $("#albumTable").trigger("reloadGrid");
                            // 上传成功
                            alert("添加成功！");
                        }
                    });
                    return postData;
                }
            }, {
                closeAfterDel: true
            });
    })

    // subgrid_id 下方空间的id  row_id 当前行id数据
    function addTable(subgrid_id, row_id) {

        // 声明子表格|工具栏id
        var subgridTable = subgrid_id + "table";
        var subgridPage = subgrid_id + "page";
        // 根据下方空间id 创建表格及工具栏
        $("#" + subgrid_id).html("<table id='" + subgridTable + "'></table><div style='height: 50px' id='" + subgridPage + "'></div>")
        // 子表格JqGrid声明
        $("#" + subgridTable).jqGrid({
            url: "${pageContext.request.contextPath}/chapter/showAllChapters?albumId=" + row_id,
            datatype: "json",
            colNames: ['id', '标题', '大小', '时长', '上传时间', '音频'],
            colModel: [
                {name: "id", align: "center", hidden: true},
                {name: "title", align: "center", editable: true},
                {name: "size", align: "center"},
                {name: "time", align: "center"},
                {name: "createDate", align: "center"},
                {
                    name: "music",
                    align: "center",
                    editable: true,
                    edittype: "file",
                    editoptions: {enctype: "mutipart/form-data"},
                    formatter: function (value, options, row) {
                        return "<a href='javascript:void(0)' onclick=\"playAudio('" + row.url + "')\" class='btn btn-xs' style='font-size: 20px'><span class='glyphicon glyphicon-play-circle'></span></a>";
                    }
                },
            ],
            rowNum: 20,
            pager: "#" + subgridPage,
            sortname: 'num',
            sortorder: "asc",
            height: '100%',
            styleUI: "Bootstrap",
            editurl: "${pageContext.request.contextPath}/chapter/edit?albumId=" + row_id,
            autowidth: true
        });
        $("#" + subgridTable).jqGrid('navGrid',
            "#" + subgridPage, {
                edit: true,
                add: true,
                del: true,
                search: false
            }, {
                closeAfterEdit: true,
                beforeShowForm: function (frm) {
                    frm.find("#music").attr("disabled", true);
                }
            }, {
                closeAfterAdd: true,
                reloadAfterSubmit: true,
                afterSubmit: function (response, postData) {
                    var chapterId = response.responseJSON.chapterId;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/chapter/upload",
                        dataType: "json",
                        type: "post",
                        data: {chapterId: chapterId},
                        fileElementId: "music",
                        success: function (data) {

                            // 重新载入表格
                            $("#" + subgridTable).trigger("reloadGrid");
                            $("#albumTable").trigger("reloadGrid");
                            // 上传成功
                            alert("添加成功！");
                        }
                    });
                    return postData;
                }
            }, {
                closeAfterDel: true,
                afterComplete: function () {
                    $("#albumTable").trigger("reloadGrid");
                }
            }
        );
    }

    // 打开模态框
    function playAudio(data) {
        $("#myModal").modal("show");
        $("#audio").attr("src", data);
    }
</script>
<div class="panel-header">
    <h4>专辑管理</h4>
</div>

<table id="albumTable"></table>
<div id="albumPage" style="height: 50px"></div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <audio src="" id="audio" controls></audio>
</div>