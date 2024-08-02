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
  public RegisterRequest(Long id2, String hoten2, String email2, String so_dien_thoai2, String address2,
			String hoten3, String gender2, Object role2, String dayofbirth, String hinhdaidien2) {
		// TODO Auto-generated constructor stub
	}
private Long id;
  private String hoten;
  private String address;
  private String so_dien_thoai ;
  private String email;
  private String password;
  private String confirm_password ;
  private Role role;
  private String Gender;
  private String hinhDaiDien;
}