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
import lombok.extern.slf4j.Slf4j; // Import Slf4j
import sportshop.web.Config.JwtUtils;
import sportshop.web.Entity.Log;
import sportshop.web.Entity.NguoiDung;
import sportshop.web.Entity.Token;
import sportshop.web.Repository.TokenRepository;
import sportshop.web.Repository.UserRepository;
import sportshop.web.token.TokenType;

@Service
@RequiredArgsConstructor
@Slf4j // Annotation cho logging
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

    public AuthenticationResponse register(RegisterRequest request) {
        Log logger = new Log();
        try {
            if (repository.findByEmail(request.getEmail()).isPresent()) {
                logger.setCreateTime(new Timestamp(System.currentTimeMillis()));
                logger.setLogString("Đăng kí thất bại: Email đã tồn tại");
                log.error("Registration failed: Email {} already exists", request.getEmail());
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
                log.info("User registered successfully with email: {}", request.getEmail());

                return AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        } catch (Exception e) {
            logger.setCreateTime(new Timestamp(System.currentTimeMillis()));
            logger.setLogString("Đăng kí thất bại: " + e.getMessage());
            log.error("Registration failed for email: {}. Error: {}", request.getEmail(), e.getMessage());
            throw e;
        }
    }
    public AuthenticationResponse login(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            log.info("User login successful for email: {}", request.getEmail());
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception e) {
            log.error("Login failed for email: {}. Error: {}", request.getEmail(), e.getMessage());
            throw e;
        }
    }

    private void saveUserToken(NguoiDung user, String jwtToken) {
        var token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        log.info("Saving token for user with email: {}", user.getEmail());
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(NguoiDung user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            log.info("No valid tokens found to revoke for user with email: {}", user.getEmail());
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        log.info("Revoked all tokens for user with email: {}", user.getEmail());
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            log.warn("Invalid or missing Authorization header");
            return;
        }
        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractEmail(refreshToken);

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
                log.info("Token refreshed successfully for user: {}", userEmail);
            } else {
                log.error("Invalid token for user: {}", userEmail);
            }
        } else {
            log.error("Unable to extract user email from token");
        }
    }
}
