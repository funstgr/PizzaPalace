package com.pizzaPalace.entity;


	import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
	
	@XmlRootElement(name="toppings")
	public class ToppingList {
		private List<Topping> toppings = new ArrayList<Topping>();

		public ToppingList() {}

		public ToppingList(List<Topping> toppings) {
			this.toppings = toppings;
		}
		
		public List<Topping> getToppings() {
			return toppings;
		}

		public void setToppings(List<Topping> toppings) {
			this.toppings = toppings;
		}
	}


