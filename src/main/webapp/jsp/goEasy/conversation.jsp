<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/boot/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script type="text/javascript">
        var goEasy = new GoEasy({
            host: 'hangzhou.goeasy.io',//应用所在的区域地址，杭州：hangzhou.goeasy.io，新加坡：singapore.goeasy.io
            appkey: "BC-82701683c8994633af27847b20182b4b",//替换为您的应用appkey
            forceTLS: false, //如果需要使用HTTPS/WSS，请设置为true，默认为false
            onConnected: function () {
                console.log('连接成功！')
            },
            onDisconnected: function () {
                console.log('连接断开！')
            },
            onConnectFailed: function (error) {
                console.log('连接失败或错误！')
            }
        });
        goEasy.subscribe({
            channel: "conversation",//替换为您自己的channel
            onMessage: function (message) {
                var p = "<div style='height: auto;background-color: #2aabd2'><p style='color: white'>" + message.content + "</p></div>";
                $("#dbody").append(p);
            }
        })

        $(function () {
            $("#sub").click(function () {
                var text = $("#text").val();
                var p = "<p style='text-align: right'>" + text + "</p>";
                $("#dbody").append(p);
                goEasy.publish({
                    channel: "conversation", //替换为您自己的channel
                    message: text //替换为您想要发送的消息内容
                });
                $("#text").val("");
            })
        })
    </script>
</head>
<body>
<h1 style="text-align: center">阿呆聊天室</h1>
<div id="dbody" style="height: 500px;width: 700px;border: #0f0f0f solid 1px;margin: auto"></div>
<div class="col-xs-6 col-xs-offset-4" style="margin-top: 20px">
    <div class="form-inline">
        <div class="form-group">
            <label class="sr-only">名称</label>
            <input type="text" style="width: 400px" class="form-control" id="text" placeholder="请输入名消息内容">
        </div>
        <button type="submit" class="btn btn-primary" id="sub">提交</button>
    </div>
</div>
</body>
</html>
