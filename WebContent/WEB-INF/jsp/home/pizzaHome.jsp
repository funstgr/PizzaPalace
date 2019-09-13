<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="-1">
        <%@ include file="../include/include.jsp" %>
        <%@ include file="../include/scriptsAndCss.jsp" %>
        <link href="${pageContext.request.contextPath}/resources/css/pizza.css" rel="stylesheet" type="text/css" >
        <link href="${pageContext.request.contextPath}/resources/css/topping.css" rel="stylesheet" type="text/css" >        
        <title>Welcome to Pizza Palace</title>
        
        <script type="text/javascript">
			var contextPath = '${pageContext.request.contextPath}';
		</script>
    </head>

    <body>
    	<div class="header"><h1>Pizza Palace</h1></div>
    	<div class="mainDiv">
    		<div class="lhsDiv innerDivs">
    			<h2>Pizza Choices</h2>
    			<hr>
    			<%@ include file="/WEB-INF/jsp/home/pizzaList.jsp" %>	
    		</div>
    		<div class="rhsDiv innerDivs">
    			<h2>Create a Custom Pizza</h2>
    			<hr>
    			<%@ include file="/WEB-INF/jsp/home/createCustomPizza.jsp" %>    			
    		</div>
    	</div>        
    </body>
</html>
