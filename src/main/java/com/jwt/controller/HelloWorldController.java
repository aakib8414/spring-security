package com.jwt.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
public class HelloWorldController {

	@RequestMapping(value="/hello" ,method = RequestMethod.GET)
	public String hello() {
		return "Hello World";
	}

}
