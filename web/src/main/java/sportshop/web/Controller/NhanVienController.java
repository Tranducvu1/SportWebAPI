package sportshop.web.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sportshop.web.Model.NhanVien;
import sportshop.web.Service.NhanVienService;

@RestController
@RequestMapping("/api/nhanvien")
public class NhanVienController {

	
	@Autowired
	private NhanVienService nhanVienService;
	
	@GetMapping()
	public ResponseEntity<Object>  findAll(){
		return ResponseEntity.ok(nhanVienService);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Object> searchByKeyWord(@RequestParam(value = "keyword")String keyword){
		return ResponseEntity.ok(nhanVienService.searchByKeyword(keyword));
	}
	@PostMapping(path = "/create", produces = "application/json;charset = utf-8")
	public ResponseEntity<Boolean>  tao(@RequestBody NhanVien NhanVien){
		Boolean result = nhanVienService.save(NhanVien);
		return ResponseEntity.ok(result);
	}
	@PutMapping(path = "/update/{id}", produces = "application/json;charset = utf-8")
	public ResponseEntity<Boolean>  update(@RequestBody @Valid NhanVien NhanVien){
		Boolean result = nhanVienService.update(NhanVien);
		return ResponseEntity.ok(result);
	} 
}

