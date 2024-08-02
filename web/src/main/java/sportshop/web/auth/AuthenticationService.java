package sportshop.web.auth;

import java.io.IOException;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import sportshop.web.Config.JwtUtils;
import sportshop.web.DTO.Role;
import sportshop.web.Model.Log;
import sportshop.web.Model.NguoiDung;
import sportshop.web.Model.Token;
import sportshop.web.Repository.LogRepository;
import sportshop.web.Repository.TokenRepository;
import sportshop.web.Repository.UserRepository;
import sportshop.web.token.TokenType;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	@Autowired
    private final UserRepository repository;
	@Autowired
    private final TokenRepository tokenRepository;
	@Autowired
    private final PasswordEncoder passwordEncoder;
	@Autowired
    private final JwtUtils jwtService;

	@Autowired
	private final AuthenticationManager authenticationManager;
	@Autowired
	private final LogRepository logRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        Log logger = new Log();
        try {
        	if (repository.findByEmail(request.getEmail()).isPresent()) {
                logger.setCreateTime(new Timestamp(System.currentTimeMillis()));
                logger.setLogString("Đăng kí thất bại: Email đã tồn tại");
                logRepository.save(logger);
                throw new RuntimeException("Email đã tồn tại");
            } else {
            var user = NguoiDung.builder()
            		.confirm_password(passwordEncoder.encode(request.getConfirm_password()))
            		.address(request.getAddress())            
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .so_dien_thoai(request.getSo_dien_thoai())
                    .role(request.getRole())
                    .hoten(request.getHoten())
                    .build();

            var savedUser = repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);
            logger.setCreateTime(new Timestamp(System.currentTimeMillis()));
            logger.setLogString("Đăng kí thành công");
            logRepository.save(logger);

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
            }
        } catch (Exception e) {
        	logger.setCreateTime(new Timestamp(System.currentTimeMillis()));
            logger.setLogString("Đăng kí thất bại: " + e.getMessage());
            logRepository.save(logger);
            throw e;
        }
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("test"+user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        Role role = user.getRole();
        String email = user.getEmail();
        String hoten = user.getHoten();
        String so_dien_thoai = user.getSo_dien_thoai();
        String address = user.getAddress();
        String gender = user.getGender();
        Long id = user.getId();
        String dayofbirrth = user.getDayofbirth();
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .id(id)
                .role(role)   
                .address(address)
                .email(email)
                .hoten(hoten)
                .Gender(gender)
                .so_dien_thoai(so_dien_thoai)
                .dayofbirth(dayofbirrth)
                .build();
    }
    
    private void saveUserToken(NguoiDung user, String jwtToken) {
        var token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(NguoiDung user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        Log logger = new Log();
        System.out.println(authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
        	logger.setCreateTime(new Timestamp(System.currentTimeMillis()));
            logger.setLogString("Authorization header is missing or does not start with Bearer");
            logRepository.save(logger);
            return;
        }
        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractEmail(refreshToken);
        logger.setCreateTime(new Timestamp(System.currentTimeMillis()));
        logger.setLogString("Authorization header found and token extracted");
        logRepository.save(logger);

        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                logger.setCreateTime(new Timestamp(System.currentTimeMillis()));
                logger.setLogString("Token refreshed successfully for user: " + userEmail);
                logRepository.save(logger);
            } else {
            	logger.setCreateTime(new Timestamp(System.currentTimeMillis()));
                logger.setLogString("Invalid token for user: " + userEmail);
                logRepository.save(logger);
            }
        } else {
        	logger.setCreateTime(new Timestamp(System.currentTimeMillis()));
            logger.setLogString("Unable to extract user email from token");
            logRepository.save(logger);
        }
    }
    	
}
