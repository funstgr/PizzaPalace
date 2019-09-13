<%@ include file="../include/include.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/createCustomPizza.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/loadToppings.js"></script>

<input type="text" id="customPizzaName">
<input type="hidden" id="pizzaId">

<table id="toppingTableList" class="toppingTable" >
	
	<tr><td colspan=9><hr></td></tr>	
	
</table>
<hr>
<div class="price" >Price: <span id="customPizzaPrice"></span></div>
<br><br>
<hr>
<input type="button" onclick="createCustomPizza();" value="Create Custom Pizza">
<hr>

<div id="dialogWarning">
	
</div>
<form id="reloadApplicationForm" method="post" action="${pageContext.request.contextPath}">
				
 </form>