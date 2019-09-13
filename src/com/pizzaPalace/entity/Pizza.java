package com.pizzaPalace.entity;

import java.util.Set;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="pizza")
public class Pizza {
	private long id;
	private String name;
	private double price;
	private Set<String> toppings = new TreeSet<String>();
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public Set<String> getToppings() {
		return toppings;
	}

	public void addTopping(Topping topping) {
		toppings.add(topping.getName());
	}
}
