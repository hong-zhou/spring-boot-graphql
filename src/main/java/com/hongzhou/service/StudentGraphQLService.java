package com.hongzhou.service;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.hongzhou.model.Student;
import com.hongzhou.repos.StudentRepository;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Service
public class StudentGraphQLService {

	@Autowired
	StudentRepository studentRepository;
	
	@Value("classpath:students.graphql")
	Resource resource;
	
	private GraphQL graphQL;

	@Autowired
	private AllStudentsDataFetcher allStudentsDataFetcher;

	@Autowired
	private StudentDataFetcher studentDataFetcher;
	
	// load schema at application start up
	@PostConstruct
	private void loadSchema() throws IOException {
		
		loadStudentDataO();
		
		// get the schema
		File schemaFile = resource.getFile();
		// parse schema
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
		RuntimeWiring wiring = buildRuntimeWiring();
		GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
		graphQL = GraphQL.newGraphQL(schema).build();
	}

	private void loadStudentDataO() {
		Stream.of (
				new Student("1", "Hong", "Zhou", "2018-11-14", new String[] {"Physics", "English", "Arts"}),
				new Student("2", "Martin", "John", "2018-11-14", new String[] {"Biology", "English", "Arts"}),
				new Student("3", "Henry", "Fort", "2018-11-14", new String[] {"English", "Arts"}))
			.forEach(student -> {studentRepository.save(student);});
	}

	private RuntimeWiring buildRuntimeWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type("Query", typeWiring -> typeWiring
						.dataFetcher("allStudents", allStudentsDataFetcher)
						.dataFetcher("student", studentDataFetcher))
				.build();
	}
	
	public GraphQL getGraphQL() {
		return graphQL;
	}
	
}
