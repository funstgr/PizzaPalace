package com.pizzaPalace.service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import com.pizzaPalace.entity.Topping;
import com.pizzaPalace.helper.PizzaHelper;


@Service
public class ToppingServiceImpl implements ToppingService {
	
	private static ConcurrentMap<Long, Topping> toppingMap; 
	
	@Override
	public Topping create(Topping Topping) {
		
		toppingMap.put(Topping.getId(), Topping);
		return Topping;
	}

	@Override
	public Topping read(long toppingId) {		
		return toppingMap.get(toppingId);
	}

	@Override
	public Topping update(Topping Topping) {
		Topping updatedTopping = toppingMap.replace(Topping.getId(), Topping);
		Validate.isTrue(updatedTopping != null, "Unable to find Topping with id: " + Topping.getId());
		return updatedTopping;
	}

	@Override
	public Topping delete(long ToppingId) {
		Topping removedTopping = toppingMap.remove(ToppingId);
		Validate.isTrue(removedTopping != null, "Unable to find Topping with id: " + ToppingId);
		return removedTopping;
	}

	@Override
	public Collection<Topping> list() {
		return toppingMap.values();
	}
	
	public static void makeToppingMap(Map<Long,Topping> Toppings){
		toppingMap = new ConcurrentHashMap<Long, Topping>(Toppings);
	}
}

