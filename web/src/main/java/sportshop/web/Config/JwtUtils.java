package sportshop.web.Config;



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

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	 @Value("${sport.app.jwtSecret}")
	// Secret key used for signing JWTs
	 private String jwtSecret;
	
	 @Value("${sport.app.ExpirationMs}")
	// JWT expiration time in milliseconds
	 private int ExpirationMs;
	
	 @Value("${sport.app.refresh-token.Expiration}")
	// Refresh token expiration time in milliseconds
	 private int refreshExpiration;
	 /** 
	 * Extract username from the Jwt token  
	 * @param token the jwt token
	 * @return the username extracted the token
	 */ 
	public String extractUsername(String token) {
	    return extractClaim(token, Claims::getSubject);
	  }

	/**
	 * Extract a specific claim from the Jwt token using a  claim resolver function
	 * @param <T> the type of claim
	 * @param token the Jwt token 
	 * @param claimsResolver a function to resolver the claim
	 * @return the extracted the claim
	 */
	  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	    final Claims claims = extractAllClaims(token);
	    return claimsResolver.apply(claims);
	  }
	  /**
	   * Extracts all claims from the JWT token.
	   * @param token the JWT token
	   * @return the claims
	   */
	  private Claims extractAllClaims(String token) {
		    return Jwts
		        .parserBuilder()
		        .setSigningKey(getSignInKey())
		        .build()
		        .parseClaimsJws(token)
		        .getBody();
		  }

	  /**
	   * Decodes the secret key from Base64 and returns the signing key.
	   * @return the signing key
	   */
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
		
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	
	  public String generateToken(UserDetails userDetails) {
		    return generateToken(new HashMap<>(), userDetails);
	  }
/**
 * Generate a refresh token for give user detials
 * @param userDetails the user Details
 * @return the generate refresh token
 */

	 public String generateToken(
		      Map<String, Object> extraClaims,
		      UserDetails userDetails
		  ) {
		    return buildToken(extraClaims, userDetails, ExpirationMs);
		  }

	/**
	   * Checks if the JWT token is expired.
	   * @param token the JWT token
	   * @return true if the token is expired, false otherwise
	   */
	public boolean isTokenExpired (String token) {
		return  extractExpiration(token).before(new Date());
		
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
	    final String username = extractUsername(token);
	    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	  }

/**
 * Extract the expiration date from the Jwt
 * @param token the Jwt token
 * @return the expiration date
 */
	private Date extractExpiration(String token) {
		
		return extractClaim(token,Claims::getExpiration);
	}
	 /**
	   * Generates a refresh token for the given user details.
	   * @param userDetails the user details
	   * @return the generated refresh token
	   */
	  public String generateRefreshToken(
	      UserDetails userDetails
	  ) {
	    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
	  }
	  /**
	   * Builds a JWT token with the specified claims, user details, and expiration time.
	   * @param extraClaims additional claims to be included in the token
	   * @param userDetails the user details
	   * @param expirationMs2 the expiration time in milliseconds
	   * @return the generated JWT token
	   */
	private String buildToken(Map<String, Object> extraclaims,UserDetails userDetails,int expirationMs2){
	return Jwts.builder()
		            .setClaims(extraclaims)
		            .setSubject(userDetails.getUsername())
		            .setIssuedAt(new Date(System.currentTimeMillis()))
		            .setExpiration(new Date(System.currentTimeMillis() + expirationMs2))
		            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
		            .compact();	
	}
	
	
}
