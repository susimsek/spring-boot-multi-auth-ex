package com.spring.auth.security.oauth2_auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.auth.config.Profiles;
import com.spring.auth.security.UserDetailsServiceImpl;
import com.spring.auth.security.jwt_auth.JwtAuthenticationEntryPoint;
import com.spring.auth.security.jwt_auth.JwtRequestFilter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Profile(Profiles.OATH2_AUTH)
@Configuration
@EnableResourceServer
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class Oath2AuthSecurityConfig extends ResourceServerConfigurerAdapter {


	private static final String[] AUTH_WHITELIST = {
			"/**/documentation/**",
			"/versions/1/auth/**",
			"/h2-console/**",
			"/**/v2/api-docs",           // swagger
			"/**/webjars/**",            // swagger-ui webjars
			"/**/swagger-resources/**",  // swagger-ui resources
			"/**/configuration/**",      // swagger configuration
			"/*.html",
			"/favicon.ico",
			"/**/*.html",
			"/**/*.css",
			"/**/*.js"
	};

	ResourceServerProperties resourceServerProperties;


	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceServerProperties.getResourceId());
	}

	@Bean
	public JwtAccessTokenCustomizer jwtAccessTokenCustomizer(ObjectMapper mapper) {
		return new JwtAccessTokenCustomizer(mapper);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().disable();
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http
				.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
				.anyRequest().authenticated();
	}

}