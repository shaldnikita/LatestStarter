package ru.shaldnikita.starter.app;

import com.vaadin.spring.access.SecuredViewAccessControl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author n.shaldenkov on 23.11.2017
 */

@Configuration
public class ApplicationConfiguration {
	@Bean
	SecuredViewAccessControl securedViewAccessControl()
	{
		return new SecuredViewAccessControl();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
