package com.cg.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

	@RequestMapping(value="/hello")
	public String sayHello() {
		/*
		 * this function is just for checking the application works properly or no.
		 * this function works when we pass url with /hello value. it uses ReqiestMapping to match function with url. 
		 */
		return "Hello World! from Spring Framework!";
	}
}
