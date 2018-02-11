package com.javacircle.batch.feed.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.javacircle.batch.feed.domain.StudentDepart;

public class StudentDeptProcessor implements ItemProcessor<StudentDepart, StudentDepart> {

	private static final Logger log = LoggerFactory.getLogger(StudentDeptProcessor.class);

	@Override
	public StudentDepart process(final StudentDepart studentDepartObj) throws Exception {
		final String firstName = studentDepartObj.getFirstName().toUpperCase();
		final String deptName = studentDepartObj.getDeptName();

		final StudentDepart transformedStudentDepart = new StudentDepart(firstName, deptName);

		log.info(" DeptName: (" + deptName + ")");

		return transformedStudentDepart;
	}

}
