package com.pizzaPalace.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

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

import com.pizzaPalace.entity.Topping;
import com.pizzaPalace.entity.ToppingList;
import com.pizzaPalace.helper.ToppingHelper;
import com.pizzaPalace.service.ToppingService;

@Controller
@RequestMapping(value = "/toppings")
public class ToppingController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ToppingController.class);

	@Autowired
	private ToppingService toppingService;

	@RequestMapping(method = RequestMethod.POST,
			headers="Accept=application/xml, application/json")
	@ResponseBody
	public Topping create(@RequestBody Topping topping) {
		LOGGER.info("Creating new Topping {}", topping);

		return toppingService.create(topping);
	}

	@RequestMapping(value = "/{toppingId}", method = RequestMethod.GET,
			headers="Accept=application/xml, application/json")
	@ResponseBody
	public Topping read(@PathVariable(value = "toppingId") long toppingId, HttpServletRequest request) throws FileNotFoundException, IOException {
		LOGGER.info("Reading Topping with id {}", toppingId);
			
		Topping topping = toppingService.read(toppingId);
		Validate.isTrue(topping != null, "Unable to find Topping with id: " + toppingId);

		return topping;
	}

	@RequestMapping(value = "/{toppingId}", method = RequestMethod.PUT,
			headers="Accept=application/xml, application/json")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable(value = "toppingId") long toppingId, @RequestBody Topping topping) {
		LOGGER.info("Updating user with id {} with {}", toppingId, topping);

		Validate.isTrue(toppingId == topping.getId(), "userId doesn't match URL toppingId: " + topping.getId());

		toppingService.update(topping);
	}

	@RequestMapping(value = "/{toppingId}", method = RequestMethod.DELETE,
			headers="Accept=application/xml, application/json")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(value = "toppingId") long toppingId) {
		LOGGER.info("Deleting Topping with id {}", toppingId);

		toppingService.delete(toppingId);
	}

	@RequestMapping(value = "/toppings",
			method = RequestMethod.GET,
			headers="Accept=application/xml, application/json")
	@ResponseBody
	public ToppingList list(HttpServletRequest request) throws FileNotFoundException, IOException { 
		LOGGER.info("Listing toppings");
		ToppingList tl = new ToppingList(ToppingHelper.getToppings(request));			

		return tl;
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
