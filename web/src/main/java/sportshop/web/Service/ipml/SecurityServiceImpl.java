package sportshop.web.Service.ipml;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import sportshop.web.Service.SecurityService;
import sportshop.web.Repository.LogRepository;
import sportshop.web.Entity.Log;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService detailsService;

    @Autowired
    private LogRepository logRepository;

    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }
        return null;
    }

    @Override
    public void autoLogin(String email, String password) {
        UserDetails userDetails = detailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(authenticationToken);

        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            logLoginAttempt(authenticationToken.getName(), "Auto login successful");
            System.out.println("Auto login with " + email);
        }
    }

    private void logLoginAttempt(String username, String message) {
        Log log = new Log();
        log.setLogString(String.format("%s for %s", message, username));
        log.setCreateTime(new Timestamp(System.currentTimeMillis()));
        logRepository.save(log);
    }
}
