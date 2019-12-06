<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<script type="text/javascript">
    $(function () {
        // 基于准备好的dom，初始化echarts实例
        var mapChar = echarts.init(document.getElementById('userMap'));
        var option = {
            title: {
                text: '用户分布图',
                subtext: '纯属虚构',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['省份']
            },
            visualMap: {
                left: 'left',
                top: 'bottom',
                text: ['高', '低'],           // 文本，默认为数值文本
                calculable: true
            },
            toolbox: {
                show: true,
                orient: 'vertical',
                left: 'right',
                top: 'center',
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            series: [{
                name: '省份',
                type: 'map',
                mapType: 'china',
                roam: false,
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                data: []
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        mapChar.setOption(option);
        // 使用ajax请求获取数据
        $.post("${pageContext.request.contextPath}/map/getAll", function (request) {
            alert(request)
            mapChar.setOption({
                series: [
                    {
                        name: '省份',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: request
                    }
                ]
            })
        }, 'json');
    })

</script>
</head>

<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="userMap" style="width: 600px;height:400px;"></div>

</body>
</html>