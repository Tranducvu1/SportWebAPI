package sportshop.web.Config;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired
    private  AuthenticationProvider authenticationProvider;
	@Autowired
	private  AuthTokenFilter authtoken;
	@Autowired
    private LogoutHandler logoutHandler;
	@Bean
	  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	  }
	
	 @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	    	.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> 
		//    auth.requestMatchers("/api/auth/**", "/api/auth/register", "/api/auth/signup").permitAll()
		// 	  .requestMatchers("/api/**").hasAuthority("ADMIN")
		// 	 .requestMatchers("/sport/**").hasAuthority("MEMBER")
		auth.requestMatchers("/api/**").permitAll()
//				 .anyRequest().authenticated()           // all authentication
			)
	    .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
		.formLogin(formLogin -> formLogin.disable())  
            .httpBasic(httpBasic -> httpBasic.disable())  
	    .authenticationProvider(authenticationProvider)
	    .addFilterBefore(authtoken, UsernamePasswordAuthenticationFilter.class)
        .logout(logout ->
                logout.logoutUrl("/api/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
	  );
	    return http.build();
	  }
//		 http
//	        .csrf().disable()
//	        .authorizeRequests()
//	        .anyRequest().permitAll();
//		return http.build();
//	 }
}
