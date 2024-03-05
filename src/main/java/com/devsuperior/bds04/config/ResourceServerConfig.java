package com.devsuperior.bds04.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private Environment environment;

	@Autowired
	private JwtTokenStore tokenStore;

	private static final String[] PUBLIC = { "/oauth/token/", "/h2-console/**" };

	private static final String[] CITY = { "/cities/**"};
	
	private static final String[] EVENT = { "/events/**" };

	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            http.headers(headers -> headers.frameOptions().disable());
		}

        http.authorizeRequests(requests -> requests
                .antMatchers(PUBLIC).permitAll()
                .antMatchers(HttpMethod.GET, CITY).permitAll()
                .antMatchers(HttpMethod.GET, EVENT).permitAll()
                .antMatchers(HttpMethod.POST, EVENT).hasAnyRole("ADMIN", "CLIENT")
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated());

	}

}
