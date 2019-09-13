package com.pizzaPalace.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.json.JSONArray;
import com.pizzaPalace.entity.Pizza;
import com.pizzaPalace.helper.PizzaHelper;

@Controller
@RequestMapping(value = "/customPrice")

public class PriceController {
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Double pricePizza(HttpServletRequest request) throws FileNotFoundException, IOException{			
		
		Double price = PizzaHelper.pricePizza(request);
		
		return price;
	}
}
