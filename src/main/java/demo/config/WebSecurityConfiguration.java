package demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login")
				// <= custom login page
				.defaultSuccessUrl("/profile").and().logout().logoutSuccessUrl("/login").and().authorizeRequests()
				.antMatchers("/webjars/**", "/login", "/signin/**", "/signup").permitAll().anyRequest().authenticated()
				/*.and().requiresChannel().anyRequest().requiresSecure()*/;
	}

}
