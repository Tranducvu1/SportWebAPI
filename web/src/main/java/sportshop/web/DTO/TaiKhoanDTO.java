package sportshop.web.DTO;



import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TaiKhoanDTO {
	private String id;
	
	@NotEmpty(message="Phải nhập địa chỉ email")
	@Email(message= "Phải nhập đúng địa chỉ email")
	private String email;

	@Length(min=8, max=32, message="mật khẩu phải dài 8-32 ký tự")
	private String password;
	
	private String confirmPassword;
	
	@NotEmpty(message="Địa chỉ không được trống")
	private String diaChi;
	
	private String dayofbirth;
	
	private String gender;
	
	@NotEmpty(message="Họ tên không được trống")
	private String hoTen;
	
	@NotEmpty(message="Số điện thoại không được trống")
	private String sdt;
	private String tenVaiTro;
}