package com.pizzaPalace.service;

import java.util.Collection;

import com.pizzaPalace.entity.Topping;

public interface ToppingService {
	Topping create(Topping topping);

	Topping read(long toppingId);

	Topping update(Topping topping);

	Topping delete(long toppingId);

	Collection<Topping> list();
}
