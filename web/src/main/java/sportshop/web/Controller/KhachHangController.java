package sportshop.web.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import sportshop.web.Model.KhachHang;
import sportshop.web.Service.KhachHangService;



@RestController
@RequestMapping("/api/khachhang")
public class KhachHangController {
	@Autowired
	private KhachHangService khachHangService;
	
	
	@GetMapping()
	 @PreAuthorize("hasRole('MEMBER')")
	public ResponseEntity<Object> findAll(){
		return ResponseEntity.ok(khachHangService.findAll());
	}
	
	@GetMapping("/search")
	 @PreAuthorize("hasRole('MEMBER')")
	public ResponseEntity<Object> SearchByKeyWord(@RequestParam(value = "keyword",required = false)String keyword){
		return ResponseEntity.ok(khachHangService.searchByKeyword(keyword));
	}
	
	@PostMapping(path = "/create",produces = "application/json;charset = utf-8")
	 @PreAuthorize("hasRole('MEMBER')")
	public ResponseEntity<Boolean>  create (@RequestBody KhachHang khachhang){
		Boolean result = khachHangService.save(khachhang);
		
		return ResponseEntity.ok(result); 
	}
	
	@PutMapping(path = "/update/{id}",produces = "application/json;charset = utf-8")
	 @PreAuthorize("hasRole('MEMBER')")
	public ResponseEntity<Boolean>  update (@RequestBody @Valid KhachHang khachhang){
		Boolean rs = khachHangService.save(khachhang);
		
		return ResponseEntity.ok(rs); 
	}
}
