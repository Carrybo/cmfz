<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <link href="favicon.ico" rel="shortcut icon"/>
    <link href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/bootstrap.min.js"></script>
    <script>
        function login() {
            if ($("#username").val() == "" || $("#password").val() == "" || $("#code").val() == "") {
                alert("请检查输入字段，不能输入空值！")
            } else {
                $.ajax({
                    url: "${pageContext.request.contextPath}/admin/login",
                    type: "POST",
                    datatype: "JSON",
                    data: $("#loginForm").serialize(),
                    success: function (data) {
                        if (data != null & data != "") {
                            $("#msg").html('<div class="alert alert-danger alert-dismissible" role="alert">\n' +
                                '  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>\n' +
                                '  <strong>' + data + '</strong></div>');
                            $("#username").val("");
                            $("#password").val("");
                            $("#code").val("");
                            $("#kaptcha").attr("src", "${pageContext.request.contextPath}/kaptcha/getKaptcha?d=" + new Date() * 1);
                        } else {
                            location.href = "${pageContext.request.contextPath}/jsp/back/home.jsp";
                        }
                    }

                })
            }
        }

    </script>
</head>
<body style=" background: url(${pageContext.request.contextPath}/img/login.jpg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="${pageContext.request.contextPath}/admin/login">
            <div class="modal-body" id="model-body">
                <div class="form-group">
                    <input id="username" type="text" class="form-control" placeholder="用户名" autocomplete="off"
                           name="username">
                </div>
                <div class="form-group">
                    <input id="password" type="password" class="form-control" placeholder="密码" autocomplete="off"
                           name="password">
                </div>

                <div class="form-group">
                    <img id="kaptcha" alt="验证码"
                         onclick="this.src='${pageContext.request.contextPath}/kaptcha/getKaptcha?d='+new Date()*1"
                         src="${pageContext.request.contextPath}/kaptcha/getKaptcha"/>
                </div>

                <div class="form-group">
                    <input id="code" type="text" class="form-control" placeholder="验证码" autocomplete="off" name="code">
                </div>
                <span id="msg"></span>
            </div>
            <div class="modal-footer">
                <div class="form-group">
                    <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
