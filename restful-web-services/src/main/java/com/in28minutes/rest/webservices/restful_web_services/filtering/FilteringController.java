package com.in28minutes.rest.webservices.restful_web_services.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	@GetMapping("filtering")
	public MappingJacksonValue filtering() {
		//this bean contains the data which we want to return back
		SomeBean someBean = new SomeBean("value1","value2","value3");
		
		//MappingJacksonValue allows you to add serialization login in addition to your data
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
		
		SimpleBeanPropertyFilter filter  = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
		
		//creating a instance of SimpleFilterProvider and adding a filter to it.
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		
		//MappingJacksonValue allows you to set filters 
		mappingJacksonValue.setFilters(filters);
		
		//so we are returning the bean as well as the serialization logic back
		return mappingJacksonValue;
	}
	
	@GetMapping("filtering-list") //field2, field3
	public MappingJacksonValue filteringList() {
		List<SomeBean> list = Arrays.asList(new SomeBean("value1","value2","value3"),
				new SomeBean("value4","value5","value6"));
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);

		SimpleBeanPropertyFilter filter  = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
		
		//creating a instance of SimpleFilterProvider and adding a filter to it.
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		
		//MappingJacksonValue allows you to set filters 
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}
	
}
