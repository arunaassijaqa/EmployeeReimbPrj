package com.Revature.Employee.Reimbursement.System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication

@EntityScan("com.revature.models") //This is telling Spring to look for DB entities here
@ComponentScan("com.revature") //Spring will look for Beans within com.revature (and its subpackages)
@EnableJpaRepositories("com.revature.daos") //This allows us to use JpaRepository in our daos
public class EmployeeReimbursementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeReimbursementSystemApplication.class, args);
		System.out.println(" inside main method");

	}

}
