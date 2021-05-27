package com.example.dobrobytplus.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	static SessionRegistry SR;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new LoginPageFilter(), DefaultLoginPageGeneratingFilter.class);
		http
				.authorizeRequests()
					.antMatchers("/register").permitAll()
					.antMatchers("/login").not().authenticated()
					.anyRequest().authenticated()
					.and()
				.formLogin()
					.loginPage("/login").defaultSuccessUrl("/main").failureUrl("/login?error").permitAll()
					.and()
				.logout()
				.logoutSuccessUrl("/login?logout").permitAll();
	}



//	@Bean
//	public AuthenticationProvider daoAuthenticationProvider(MyUserDetailsService detailsService, PasswordEncoder passwordEncoder) {
//		DaoAuthenticationProvider provider =
//				new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(passwordEncoder);
//		provider.setUserDetailsService(detailsService);
//		return provider;
//	}

	// z aktualnym uzytkownikiem
	@Bean
	public AuthenticationProvider daoAuthenticationProvider(MyUsersDetailsService detailsService, PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider =
				new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(detailsService);
		return provider;
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}



}