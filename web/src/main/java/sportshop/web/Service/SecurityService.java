package sportshop.web.Service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import sportshop.web.Model.Log;
import sportshop.web.Repository.LogRepository;

@Service
public class SecurityService {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService detailsService;
	@Autowired
	LogRepository logRepository;
	
	public String findLoggedInUsername() {
		Object userDetials = SecurityContextHolder.getContext().getAuthentication();
		if(userDetials instanceof UserDetails) {
			return ((UserDetails) userDetials).getUsername();
		}
		return null;
	}
	
	public void autoLogin(String email,String password) {
		UserDetails userDetails = detailsService.loadUserByUsername(email);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password,userDetails.getAuthorities());
		authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		if(usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			//saveImageForMatHang(savedMatHang, hinhanh);
            Log log = new Log();
            log.setLogString("Auto Loggin %s" + usernamePasswordAuthenticationToken.getName()+"successs");
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            logRepository.save(log);
            System.out.println("auto login with "+ email);
		}
	}
	
}
