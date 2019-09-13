package com.pizzaPalace.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.json.JSONException;
import com.pizzaPalace.entity.Pizza;
import com.pizzaPalace.entity.PizzaList;
import com.pizzaPalace.helper.PizzaHelper;
import com.pizzaPalace.service.PizzaService;



@Controller
@RequestMapping(value = "/pizzas")
public class PizzaController {	
	
		private static final Logger LOGGER = LoggerFactory.getLogger(PizzaController.class);

		@Autowired
		private PizzaService pizzaService;

		@RequestMapping(method = RequestMethod.POST,
				headers="Accept=application/xml, application/json")
		@ResponseBody
		public Pizza create(@RequestBody Pizza pizza, HttpServletRequest request) throws JSONException, IOException {
			LOGGER.info("Creating new pizza {}");

			return pizzaService.create(pizza, request);
		}

		@RequestMapping(value = "/{pizzaId}", method = RequestMethod.GET,
				headers="Accept=application/xml, application/json")
		@ResponseBody
		public Pizza read(@PathVariable(value = "pizzaId") long pizzaId, HttpServletRequest request) throws FileNotFoundException, IOException {
			LOGGER.info("Reading pizza with id {}", pizzaId);
				
			Pizza pizza = pizzaService.read(pizzaId);
			Validate.isTrue(pizza != null, "Unable to find pizza with id: " + pizzaId);

			return pizza;
		}

		@RequestMapping(value = "/{pizzaId}", method = RequestMethod.PUT,
				headers="Accept=application/xml, application/json")
		@ResponseStatus(value = HttpStatus.NO_CONTENT)
		public void update(@PathVariable(value = "pizzaId") long pizzaId, @RequestBody Pizza pizza) {
			LOGGER.info("Updating user with id {} with {}", pizzaId, pizza);

			Validate.isTrue(pizzaId == pizza.getId(), "userId doesn't match URL pizzaId: " + pizza.getId());

			pizzaService.update(pizza);
		}

		@RequestMapping(value = "/{pizzaId}", method = RequestMethod.DELETE,
				headers="Accept=application/xml, application/json")
		@ResponseStatus(value = HttpStatus.NO_CONTENT)
		public void delete(@PathVariable(value = "pizzaId") long pizzaId) {
			LOGGER.info("Deleting pizza with id {}", pizzaId);

			pizzaService.delete(pizzaId);
		}
		
		@RequestMapping(value = "/pizzas", 
				method = RequestMethod.GET,
				headers="Accept=application/json,application/xml")
		@ResponseBody
		public PizzaList list(HttpServletRequest request) throws FileNotFoundException, IOException { 
			LOGGER.info("Listing pizzas");
			PizzaList pl = new PizzaList(PizzaHelper.getPizzas(request));			

			return pl;
		}		

		@ExceptionHandler(IllegalArgumentException.class)
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		@ResponseBody
		public String handleClientErrors(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return ex.getMessage();
		}

		@ExceptionHandler(Exception.class)
		@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
		@ResponseBody
		public String handleServerErrors(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return ex.getMessage();
		}
}
