package sportshop.web.Config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import sportshop.web.Service.ipml.UserDetailsServiceImpl;
	
	@Component
	@RequiredArgsConstructor
	public class AuthTokenFilter extends OncePerRequestFilter{
		@Autowired
		private JwtUtils jwtUtils;
		@Autowired
		private UserDetailsServiceImpl userDetailsService;
		@Override
		protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
				throws ServletException, IOException {
	//Filter focuses only on its main task of validating JWT tokens for requests that need validation.
				final String authHeader = request.getHeader("Authorization");
				System.out.println("nanan1"+authHeader);
				final String jwt ;
				final String userEmail;
				if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			            filterChain.doFilter(request, response);
			            return;
			        }
				
				System.out.println("nanan"+authHeader);
					jwt = authHeader.substring(7);
					userEmail = jwtUtils.extractEmail(jwt);
					System.out.println("gg"+userEmail);
					if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
						UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
						System.out.println(userDetails);
						//if(!jwtUtils.isTokenValid(userEmail, userDetails)) {
							UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(
									userDetails
									,null,
									userDetails.getAuthorities()
									);
							authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
							SecurityContextHolder.getContext().setAuthentication(authtoken);
						//}
					}
					System.out.println(request+"hh"+ response);
					 filterChain.doFilter(request, response);	
			}
		}
	
			
		
	
	

