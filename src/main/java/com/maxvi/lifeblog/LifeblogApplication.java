package com.maxvi.lifeblog;

import io.undertow.Undertow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.MultipartConfigElement;
import java.util.concurrent.Executor;

@SpringBootApplication
@Configuration
@EnableAsync
@EnableTransactionManagement
public class LifeblogApplication {

	@Bean
	@Primary
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Logger logger()
	{
		return LogManager.getLogger();
	}

	@Bean
	public MultipartConfigElement multipartConfigElement()
	{
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("150MB");
		factory.setMaxRequestSize("150MB");
		return factory.createMultipartConfig();
	}

	@Bean
	public Executor asyncExecutor()
	{
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("LifeblogAsync-");
		executor.initialize();
		return executor;
	}


	public static void main(String[] args) {
		SpringApplication.run(LifeblogApplication.class, args);
	}
}
