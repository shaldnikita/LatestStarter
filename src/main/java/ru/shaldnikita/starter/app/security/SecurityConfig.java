package ru.shaldnikita.starter.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.shaldnikita.starter.app.Application;
import ru.shaldnikita.starter.backend.model.dict.Role;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;

	private final PasswordEncoder passwordEncoder;

	private final RedirectAuthenticationSuccessHandler successHandler;

	@Autowired
	public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder,
			RedirectAuthenticationSuccessHandler successHandler) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
		this.successHandler = successHandler;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry reg = http
				.authorizeRequests();


		reg = reg.antMatchers("/VAADIN/**").permitAll();
		reg = reg.antMatchers("/**").hasAnyAuthority(Role.getAllRoles());
		HttpSecurity sec = reg.and();

		FormLoginConfigurer<HttpSecurity> login = sec.formLogin().permitAll();
		login = login.loginPage(Application.LOGIN_URL).loginProcessingUrl(Application.LOGIN_PROCESSING_URL)
				.failureUrl(Application.LOGIN_FAILURE_URL).successHandler(successHandler);
		login.and().logout().logoutSuccessUrl(Application.LOGOUT_URL);
	}

}
