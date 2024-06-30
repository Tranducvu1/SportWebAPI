package sportshop.web.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sportshop.web.Model.NguoiDung;
import sportshop.web.Service.UserService;

public class NguoiDungValidator implements Validator {
@Autowired
private UserService userService; 
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return NguoiDung.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		NguoiDung user = (NguoiDung) target;
		
		
		ValidationUtils.rejectIfEmpty(errors, "emails", "error.hoTen","Họ tên không được bỏ trống");
		ValidationUtils.rejectIfEmpty(errors, "soDienThoai", "error.soDienThoai","Số điện thoại không được bỏ trống");
		ValidationUtils.rejectIfEmpty(errors, "diaChi", "error.diaChi","Địa chỉ không được bỏ trống");
		//validate cho email
		//validate check trống
		ValidationUtils.rejectIfEmpty(errors, "email", "error.email", "Email không được trống");

		
		Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		if(!pattern.matcher(user.getEmail()).matches()) {
			errors.rejectValue("email", "error.email", "Địa chỉ email không phù hợp");
		}
		
		//check emailo đã dùng chưa
		if(userService.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "error.email", "Địa chỉ email đã được sử dụng");
		}
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password","error.password","Password không được bỏ trống");
		// check confirmPassword trống hay không
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "error.confirmPassword",
						"Nhắc lại mật khẩu không được bỏ trống");
		//check độ dài password 
			if(user.getPassword().length() <8 || user.getPassword().length() >32) {
				errors.rejectValue("password", "error.password", "Mật khẩu phải dài 8-32 ký tự");

			}
			// check match pass và confirmPass
			if (!user.getConfirmPassword().equals(user.getPassword())) {
				errors.rejectValue("confirmPassword", "error.confirmPassword", "Nhắc lại mật khẩu không chính xác");
			}
	}

}
