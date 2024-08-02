package sportshop.web.auth;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sportshop.web.DTO.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;
  private Long id;
  private Role role;
  private String email;
  private String hoten;
  private String password;
  private String confirm_password;
  private String so_dien_thoai;
  private String address;
  private String dayofbirth;
  private String Gender;
}
