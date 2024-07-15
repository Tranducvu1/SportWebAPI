package sportshop.web.Controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sportshop.web.Model.DanhMuc;
import sportshop.web.Model.MatHang;
import sportshop.web.Service.DanhMucService;


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
	System.out.println(danhmuc);
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


@PutMapping(path="/danhmuc/update",  consumes = "application/json",produces ="application/json;charset = utf-8")
public ResponseEntity<Boolean> capnhat(@RequestBody @Valid DanhMuc danhmuc){
	Boolean result = danhMucService.update(danhmuc);
	return ResponseEntity.ok(result);
	
}


@GetMapping("/danhmuc/{id}")
public DanhMuc getDanhMucById(@PathVariable("id") Integer id) {
	return danhMucService.getById(id);
}




@GetMapping("/danhmuc/search")
public ResponseEntity<Object> findByKeyWord(@RequestParam(value = "keyword",required = false) String keyword){		
	return ResponseEntity.ok(danhMucService.searchByKeyword(keyword)); 
}

@DeleteMapping("/danhmuc/delete/{id}")
public ResponseEntity<String> deleteProducts(@PathVariable Integer id) {
    try {
        Boolean isDeleted = danhMucService.deleteProduct(id);
        if(isDeleted) {
            return ResponseEntity.ok("Xóa thành công");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh mục không tồn tại");
        }
    } catch (Exception e) {
        // Log the exception
        e.printStackTrace(); // This will print the stack trace to your server logs
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Đã xảy ra lỗi: " + e.getMessage());
    }
}


@GetMapping("/danhmuc/pagination/{offset}/{pageSize}")
public ResponseEntity<Page<DanhMuc>> DanhMucPagination(@PathVariable int offset,@PathVariable int pageSize){
	Page<DanhMuc> dm = danhMucService.getDanhMucPagination(offset, pageSize);
	return ResponseEntity.ok(dm);
	
}


}
