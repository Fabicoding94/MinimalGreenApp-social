package com.fabicoding94.MinimalGreenApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@SpringBootApplication /*(exclude={DataSourceAutoConfiguration.class})*/

public class MinimalGreenAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinimalGreenAppApplication.class, args);
	}

}
