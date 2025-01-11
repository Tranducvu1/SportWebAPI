package sportshop.web.Config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    
    @Value("${sport.app.jwtSecret}")
	public String jwtSecret; // Secret key used for signing JWTs
    
    @Value("${sport.app.ExpirationMs}")
    private int ExpirationMs; // JWT expiration time in milliseconds
    
    @Value("${sport.app.refresh-token.Expiration}")
    private int refreshExpiration; // Refresh token expiration time in milliseconds

    // Extract username from the Jwt token  
    public String extractEmail(String token) {
    	
        return extractClaim(token, Claims::getSubject);
    }

    // Extract a specific claim from the Jwt token using a claim resolver function
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extracts all claims from the JWT token
    public Claims extractAllClaims(String token) {
        try {
            System.out.println("Token being processed: " + token);
            System.out.println("Number of periods in token: " + (token.length() - token.replace(".", "").length()));
            
            Key signingKey = getSignInKey();
            System.out.println("Signing key algorithm: " + signingKey.getAlgorithm());
            	System.out.println(token);
            return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                
                .getBody();
        } catch (Exception e) {
            System.out.println("Error parsing token: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    // Decodes the secret key from Base64 and returns the signing key
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
   
    // Generate a token
    public String generateToken(Object user) {
        return generateToken(new HashMap<>());
    }

    // Generate a refresh token for given user details
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, ExpirationMs);
    }

    // Checks if the JWT token is expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Extract the expiration date from the Jwt
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generates a refresh token for the given user details
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    // Builds a JWT token with the specified claims, user details, and expiration time
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, int expirationMs) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

//    public String getUserFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
//    }
    
    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }
   
    
    public String getUserNameFromJwtToken(String token) {
        		return Jwts.parserBuilder()
        				.setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(token)  
                        .getBody()
                        .getSubject();
    }
}
