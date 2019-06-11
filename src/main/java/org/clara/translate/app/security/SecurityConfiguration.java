package org.clara.translate.app.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import lombok.Setter;

@Configuration
@EnableWebSecurity
@Setter
@ConfigurationProperties(prefix = "backoffice")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private String user;
	
	private String password;

	private static final String LOGIN_URL = "/login";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/h2-console/**").permitAll()
			.antMatchers("/back").authenticated()
			.anyRequest().permitAll()
			.and().formLogin().loginPage(LOGIN_URL).permitAll().and().logout().logoutSuccessUrl(LOGIN_URL).permitAll()
			.and().headers().frameOptions().sameOrigin()
			.and().csrf().disable();
            
			
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder().username(this.user).password(this.password).roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}
}
