<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>H+ 后台主题UI框架 - FooTable</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

    <link rel="shortcut icon" href="favicon.ico"> <link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="css/plugins/footable/footable.core.css" rel="stylesheet">

    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
          <div class="row">
            <div class="col-sm-12">
                <div class="col-sm-15">
                	<div class="ibox"> 
                		<div class="ibox-title">
                        	<h5>数据</h5>
                    	</div>
                		<div class="ibox-content">
                        	<table class="footable table table-stripped" data-page-size="8" id="tablePos" data-filter=#filter>   
                        	</table>
                        </div>
                    </div>
               </div>
            </div>
            
		  </div>
        </div>
    <!-- 全局js -->
    <script src="js/jquery.min.js?v=2.1.4"></script>
    <script src="js/bootstrap.min.js?v=3.3.6"></script>
    <script src="js/plugins/footable/footable.all.min.js"></script>
	<script src="js/tool.js"></script>
    <!-- 自定义js -->
    <script src="js/content.js?v=1.0.0"></script>
    <script>
        $(document).ready(function() {
        	jsonAjax("<c:url value='/platform/showMsg'/>","","json",refresh);
        });
        function refresh(data){
        	var tableStr = "<table class=\"footable table table-stripped toggle-arrow-tiny\" data-page-size=\"8\">";
        	tableStr = tableStr + "<thead><tr><th>标题</th><th>描述</th><th>图片url</th><th>正文url</th><th>短信类型</th></tr></thead><tbody>";
        	for(var i =0; i<data.length ; i++){
        		tableStr = tableStr 
        					+"<tr><td>"+data[i].title
        					+"</td><td>"+data[i].desc
        					+"</td><td>"+data[i].picUrl
        					+"</td><td>"+data[i].url
        					+"</td><td>"+data[i].type
        					+"</td><td><a href=\"#\" onclick=\"test(this);\"><i class=\"fa fa-close text-navy\"></i> 删除</a></td></tr>";
        	}        	
        	tableStr = tableStr + "</tbody><tfoot><tr><td colspan=\"8\"><ul class=\"pagination pull-right\"></ul></td></tr></tfoot></table>";
        	$("#tablePos").html(tableStr);  
            $('.footable').footable();
            
        }
        function test(obj){
        	/* alert($(obj).closest('tr').find('td')[0].innerText);
        	alert($(obj).closest('tr').find('td')[1].innerText);
        	alert($(obj).closest('tr').find('td')[2].innerText);
        	alert($(obj).closest('tr').find('td')[3].innerText); */
        	parent.layer.confirm('是否确定删除该信息内容？', {
        	    btn: ['是','否'], //按钮
        	    shade: false //不显示遮罩
        	}, function(){
        		del(obj);
        	}, function(){
        	    parent.layer.msg('取消删除', {icon: 2,
        	    								shade: [0.3, '#393D49'],
												success: function(layero, index) {
													window.location.reload();
												}});
        	});
        }
		function del(obj){
        	var param = {
        			title:$(obj).closest('tr').find('td')[0].innerText,
        			desc:$(obj).closest('tr').find('td')[1].innerText,
        			picUrl:$(obj).closest('tr').find('td')[2].innerText,
        			url:$(obj).closest('tr').find('td')[3].innerText,
        			type:$(obj).closest('tr').find('td')[4].innerText
        		};
        	jsonAjax("<c:url value='/platform/delMsg'/>",param,"json",succDel);
		}
		function succDel(){
			parent.layer.msg('删除成功', {icon: 1,shade: [0.3, '#393D49'],
											success: function(layero, index) {
												window.location.reload();
											}});
			window.location.reload();
		}
    </script>

    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
    <!--统计代码，可删除-->

</body>

</html>
