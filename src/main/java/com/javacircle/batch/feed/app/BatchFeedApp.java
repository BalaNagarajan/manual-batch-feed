package com.javacircle.batch.feed.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main entry point class for the Manual feed batch application
 * 
 * @author Bala
 *
 */

@SpringBootApplication(scanBasePackages = "com.javacircle")
public class BatchFeedApp {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BatchFeedApp.class, args);
	}
}
