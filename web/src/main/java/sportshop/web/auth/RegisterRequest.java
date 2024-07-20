package sportshop.web.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sportshop.web.DTO.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String hoten;
  private String address;
  private String so_dien_thoai ;
  private String email;
  private String password;
  private String confirm_password ;
  private Role role;
}