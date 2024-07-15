package sportshop.web.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sportshop.web.Config.LogoutService;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	@Autowired
  private  AuthenticationService service;
	@Autowired
	private LogoutService logoutService;
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
	
  @PostMapping("/signup")
  public ResponseEntity<String> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
	  try {
		  service.authenticate(request);
		  return ResponseEntity.ok("Login thành công");
	  } catch (Exception e) {
          return ResponseEntity.status(500).body("Có lỗi xảy ra: " + e.getMessage());
      }
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
      try {
          logoutService.logout(request, response, authentication);
          return ResponseEntity.ok("Logout thành công");
      } catch (Exception e) {
          return ResponseEntity.status(500).body("Có lỗi xảy ra: " + e.getMessage());
      }
  }

}
