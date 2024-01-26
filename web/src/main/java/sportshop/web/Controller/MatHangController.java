package sportshop.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sportshop.web.Model.MatHang;
import sportshop.web.Service.MatHangService;

@RestController
@RequestMapping("/api/mathang")
public class MatHangController {
	@Autowired
	private MatHangService matHangService;
	
	@GetMapping()
	public ResponseEntity<Object> findAll(){
		
		return ResponseEntity.ok(matHangService.findAll());
	}
	
	
	@GetMapping("/search")
	public ResponseEntity<Object> SearchByKeyWord(@RequestParam(value = "keyword",required = false)String keyword ){
		
		return ResponseEntity.ok(matHangService.searchByKeyword(keyword));
	}
	
	@PostMapping(path = "/create",produces = "application/json;charset = utf-8")
	public ResponseEntity<Boolean> taomoi (@RequestBody MatHang mathang){
		Boolean result = matHangService.save(mathang);
		return ResponseEntity.ok(result);
		
	}
	
	@PutMapping(path = "/update/{id}",  consumes = "application/json",produces ="application/json;charset = utf-8")
	public ResponseEntity<Boolean> update (@RequestBody @Valid MatHang mathang){
		Boolean rs = matHangService.update(mathang);
		return ResponseEntity.ok(rs);
	}
	
}
