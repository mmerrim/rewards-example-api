package com.example.rewards.configuration;

import com.example.rewards.service.RewardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Spring wiring class. The beans here are available for auto-wiring in the Spring components.
 */
@Configuration
public class ApplicationConfiguration
{
	/**
	 * @return Service for handling reward requests.
	 */
	@Bean
	public RewardService rewardService()
	{
		return new RewardService();
	}


	/**
	 * Enable logging of HTTP requests.
	 *
	 * @return The logging filter.
	 */
	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter()
	{
		CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();

		loggingFilter.setIncludeClientInfo(true);
		loggingFilter.setIncludeQueryString(true);
		loggingFilter.setIncludePayload(true);
		loggingFilter.setIncludeHeaders(true);

		return loggingFilter;
	}
}
