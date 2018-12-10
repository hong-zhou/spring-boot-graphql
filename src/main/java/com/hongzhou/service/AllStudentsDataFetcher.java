package com.hongzhou.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hongzhou.model.Student;
import com.hongzhou.repos.StudentRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AllStudentsDataFetcher implements DataFetcher<List<Student>>{

	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public List<Student> get(DataFetchingEnvironment environment) {
		return studentRepository.findAll();
	}

}
