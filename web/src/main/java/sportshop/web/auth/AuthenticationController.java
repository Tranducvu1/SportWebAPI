package sportshop.web.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sportshop.web.Config.JwtUtils;
import sportshop.web.Config.LogoutService;
import sportshop.web.Entity.NguoiDung;
import sportshop.web.Repository.UserRepository;
import sportshop.web.Service.NguoiDungService;
import sportshop.web.Service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	@Autowired
  private AuthenticationService service;
	@Autowired
	private NguoiDungService userService;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LogoutService logoutService;
	@GetMapping("/getAll")
	 public ResponseEntity<Object> findall(){
		return ResponseEntity.ok(userService.getAllUsers());	 
	 }
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
	  AuthenticationResponse regis = service.register(request);
    return ResponseEntity.ok(regis);
  }
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
      // Gọi service login và lấy thông tin từ response
      AuthenticationResponse authResponse = service.login(request);

      // Kiểm tra console để xem đúng không
      System.out.println("Login successful. Response: " + authResponse);

      // Trả về response hoàn chỉnh
      return ResponseEntity.ok(authResponse);
  }

  @GetMapping("/userdetails")
  public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String token) {
	  System.out.println(token);
      String username = jwtUtils.extractEmail(token.substring(7));
      System.out.println("username "+username);
      NguoiDung user = userRepository.searchByEmail(username);
      	System.out.println( "hi"+user);
      			RegisterRequest userDetailsResponse = new RegisterRequest(user.getId(),
          user.getHoten(), user.getEmail(), user.getSo_dien_thoai(), user.getAddress(),
          user.getHoten(), user.getGender(), null, user.getDayofbirth(), user.getHinhdaidien()
      );
      return ResponseEntity.ok(userDetailsResponse);
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
