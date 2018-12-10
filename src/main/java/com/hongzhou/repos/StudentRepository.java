package com.hongzhou.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hongzhou.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

}
