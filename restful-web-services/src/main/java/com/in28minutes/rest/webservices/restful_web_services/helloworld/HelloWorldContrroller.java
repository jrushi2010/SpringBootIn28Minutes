package com.in28minutes.rest.webservices.restful_web_services.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldContrroller {
	
	private MessageSource messageSource;
	
	public HelloWorldContrroller(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	
	@GetMapping(path = "/hello-world")
	public String HelloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean HelloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	@GetMapping(path = "/hello-world/Path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));
	}
	
	@GetMapping(path = "/hello-world-internationalized")
	public String HelloWorldInternationlised() {
		
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
		
		
		
		//Example : en - English (Good Morning)
		//Example : nl - Dutch (Goedemorgen)
		//Example : fr - French (Bonjour)
		//Example : de - Deutsch (Guten Morgen)
	}
	
}
