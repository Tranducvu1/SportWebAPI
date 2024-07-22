package sportshop.web.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SportWebFullStack.Model.Nguoidung;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sportshop.web.Config.JwtUtils;
import sportshop.web.Config.LogoutService;
import sportshop.web.Model.NguoiDung;
import sportshop.web.Service.UserService;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	@Autowired
  private AuthenticationService service;
	@Autowired
	private UserService userService;
	@Autowired
	private LogoutService logoutService;
	@Autowired
	private JwtUtils jwtUtils;
	 @GetMapping("/getAll")
	 public ResponseEntity<Object> findall(){
		return ResponseEntity.ok(userService.findAll());	 
	 }
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
	  AuthenticationResponse regis = service.register(request);
    return ResponseEntity.ok(regis);
  }
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(
      @RequestBody AuthenticationRequest request
  ) {
	  
	 
		  return ResponseEntity.ok(service.login(request));
	  
  }
  
  @GetMapping("/profile")
  public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
      try {
          String token = authHeader.substring(7); 
          String email = jwtUtils.getEmailFromToken(token);
          NguoiDung nguoidung = userService.findByEmail(email);
          return ResponseEntity.ok(nguoidung);
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
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
