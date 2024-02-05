package sportshop.web.Controller;


import sportshop.web.Model.DanhMuc;
import sportshop.web.Model.DonHang;
import sportshop.web.Service.DanhMucService;

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
@RestController
@RequestMapping("/api/danhmuc")
public class DanhmucController {
 @Autowired
 private DanhMucService danhMucService;
 
 @GetMapping()
 public ResponseEntity<Object> findall(){
	return ResponseEntity.ok(danhMucService.findAll());	 
 }


@PostMapping(path="/create",produces = "application/json;charset=utf-8")
public ResponseEntity<Boolean>  taomoi(@RequestBody DanhMuc danhmuc){
	Boolean result = danhMucService.save(danhmuc);
	return ResponseEntity.ok(result);
	
}

@PutMapping(path="/update/{id}",produces ="application/json;charset = utf-8")
public ResponseEntity<Boolean> capnhat(@RequestBody @Valid DanhMuc danhmuc){
	Boolean result = danhMucService.update(danhmuc);
	return ResponseEntity.ok(result);
	
}
}
