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

import sportshop.web.Model.HangSanXuat;
import sportshop.web.Service.HangSanXuatService;



@RestController
@RequestMapping("/api/hangsanxuat")
public class HangSanXuatController {

	
	@Autowired
	private HangSanXuatService hangSanXuatService;
	
	@GetMapping()
	public ResponseEntity<Object>  findAll(){
		return ResponseEntity.ok(hangSanXuatService);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Object> searchByKeyWord(@RequestParam(value = "keyword")String keyword){
		return ResponseEntity.ok(hangSanXuatService.searchByKeyword(keyword));
		
	}
	
	@PostMapping(path = "/create", produces = "application/json;charset = utf-8")
	public ResponseEntity<Boolean>  tao(@RequestBody HangSanXuat HangSanXuat){
		Boolean result = hangSanXuatService.save(HangSanXuat);
		return ResponseEntity.ok(result);
	}
	
	@PutMapping(path = "/update/{id}", produces = "application/json;charset = utf-8")
	public ResponseEntity<Boolean>  update(@RequestBody @Valid HangSanXuat HangSanXuat){
		Boolean result = hangSanXuatService.update(HangSanXuat);
		return ResponseEntity.ok(result);
	} 
}