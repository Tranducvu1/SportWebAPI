package sportshop.web.Controller;


import sportshop.web.Model.DanhMuc;
import sportshop.web.Model.DonHang;
import sportshop.web.Model.MatHang;
import sportshop.web.Service.DanhMucService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class DanhmucController {
 @Autowired
 private DanhMucService danhMucService;
 
 @GetMapping("/danhmuc")
 public ResponseEntity<Object> findall(){
	return ResponseEntity.ok(danhMucService.findAll());	 
 }


@PostMapping(path="/danhmuc/create",produces = "application/json;charset=utf-8")
public ResponseEntity<Boolean>  taomoi(@RequestBody DanhMuc danhmuc){
	Boolean result = danhMucService.save(danhmuc);
	return ResponseEntity.ok(result);
	
}

@GetMapping("/danhmuc/{danhMucId}/products/{productId}")
public ResponseEntity<Object> getIdDanhMuc(@PathVariable("danhMucId") Integer danhMucId, 
                                           @PathVariable("productId") Integer productId) {
    DanhMuc danhmuc = danhMucService.getById(danhMucId);
    if (danhmuc != null) {
        MatHang product = danhMucService.getProductByIdFromCategory(danhmuc, productId);
        return ResponseEntity.ok(product);
    } else {
        return ResponseEntity.notFound().build();
    }
}


@PutMapping(path="/danhmuc/update/{id}",produces ="application/json;charset = utf-8")
public ResponseEntity<Boolean> capnhat(@RequestBody @Valid DanhMuc danhmuc){
	Boolean result = danhMucService.update(danhmuc);
	return ResponseEntity.ok(result);
	
}

@GetMapping("/danhmuc/search")
public ResponseEntity<Object> findByKeyWord(@RequestParam(value = "keyword",required = false) String keyword){		
	return ResponseEntity.ok(danhMucService.searchByKeyword(keyword)); 
}
}
