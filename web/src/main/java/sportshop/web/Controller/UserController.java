package sportshop.web.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import sportshop.web.Model.NhanVien;
import sportshop.web.Model.User;
import sportshop.web.Service.NhanVienService;
import sportshop.web.Service.UserService;

public class UserController {

	
	@Autowired
	private UserService nhanVienService;
	
	@GetMapping()
	public ResponseEntity<Object>  findAll(){
		return ResponseEntity.ok(nhanVienService);
	}
	
	
	@PostMapping(path = "/create", produces = "application/json;charset = utf-8")
	public ResponseEntity<Boolean>  tao(@RequestBody User nguoidung){
		Boolean result = nhanVienService.save(nguoidung);
		return ResponseEntity.ok(result);
	}
	
	@PutMapping(path = "/update/{id}", produces = "application/json;charset = utf-8")
	public ResponseEntity<Boolean>  update(@RequestBody @Valid User nguoidung){
		Boolean result = nhanVienService.update(nguoidung);
		return ResponseEntity.ok(result);
	} 
}
