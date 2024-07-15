//package sportshop.web.Service;
//
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import sportshop.web.Model.NguoiDung;
//
//public class UserDetailsImpl implements UserDetails {
//	private static final long serialVersionUID = 1L;
//	
//	private int id;
//
//	 private String username;
//
//	  private String email;
//	  
//	  @JsonIgnore
//	  private String password;
//	  
//	  private Collection<? extends GrantedAuthority> authorities;
//
//	  public UserDetailsImpl(int i, String username, String email, String password,
//	      Collection<? extends GrantedAuthority> authorities) {
//	    this.id = i;
//	    this.username = username;
//	    this.email = email;
//	    this.password = password;
//	    this.authorities = authorities;
//	  }
//	  
//	  public static UserDetailsImpl build(NguoiDung account) {
//		    List<GrantedAuthority> authorities = account.getVaiTro().stream()
//		        .map(role -> new SimpleGrantedAuthority(role.getTenVaiTro().name()))
//		        .collect(Collectors.toList());
//
//		    return new UserDetailsImpl(
//		        account.getId(), 
//		        account.getLastname(), 
//		        account.getEmail(),
//		        account.getPassword(), 
//		        authorities);
//		  }
//	  
//	  @Override
//	  public Collection<? extends GrantedAuthority> getAuthorities() {
//	    return authorities;
//	  }
//
//	  public int getId() {
//	    return id;
//	  }
//
//	  public String getEmail() {
//	    return email;
//	  }
//
//	  @Override
//	  public String getPassword() {
//	    return password;
//	  }
//
//	  @Override
//	  public String getUsername() {
//	    return username;
//	  }
//
//	  @Override
//	  public boolean isAccountNonExpired() {
//	    return true;
//	  }
//
//	  @Override
//	  public boolean isAccountNonLocked() {
//	    return true;
//	  }
//
//	  @Override
//	  public boolean isCredentialsNonExpired() {
//	    return true;
//	  }
//
//	  @Override
//	  public boolean isEnabled() {
//	    return true;
//	  }
//
//	  @Override
//	  public boolean equals(Object o) {
//	    if (this == o)
//	      return true;
//	    if (o == null || getClass() != o.getClass())
//	      return false;
//	    UserDetailsImpl user = (UserDetailsImpl) o;
//	    return Objects.equals(id, user.id);
//	  }
//
//}