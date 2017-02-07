<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page language="java" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>统计查询页</title>
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
	                <div class="ibox float-e-margins">
	                	<div class="ibox-title">
	                        <h5>用户过滤</h5>
	                    </div>
	                    <div class="ibox-content">
	                        <form  class="form-horizontal" action="<c:url value='/platform/showAll'/>" method="post">
	                            <div class="form-group">
                                            <label  class="col-sm-2 control-label">地区：</label>
					        				<div class="col-sm-8">
					            				<select class="form-control" name="area"></select>
					        				</div>
					        				
                                </div>
	                            <div class="hr-line-dashed"></div>
	                            <div class="form-group">
                                           <label class="col-sm-2 control-label">组织：</label>
					        				<div class="col-sm-8">
					            				<select class="form-control" name="org"></select>
					        				</div>
                                </div>
	                            <div class="hr-line-dashed"></div>
	                            <div class="form-group">
                                            <label class="col-sm-2 control-label">年龄：</label>
					        				<div class="col-sm-8">
					            				<select class="form-control" id="age" name="age" onchange="checkField(this.value)"></select>
					        				</div>
                                </div>
	                            <div class="hr-line-dashed"></div>
	                            <div class="form-group">
                                            <label class="col-sm-2 control-label">性别：</label>
					        				<div class="col-sm-8">
					            				<select class="form-control" id="sex" name="sex" onchange="checkField(this.value)"></select>
					        				</div>
                                </div>
	                            <div class="hr-line-dashed"></div>
	                            <div class="form-group">
	                                <div class="col-sm-4 col-sm-offset-2">
	                                    <button class="btn btn-primary"  type="submit"><strong>查  询</strong>
                        				</button>
	                                </div>
	                            </div>
	                        </form>
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

        });

        
    </script>

    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
    <!--统计代码，可删除-->

</body>

</html>
