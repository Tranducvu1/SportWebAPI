package sportshop.web.DTO;



import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDTO {

	@NotEmpty(message = "Phải nhập mật khẩu cũ")
	private String oldPassword;

	@NotEmpty(message = "Phải nhập mật khẩu mới")
	@Length(min=8, max=32, message="Mật khẩu phải dài 8-32 ký tự")
	private String newPassword;

	@NotEmpty(message = "Phải nhắc lại mật khẩu mới")
	private String confirmNewPassword;

}
