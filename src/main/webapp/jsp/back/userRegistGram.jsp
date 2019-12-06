<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 创建goeasy对象
    var goEasy = new GoEasy({
        host: 'hangzhou.goeasy.io',//应用所在的区域地址，杭州：hangzhou.goeasy.io，新加坡：singapore.goeasy.io
        appkey: "BS-c78bae93e1e64c09b20ef3ceab342d48",//替换为您的应用appkey
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

    // 接受goeasy消息
    goEasy.subscribe({
        channel: "cmfz",//替换为您自己的channel
        onMessage: function (message) {
            console.log(message.content);
            var map = JSON.parse(message.content)
            console.log("转换后：" + map);
            myChart.setOption({
                series: [
                    {
                        name: '男',
                        type: 'bar',
                        data: map.man
                    }, {
                        name: '女',
                        type: 'bar',
                        data: map.female
                    }
                ]
            })
        }
    })
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '用户注册信息图'
        },
        tooltip: {},
        legend: {
            data: ['男', '女']
        },
        xAxis: {
            data: ["1天", "7天", "1月", "1年"]
        },
        yAxis: {},
        series: []
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    // 使用ajax异步数据回显
    $.post("${pageContext.request.contextPath}/user/getCount", function (request) {
        console.log(request);
        myChart.setOption({
            series: [
                {
                    name: '男',
                    type: 'bar',
                    data: request.man
                }, {
                    name: '女',
                    type: 'bar',
                    data: request.female
                }
            ]
        })
    }, 'json');
</script>