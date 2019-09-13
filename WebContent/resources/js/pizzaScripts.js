function showPizza(){
	var path = '${pageContext.request.contextPath}/p/pizza';
	var request= {
	            action : "showPizza",
	            rand : Math.floor(Math.random()*100000)
	        };
	
	$('#pizza').load(path,  request,   function(data, status, xhr){						
	            if(xhr)
	            {
	            	
	            }
			}, "json");		
}

