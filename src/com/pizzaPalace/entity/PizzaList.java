package com.pizzaPalace.entity;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="pizzas")
public class PizzaList {
	private List<Pizza> pizzas = new ArrayList<Pizza>();

	public PizzaList() {}

	public PizzaList(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	
	public List<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
}
