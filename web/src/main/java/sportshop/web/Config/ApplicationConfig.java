package sportshop.web.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import sportshop.web.Config.Security.auditing.auditing;
import sportshop.web.Repository.UserRepository;

@Configuration
//@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "sportshop.web.Repository")
public class ApplicationConfig {

@Autowired
private UserRepository userRepository;


@Bean
public UserDetailsService userDetailsService() {
  return username -> userRepository.findByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
}

@Bean
public AuthenticationProvider authenticationProvider() {
  DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
  authProvider.setUserDetailsService(userDetailsService());
  authProvider.setPasswordEncoder(passwordEncoder());
  return authProvider;
}

@Bean
public AuditorAware<Long> auditorAware() {
  return new auditing();
}


@Bean
public PasswordEncoder passwordEncoder() {
  return new BCryptPasswordEncoder();
}


}
