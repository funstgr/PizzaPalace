package com.pizzaPalace.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import com.pizzaPalace.entity.Topping;
import com.pizzaPalace.service.ToppingServiceImpl;
import com.pizzaPalace.util.PropertyUtils;

public class ToppingHelper {
	
	private static String TOPPING_DATA = "/properties/toppings.properties";
	
	private ToppingServiceImpl toppingService;	
	
	public static synchronized List<Topping> getToppings(HttpServletRequest request) throws FileNotFoundException, IOException{
		
		Map<Long,Topping> toppings = new TreeMap<Long, Topping>();
		
		Properties toppingProps = PropertyUtils.getProperties(request, TOPPING_DATA);
		Set<String> toppingList = PropertyUtils.getKeys(toppingProps);
		
		for (String key : toppingList) {
			String toppingString =  toppingProps.getProperty(key);
			String[] array = getStringArray(toppingString);
			Topping Topping = makeTopping(key, array);
			
			toppings.put(Long.valueOf(key), Topping);
		}
		ToppingServiceImpl.makeToppingMap(toppings);
		List<Topping> pl = new ArrayList<Topping>(toppings.values());		
		
		return pl;		
	}
	
	private static Topping makeTopping(String key, String[] array) {
		Topping topping = new Topping();
		topping.setId(Integer.parseInt(key));
		for (int i = 0 ; i<array.length; i++) {
			if(i==0){
				topping.setName(array[i]);
			}
		}
		return topping;
	}

	private static String[] getStringArray(String pizzaString){
		
		String[] array = pizzaString.split(",");		
		
		return array;
	}


}
