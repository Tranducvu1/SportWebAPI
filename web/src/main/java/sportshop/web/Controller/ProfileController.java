package sportshop.web.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import sportshop.web.DTO.PasswordDTO;
import sportshop.web.Entity.NguoiDung;
import sportshop.web.Entity.ResponseObject;
import sportshop.web.Service.UserService;



public class ProfileController {


@RestController
@RequestMapping("/api/v1/profile")
public class ProfileApi {

	@Autowired
	private UserService nguoiDungService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/{id}")
	public NguoiDung getNguoiDungById(@PathVariable Long id) {
		NguoiDung nd = nguoiDungService.findById(id);
		return nd;
	}

	@PostMapping("/doiMatKhau")
	public ResponseObject changePass(@RequestBody @Valid PasswordDTO dto, BindingResult result,
			HttpServletRequest request) {
		System.out.println(dto.toString());
		NguoiDung currentUser = getSessionUser(request);

		ResponseObject ro = new ResponseObject();
		
		if (!passwordEncoder.matches( dto.getOldPassword(), currentUser.getPassword())) {
			result.rejectValue("oldPassword", "error.oldPassword", "Mật khẩu cũ không đúng");
		}

		if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
			result.rejectValue("confirmNewPassword", "error.confirmNewPassword", "Nhắc lại mật khẩu mới không đúng");
		}

		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
		    List<FieldError> errorsList = result.getFieldErrors();
		    for (FieldError error : errorsList ) {
		        errors.put(error.getField(), error.getDefaultMessage());
		    }
			ro.setErrorMessages(errors);
			ro.setStatus("fail");
			errors = null;
		} else {
			nguoiDungService.changePass(currentUser, dto.getNewPassword());
			ro.setStatus("success");
		}
		
		return ro;
	}

	public NguoiDung getSessionUser(HttpServletRequest request) {
		return (NguoiDung) request.getSession().getAttribute("loggedInUser");
	}
}

}
