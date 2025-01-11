package sportshop.web.Config.Security.auditing;

import java.sql.Timestamp;
import java.util.Optional;

import org.apache.catalina.User;
import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import sportshop.web.Entity.Log;
import sportshop.web.Entity.NguoiDung;
import sportshop.web.Repository.LogRepository;

public class auditing implements AuditorAware<Long>{
	@Autowired
	LogRepository logRepository;
	 @Override
	public Optional<Long> getCurrentAuditor(){
		 Log logger = new Log();
		 Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		 if(authentication==null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
			 		
			 		logger.setLogString("No authenticated user found for auditing");
			 		logger.setCreateTime(new Timestamp(System.currentTimeMillis()));
			 		logRepository.save(logger);
			 		return Optional.empty();
		 }
		try {
			Object principal = authentication.getPrincipal();
			if(principal instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) principal;
				if(userDetails instanceof NguoiDung) {
				NguoiDung user = (NguoiDung) userDetails;
				Long userId = user.getId();
				if(userId != null) {
					logger.setLogString("Current auditor user ID:");
					return Optional.of(userId);
					}
			}
			}
			logger.setLogString("Authecated principal is not of ễpcted type for auditing");
			return Optional.empty();
		} catch (Exception e) {
			logger.setLogString("Error while getting curent auditor");
		return Optional.empty();
	}
	 }
}
