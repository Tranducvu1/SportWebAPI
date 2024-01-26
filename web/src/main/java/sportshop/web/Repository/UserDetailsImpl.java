//package sportshop.web.Repository;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collector;
//import java.util.stream.Collectors;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//
//import sportshop.web.Model.Account;
//
//import org.springframework.security.core.userdetails.UserDetails;
//public class UserDetailsImpl implements UserDetails {
//private static final long serialVersionUID = 1L;
//	
//	private Long id;
//
//	  private String username;
//
//	  private String email;
//	  
//	  @JsonIgnore
//	  private String password;
//
//	public Collection<? extends GrantedAuthority> auth;
//	
//	public UserDetailsImpl(Long id, String username, String email, String password,Collection<? extends GrantedAuthority> auth) {
//		this.id = id;
//	    this.username = username;
//	    this.email = email;
//	    this.password = password;
//	    this.auth = auth;
//	}
//	
//	public static UserDetailsImpl build(Account account) {
//		List<GrantedAuthority>  auth = account.getRoles().stream()
//				.map(role -> new SimpleGrantedAuthority(role.getRole().name()))
//				.collect(Collectors.toList());
//		
//		return new UserDetailsImpl(
//		        account.getId(), 
//		        account.getUsername(), 
//		        account.getEmail(),
//		        account.getPassword(), 
//		        auth);
//		  }
//	
//	@Override
//	  public Collection<? extends GrantedAuthority> getAuthorities() {
//	    return auth;
//	  }
//
//	public Long getId() {
//	    return id;
//	  }
//
//	  public String getEmail() {
//	    return email;
//	  }
//	  
//	@Override
//	public String getPassword() {
//		// TODO Auto-generated method stub
//		return password;
//	}
//
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return username;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//	@Override
//	  public boolean equals(Object o) {
//		if(this == o )
//			return true;
//		if(o == null || getClass() != o.getClass())
//			return false;
//		UserDetailsImpl user = (UserDetailsImpl) o;
//		return Objects.equals(id, user.id);
//		
//	}
//}
