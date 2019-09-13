package com.pizzaPalace.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.json.JSONException;
import com.pizzaPalace.entity.Pizza;

public interface PizzaService {
	Pizza create(Pizza pizza, HttpServletRequest request) throws FileNotFoundException, JSONException, IOException;

	Pizza read(long pizzaId);

	Pizza update(Pizza pizza);

	Pizza delete(long pizzaId);

	Collection<Pizza> list();
}
