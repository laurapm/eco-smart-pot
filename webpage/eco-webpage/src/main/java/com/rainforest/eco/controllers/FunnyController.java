package com.rainforest.eco.controllers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@RequestMapping("/api")
public class FunnyController 
{
	/**
	 * Welcomes the developer aceessing the API.
	 * 
	 * @return Welcome message
	 */
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	@ResponseBody
	public String hello() {
		return "Hello World Developer!";
	}
}
