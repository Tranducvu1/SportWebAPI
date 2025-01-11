package sportshop.web.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import sportshop.web.Repository.TokenRepository;


@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler{
@Autowired
private  TokenRepository tokenRepository;
private static final Logger logger = LoggerFactory.getLogger(LogoutService.class);
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
	try {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			return;
		}
		jwt = authHeader.substring(7);
		var storeToken = tokenRepository.findBytoken(jwt).orElse(null);
		if(storeToken!=null) {
			storeToken.setExpired(true);
			storeToken.setRevoked(true);
			tokenRepository.save(storeToken);
			SecurityContextHolder.clearContext();
		}
	} catch (Exception e) {
		// TODO: handle exception
		logger.error("Error during logout"+e);
	}	
	}

}
