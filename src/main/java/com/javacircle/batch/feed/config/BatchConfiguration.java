package com.javacircle.batch.feed.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.javacircle.batch.feed.domain.Person;
import com.javacircle.batch.feed.domain.StudentDepart;
import com.javacircle.batch.feed.listeners.JobCompletionNotificationListener;
import com.javacircle.batch.feed.processors.PersonItemProcessor;
import com.javacircle.batch.feed.processors.StudentDeptProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	@Bean
	public FlatFileItemReader<Person> reader() {

		FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
		reader.setResource(new ClassPathResource("sample-data.csv"));
		reader.setLineMapper(new DefaultLineMapper<Person>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "firstName", "lastName", "email", "age", "deptCode" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
					{
						setTargetType(Person.class);
					}
				});
			}
		});
		return reader;
	}

	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}

	@Bean
	public StudentDeptProcessor studentDeptprocessor() {
		return new StudentDeptProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Person> writer() {
		JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
		writer.setSql(
				"INSERT INTO test.student (first_name, last_name, email, age, dept_Code) VALUES (:firstName, :lastName, :email, :age, :deptCode)");
		writer.setDataSource(dataSource);
		return writer;
	}

	@Bean
	public JdbcCursorItemReader<StudentDepart> readPersons() {

		JdbcCursorItemReader<StudentDepart> jdbcCursorObj = new JdbcCursorItemReader<StudentDepart>();
		jdbcCursorObj.setDataSource(dataSource);
		jdbcCursorObj.setRowMapper(new BeanPropertyRowMapper<StudentDepart>() {
			{
				setMappedClass(StudentDepart.class);
				
			}
		});
		jdbcCursorObj.setSql(
				"SELECT S1.FIRST_NAME,D1.DEPT_NAME FROM TEST.STUDENT S1,TEST.DEPT D1 WHERE S1.DEPT_CODE = D1.DEPT_CODE");
	
		
		
		
		return jdbcCursorObj;

	}

	@Bean
	public JdbcBatchItemWriter<StudentDepart> studentDepartWriter() {
		JdbcBatchItemWriter<StudentDepart> writer = new JdbcBatchItemWriter<StudentDepart>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<StudentDepart>());
		writer.setSql(
				"INSERT INTO STUD_DEPT (first_name,  dept_name) VALUES (:firstName,  :deptName)");
		writer.setDataSource(dataSource);
		return writer;
	}

	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener) {
		return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(listener)
				.flow(step1()).next(step2()).end().build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Person, Person>chunk(30).reader(reader()).processor(processor())
				.writer(writer()).build();
	}

	// To populate the STUDENT_DEPARTMENT
	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2").<StudentDepart, StudentDepart>chunk(30).reader(readPersons())
				.processor(studentDeptprocessor()).writer(studentDepartWriter()).build();
	}

}
