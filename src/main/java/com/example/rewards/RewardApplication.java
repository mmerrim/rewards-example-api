package com.example.rewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot entry point.
 */
@SpringBootApplication
@Configuration
public class RewardApplication
{
	/**
	 * Application entry point.
	 *
	 * @param args Unused.
	 */
	public static void main(final String[] args)
	{
		SpringApplication.run(RewardApplication.class, args);
	}
}
