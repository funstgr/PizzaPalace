

function loadBasicCustomPizza(pizzaNum){
	
	var path = contextPath + '/pizzas/' + pizzaNum;	
	var request= { };
	
	$.get(path,  request,   function(data, status, xhr){						
        if(xhr)
        {
        	loadCustomPizza(data);
        }
	}, "json");		
}

function updatePizzaPrice(){
	var topNo = getToppings().length;	
	var path = contextPath + '/customPrice';	
	var request= { 
			numberOfToppings: topNo
	};
	
	$.post(path,  request,   function(data, status, xhr){						
        if(xhr)
        {
        	var price = formatNo(data);
        	console.log(price);
        	$("#customPizzaPrice").html('$' + price);
        }
	}, "json");		
}

function loadPizza(pizzaNum){
	
	showDialog(pizzaNum);	
	
}


function showDialog(pizzaNum)
{
	$( "#dialogWarning" ).html('<p><span class="ui-icon ui-icon-alert" style="float: '
	+ 'left; margin: 0 7px 20px 0;"></span>' + 'This custom pizza settings will be lost'
	+ ' and cannot be recovered. Are you sure?</p>');
	$( "#dialogWarning" ).dialog({ 
			width: 500,
			height: 250,
			modal: true,
			title: 'Custom Pizza Warning',
			autoOpen: true,
			buttons: {
		        "OK": function() {
		        loadBasicCustomPizza(pizzaNum);
		        $( "#dialogWarning" ).html();
		        $( this ).dialog( "close" );
		        },
		        Cancel: function() {
		        $( "#dialogWarning" ).html();
		        $( this ).dialog( "close" );
		        }
		    }
		});	
}


function loadToppings(){
	
	var path = contextPath + '/toppings';	
	var request= { };
	
	$.get(path,  request,   function(data, status, xhr){						
        if(xhr)
        {
        	loadToppingList(data.toppings);
        }
	}, "json");
}

function loadToppingList(toppings){
	var row = "";
	$(toppings).each(function(index,element){		
		if(index % 2 == 0){
			row = '<tr>';
			row = row + '<td class="ckTd"><input type="checkbox" name="toppings" value="'  
			+ element.name +'" onclick="updatePizzaPrice();"></td><td class="leftTd">' 
			+ element.name + '</td>';
		}else{
			row = row + '<td class="ckTd"><input type="checkbox" name="toppings" value="' 
			+ element.name +'" onclick="updatePizzaPrice();"></td><td class="rightTd">' 
			+ element.name + '</td></tr>';
			$("#toppingTableList").append(row);
			row = '';
		}
	});
	if(row.length>0){
		row = row + '</tr>';
		$("#toppingTableList").append(row);
	}
}


function loadCustomPizza(pizza){
	var toppings = new Array();
	toppings = pizza.toppings;	
	var price = formatNo(pizza.price);
	$("#customPizzaName").val(pizza.name);
	$("#customPizzaPrice").html('$' + price);
	$("input[type='checkbox']").each(function(){
		$(this).removeAttr("checked");
	});
	$(toppings).each(function(index,element){
		var topping = element.trim();
		$('input[value="'+ topping +'"]').attr('checked', 'checked');
	});
}

//function createCustomPizza(){
//	var path = contextPath + '/pizzas';
//	var name = $("#customPizzaName").val();
//	console.log(name);
//	var toppings = JSON.stringify(getToppings());
//	var request= { 
//		name: name,	
//		toppings: toppings
//	};	
//	$.post(path,  request,   function(data, status, xhr){						
//        if(xhr)
//        {
//        	reloadApplication();
//        }
//	}, "json");	
//}

function createCustomPizza(){
	var path = contextPath + '/pizzas';
	var pizza = JSON.stringify(createCustomPizzaObject());
	console.log(pizza);
	
	$.ajax({
	    type: "POST",
	    url: path,
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    data: pizza,
	    success: function(json) {
	    	reloadApplication();
	}});
}

function createCustomPizzaObject(){
	var pizza = new Object();
	pizza.id = "";
	pizza.name = $("#customPizzaName").val();
	pizza.toppings = getToppings();
	
	return pizza;
}

function reloadApplication(){
	$('#reloadApplicationForm').submit();
}

function getToppings(){
	var toppings = new Array();
	
	$("input[type='checkbox']:checked").each(function(index, element){		
		toppings[index] = element.value;		
	});
	
	return toppings;
}

$(document).ready(function() {		
	loadToppings();	
	loadBasicCustomPizza(1);    
	
});