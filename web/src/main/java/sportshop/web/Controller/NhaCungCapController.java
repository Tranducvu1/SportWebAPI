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


import sportshop.web.Model.NhaCungCap;
import sportshop.web.Service.NhaCungCapService;

@RestController
@RequestMapping("/api/nhacungcap")
public class NhaCungCapController {

	
	@Autowired
	private NhaCungCapService nhaCungCapService;
	
	@GetMapping()
	public ResponseEntity<Object>  findAll(){
		return ResponseEntity.ok(nhaCungCapService);
	}
	
	@GetMapping("/search")
	public ResponseEntity<Object> searchByKeyWord(@RequestParam(value = "keyword")String keyword){
		return ResponseEntity.ok(nhaCungCapService.searchByKeyword(keyword));
		
	}
	
	@PostMapping(path = "/create", produces = "application/json;charset = utf-8")
	public ResponseEntity<Boolean>  tao(@RequestBody NhaCungCap nhaCungCap){
		Boolean result = nhaCungCapService.save(nhaCungCap);
		return ResponseEntity.ok(result);
	}
	
	@PutMapping(path = "/update/{id}", produces = "application/json;charset = utf-8")
	public ResponseEntity<Boolean>  update(@RequestBody @Valid NhaCungCap nhaCungCap){
		Boolean result = nhaCungCapService.update(nhaCungCap);
		return ResponseEntity.ok(result);
	} 
}
