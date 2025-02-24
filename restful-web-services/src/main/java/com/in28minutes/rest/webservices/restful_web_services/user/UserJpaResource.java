package com.in28minutes.rest.webservices.restful_web_services.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restful_web_services.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
	private UserRepository repository;

	public UserJpaResource(UserRepository repository) {
		this.repository = repository;
	}

	@GetMapping(path = "/jpa/users")
	public List<User> retrieveAllUsers(){
		return repository.findAll();
	}
	
	@GetMapping(path = "/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id){
		Optional<User> user = repository.findById(id);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);
		
		//wrapping the user class and creating the entity modal
		EntityModel<User> entityModel = EntityModel.of(user.get());
		
		//we are using WebMvcLinkBuilder to link method to create a method pointing to the controller method
		//the controller method we are pointing to retrieveAllUsers 
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		//now once we have the link we can the link to the entityModel 
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = repository.save(user);
		
		//sending created user as a response in header
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}") //so we are appending id on the current request and replacing with the value
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location ).build();
	}
	
	@DeleteMapping(path = "/jpa/users/{id}")
	public void deleteUser(@PathVariable int id){
		repository.deleteById(id);
	}
}
