package com.hongzhou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hongzhou.model.Student;
import com.hongzhou.repos.StudentRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class StudentDataFetcher implements DataFetcher<Student>{
	
	@Autowired
	private StudentRepository studentRepository;

	@Override
	public Student get(DataFetchingEnvironment environment) {
		String studentId = environment.getArgument("studentId");
		return studentRepository.findById(studentId).get();
	}

}
