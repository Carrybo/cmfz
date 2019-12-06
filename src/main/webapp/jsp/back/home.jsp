<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>持明法舟后台管理系统</title>
    <link href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/boot/css/back.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/jqgrid/css/jquery-ui.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/boot/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/boot/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/echarts/echarts.min.js"
            charset="UTF-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/echarts/china.js " charset="UTF-8"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script>
        $(function () {
            //切换样式
            $(".list-group").on("click", ".list-group-item", function () {
                //去掉所有
                $(".list-group-item").removeClass().addClass("list-group-item");
                //点击激活
                $(this).addClass("active");
            });

        })
        KindEditor.ready(function (K) {
            window.editor = K.create('#editor_id', {
                width: '860px',
                // 1. 指定图片上传路径
                uploadJson: "${pageContext.request.contextPath}/article/uploadImg",
                allowFileManager: true,
                fileManagerJson: "${pageContext.request.contextPath}/article/showAllImges",
                afterBlur: function () {
                    this.sync();
                }
            });
        });
    </script>
</head>
<body>
<%--  导航条开始 --%>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持明法舟后台管理系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><p class="navbar-text">欢迎：${sessionScope.admin}</p></li>
                <li><a href="#">退出登录</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<%-- 栅格系统开始 --%>
<div class="container-fluid">
    <div class="container-fluid">
        <div class="row">
            <%-- 左侧菜单开始 --%>
            <div class="col-xs-2">
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                                   aria-expanded="true" aria-controls="collapseOne">
                                    用户管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse" role="tabpanel"
                             aria-labelledby="headingOne">
                            <div class="panel-body">
                                <div class="list-group">
                                    <a href="javascript:$('#content').load('./userlist.jsp')" class="list-group-item">用户信息</a>
                                    <a href="javascript:$('#content').load('./userRegistGram.jsp')"
                                       class="list-group-item">用户注册图表</a>
                                    <a href="javascript:$('#content').load('./usermap.jsp')" class="list-group-item">用户地图分布</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingTwo">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                                   href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                    上师管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel"
                             aria-labelledby="headingTwo">
                            <div class="panel-body">
                                <div class="list-group">
                                    <a href="javascript:$('#content').load('./gurulist.jsp')" class="list-group-item">上师信息</a>
                                    <a href="#" class="list-group-item">添加上师</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingThree">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                                   href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                    文章管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel"
                             aria-labelledby="headingThree">
                            <div class="panel-body">
                                <div class="list-group">
                                    <a href="javascript:$('#content').load('./articlelist.jsp')"
                                       class="list-group-item">文章信息</a>
                                    <a href="#" class="list-group-item">添加文章</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingFour">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                                   href="#collapseFour" aria-expanded="false" aria-controls="collapseThree">
                                    专辑管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFour" class="panel-collapse collapse" role="tabpanel"
                             aria-labelledby="headingFour">
                            <div class="panel-body">
                                <div class="list-group">
                                    <a href="javascript:$('#content').load('./albumlist.jsp')" class="list-group-item">专辑信息</a>
                                    <a href="#" class="list-group-item">添加专辑</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingFive">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                                   href="#collapseFive" aria-expanded="false" aria-controls="collapseThree">
                                    轮播图管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFive" class="panel-collapse collapse" role="tabpanel"
                             aria-labelledby="headingFive">
                            <div class="panel-body">
                                <div class="list-group">
                                    <a href="javascript:$('#content').load('./bannerlist.jsp')" class="list-group-item">图片信息</a>
                                    <a href="#" class="list-group-item">添加图片</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%-- 右侧巨幕 --%>
            <div class="col-xs-10" id="content">
                <div class="well">
                    <h2>欢迎使用持明法舟后台管理系统！</h2>
                </div>

                <%-- 轮播图开始 --%>
                <div id="myCarousel" class="carousel slide">
                    <!-- 轮播（Carousel）指标 -->
                    <ol class="carousel-indicators">
                        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                        <li data-target="#myCarousel" data-slide-to="1"></li>
                        <li data-target="#myCarousel" data-slide-to="2"></li>
                    </ol>
                    <!-- 轮播（Carousel）项目 -->
                    <div class="carousel-inner" style="height: 450px">
                        <div class="item active">
                            <img src="${pageContext.request.contextPath}/img/1.jpg" alt="First slide">
                        </div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/img/2.jpg" alt="Second slide">
                        </div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/img/3.jpg" alt="Third slide">
                        </div>
                    </div>
                    <!-- 轮播（Carousel）导航 -->
                    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>

        </div>
    </div>
</div>
<%-- 页脚开始 --%>

<%-- 模态框表单 --%>
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">添加文章</h4>
            </div>
            <div class="modal-body">
                <form role="form" enctype="multipart/form-data" id="myForm">
                    <div class="form-group">
                        <label>标题</label>
                        <input type="text" class="form-control" id="title" placeholder="请输入标题">
                    </div>
                    <div class="form-group">
                        <label for="inputfile">封面</label>
                        <input type="file" id="inputfile" name="inputfile">
                        <p class="help-block">这里是上传文章的封面图片</p>
                    </div>
                    <div class="form-group">
                        <label>所属上师</label>
                        <select class="form-control" id="guruList" name="guruId">

                        </select>
                    </div>
                    <div class="form-group">
                        <label>状态</label>
                        <select class="form-control" id="status" name="guruId">
                            <option value="1" id="op1">展示</option>
                            <option value="0" id="op2">冻结</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="editor_id">内容</label>
                        <textarea id="editor_id" name="content" style="width:700px;height:300px;">
                            &lt;strong&gt;HTML内容&lt;/strong&gt;
                        </textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer" id="modal_foot">
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>
