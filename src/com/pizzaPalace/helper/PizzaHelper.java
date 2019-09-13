package com.pizzaPalace.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;

import com.json.JSONArray;
import com.json.JSONException;
import com.json.JSONObject;
import com.pizzaPalace.entity.Pizza;
import com.pizzaPalace.entity.Topping;
import com.pizzaPalace.service.PizzaService;
import com.pizzaPalace.service.PizzaServiceImpl;
import com.pizzaPalace.util.PropertyUtils;

public class PizzaHelper {
	
	private static String PIZZAS_DATA = "/properties/pizzas.properties";
	
	private static String PIZZAS_PRICING = "/properties/pizzaPricing.properties";
	
	private static String BASIC_PIZZA_PRICE = "pizza.basic";
	
	private static String PIZZA_TOPPING_PRICE = "pizza.topping";	
	
	private PizzaServiceImpl pizzaService;
	
	
	public static double pricePizza(HttpServletRequest request) throws FileNotFoundException, IOException{
		
		String numberOfToppings = request.getParameter("numberOfToppings");
		Properties pizzaProps = PropertyUtils.getProperties(request, PIZZAS_PRICING);	
		double basicPizzaPrice = Double.valueOf(pizzaProps.getProperty(BASIC_PIZZA_PRICE)); 
		double toppingPrice = Double.valueOf(pizzaProps.getProperty(PIZZA_TOPPING_PRICE));		
		double pizzaPrice = basicPizzaPrice;			
		
		pizzaPrice = pizzaPrice + (Double.valueOf(numberOfToppings) * toppingPrice);
		
		return pizzaPrice;
			
	}
	
	public static synchronized List<Pizza> getPizzas(HttpServletRequest request) throws FileNotFoundException, IOException{
		
		Map<Long,Pizza> pizzas = new TreeMap<Long, Pizza>();
		
		Properties pizzaProps = PropertyUtils.getProperties(request, PIZZAS_DATA);
		Set<String> pizzaList = PropertyUtils.getKeys(pizzaProps);
		
		for (String key : pizzaList) {
			String pizzaString =  pizzaProps.getProperty(key);
			String[] array = getStringArray(pizzaString);
			Pizza pizza = makePizza(key, array);
			pizza.setPrice(pricePizza(request, pizza));
			pizzas.put(Long.valueOf(key), pizza);
		}
		PizzaServiceImpl.makePizzaMap(pizzas);
		List<Pizza> pl = new ArrayList<Pizza>(pizzas.values());		
		
		return pl;
		
	}
	
	private static double pricePizza(HttpServletRequest request, Pizza pizza) throws FileNotFoundException, IOException{		
		
		Properties pizzaProps = PropertyUtils.getProperties(request, PIZZAS_PRICING);
		String basicPrice = pizzaProps.getProperty(BASIC_PIZZA_PRICE);
		double basicPizzaPrice = Double.valueOf(basicPrice); 
		String tPrice = pizzaProps.getProperty(PIZZA_TOPPING_PRICE);
		double toppingPrice = Double.valueOf(tPrice);		
		double pizzaPrice = basicPizzaPrice;			
		
		pizzaPrice = pizzaPrice + (pizza.getToppings().size() * toppingPrice);
		
		return pizzaPrice;
			
	}
	
	private static Pizza makePizza(String key, String[] array) {
		Pizza pizza = new Pizza();
		pizza.setId(Integer.parseInt(key));
		for (int i = 0 ; i<array.length; i++) {
			if(i==0){
				pizza.setName(array[i]);
			}else{
				Topping topping = new Topping();
				topping.setName(array[i]);
				pizza.addTopping(topping);
			}
		}
		return pizza;
	}
	
	public static Pizza makePizza(HttpServletRequest request) {
		Pizza pizza = new Pizza();
		
		return pizza;
	}

	private static String[] getStringArray(String pizzaString){
		
		String[] array = pizzaString.split(",");		
		
		return array;
	}

	public static Pizza createNewCustomPizza(HttpServletRequest request, ConcurrentMap<Long, Pizza> pizzaMap) 
			throws FileNotFoundException, IOException, JSONException {
		Pizza pizza = new Pizza();
		pizza.setId(pizzaMap.size() + 1);
		pizza.setName(request.getParameter("name"));
		String toppings = request.getParameter("toppings");
		JSONArray jArray = new JSONArray(toppings);
		
		List<Topping> tl = ToppingHelper.getToppings(request);
		for (Topping topping : tl) {
			for(int i = 0; i < jArray.length(); i++){				
				String tq = jArray.getString(i);				
				if(topping.getName().equals(tq)){
					pizza.addTopping(topping);
				}
			}			
		}
		List<Pizza> pizzas =  getPizzas(request);
		pizzas.add(pizza);
		PropertyUtils.addPizzaProperties( request,  PIZZAS_DATA,  pizzas);		
		
		return pizza;
	}
	
	public static Pizza createNewCustomPizza(HttpServletRequest request, ConcurrentMap<Long, Pizza> pizzaMap, Pizza pizza) 
			throws FileNotFoundException, IOException, JSONException {	
		
		List<Pizza> pizzas =  getPizzas(request);
		pizzas.add(pizza);
		PropertyUtils.addPizzaProperties( request,  PIZZAS_DATA,  pizzas);		
		
		return pizza;
	}

}
