package sportshop.web.Entity;
import java.util.Collection;
import java.util.List;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
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
import lombok.ToString;
import sportshop.web.DTO.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "nguoidung")
@ToString(exclude = {"tokens", "donHangs"})
public class NguoiDung implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String hinhdaidien;
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
  @OneToMany(mappedBy = "nguoidung", cascade = CascadeType.ALL)
  private List<Token> tokens;

  @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL)
  private List<DonHang> donHangs;

  
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
  

}