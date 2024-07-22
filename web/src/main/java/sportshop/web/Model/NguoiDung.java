package sportshop.web.Model;



import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sportshop.web.DTO.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nguoidung")
public class NguoiDung implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String hoten;
  private String password;
  private String confirm_password;
  private String so_dien_thoai;
  private String address;
  private String dayofbirth;
  private String Gender;
  @Enumerated(EnumType.STRING)
  private Role role;
  @JsonManagedReference
  @OneToMany(mappedBy = "nguoidung")
  private List<Token> tokens;

  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

@Override
public String toString() {
	return "NguoiDung [id=" + id + ", email=" + email + ", hoten=" + hoten + ", password=" + password
			+ ", confirm_password=" + confirm_password + ", so_dien_thoai=" + so_dien_thoai + ", address=" + address
			+ ", dayofbirth=" + dayofbirth + ", Gender=" + Gender + ", role=" + role + ", tokens=" + tokens + "]";
}
  
  

}