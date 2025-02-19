package com.in28minutes.rest.webservices.restful_web_services.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldContrroller {

	
	@GetMapping(path = "/hello-world")
	public String HelloWorld() {
		return "Hello World";
	}
}
