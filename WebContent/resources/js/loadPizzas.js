
function loadPizzas(){
	
	var path = contextPath + '/pizzas';	
	var request= { };
	
	$.get(path,  request,   function(data, status, xhr){						
        if(xhr)
        {
        	loadPizzaList(data.pizzas);
        }
	}, "json");		
}

function loadPizzaList(pizzas){	
	$(pizzas).each(function(index,element){		
		$("#pizzaTableList").append(
			'<tr><td>' + element.id + '</td><td><a href="javascript:void(0);" onclick="loadPizza(' 
			+ element.id + ');" >' + element.name 
			+ '</a></td><td class="price">$' + formatNo(element.price) + '</td></tr>'
		);
	});	
}

function formatNo(price){
	price = parseFloat(price).toFixed(2);
	
	return price;
}


$(document).ready(function() {
	
	loadPizzas();
});