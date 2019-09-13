package com.pizzaPalace.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import com.json.JSONException;
import com.pizzaPalace.entity.Pizza;
import com.pizzaPalace.helper.PizzaHelper;


@Service
public class PizzaServiceImpl implements PizzaService {
	
	private static ConcurrentMap<Long, Pizza> pizzaMap; 
	
	@Override
	public Pizza create(Pizza pizza, HttpServletRequest request) throws JSONException, IOException {
		
		
		pizza.setId(pizzaMap.size() + 1);
		pizza = PizzaHelper.createNewCustomPizza(request, pizzaMap, pizza);
		
		
		return pizza;
	}

	@Override
	public Pizza read(long pizzaId) {		
		return pizzaMap.get(pizzaId);
	}

	@Override
	public Pizza update(Pizza pizza) {
		Pizza updatedPizza = pizzaMap.replace(pizza.getId(), pizza);
		Validate.isTrue(updatedPizza != null, "Unable to find Pizza with id: " + pizza.getId());
		return updatedPizza;
	}

	@Override
	public Pizza delete(long pizzaId) {
		Pizza removedPizza = pizzaMap.remove(pizzaId);
		Validate.isTrue(removedPizza != null, "Unable to find Pizza with id: " + pizzaId);
		return removedPizza;
	}

	@Override
	public Collection<Pizza> list() {
		return pizzaMap.values();
	}
	
	public static void makePizzaMap(Map<Long,Pizza> pizzas){
		pizzaMap = new ConcurrentHashMap<Long, Pizza>(pizzas);
	}
}
