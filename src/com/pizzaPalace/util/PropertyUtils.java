package com.pizzaPalace.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.SystemPropertyUtils;

import com.pizzaPalace.entity.Pizza;
import com.pizzaPalace.helper.ToppingHelper;

public class PropertyUtils extends SystemPropertyUtils {

	public static Properties getProperties(HttpServletRequest request, String fileName)
			throws IOException, FileNotFoundException {

		Properties properties = new Properties();
		String path = FileSystemUtil.getRealPath(request,
				fileName);

		File propertiesFile = new File(path);
		if (!propertiesFile.exists()) {
			throw new IllegalStateException("Could not find properties file: "
					+ propertiesFile.getAbsolutePath());

		} else {
			properties.load(new FileInputStream(propertiesFile));
		}
		return properties;
	}

	public static Set<String> getKeys(Properties properties) {

		return properties.stringPropertyNames();
	}
	
	public static void addPizzaProperties(HttpServletRequest request, String fileName, List<Pizza> pizzas){
		try {
			Properties properties = new Properties();
			String path = FileSystemUtil.getRealPath(request,
					fileName);
			for (Pizza pizza : pizzas) {
				StringBuilder pizzaString = buildPropertyString(pizza, request);
				properties.setProperty("" + pizza.getId(), pizzaString.toString());
			}
						

			File propertiesFile = new File(path);
			FileOutputStream fileOut = new FileOutputStream(propertiesFile, false);
			properties.store(fileOut, "Pizzas");
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static StringBuilder buildPropertyString(Pizza pizza, HttpServletRequest request){
		StringBuilder sb = new StringBuilder();
		sb.append(pizza.getName());
		Set<String> tops = pizza.getToppings();
		for (String t : tops) {
			sb.append(",");
			sb.append(t);
		}
		
		return sb;
	}
}
