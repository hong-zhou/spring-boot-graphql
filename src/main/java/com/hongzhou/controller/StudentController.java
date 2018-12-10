package com.hongzhou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hongzhou.service.StudentGraphQLService;

import graphql.ExecutionResult;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	
	@Autowired
	StudentGraphQLService studentGraphQLService;

	@PostMapping
	public ResponseEntity<Object> retrieveAllStudents(@RequestBody String query){
		
		ExecutionResult execute = studentGraphQLService.getGraphQL().execute(query);
		
		return new ResponseEntity<>(execute, HttpStatus.OK);
	}
}
