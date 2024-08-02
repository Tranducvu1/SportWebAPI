package sportshop.web.Controller;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sportshop.web.Model.Banner;
import sportshop.web.Service.BannerService;

@RestController
@RequestMapping("/api/banner")
public class BannerController {
 @Autowired
 private BannerService bannerservice;
 
 @GetMapping()
 public ResponseEntity<Object> findall(){
	return ResponseEntity.ok(bannerservice.findAll());	 
 }



@PostMapping(path="/create",produces = "application/json;charset=utf-8")
public ResponseEntity<Boolean>  taomoi(@RequestBody Banner banner){
	Boolean result = bannerservice.save(banner);
	return ResponseEntity.ok(result);
	
}

@PutMapping(path="/update/{id}",produces ="application/json;charset = utf-8")
public ResponseEntity<Boolean> capnhat(@RequestBody @Valid Banner banner ){
	Boolean result = bannerservice.update(banner);
	return ResponseEntity.ok(result);
	
}
//@GetMapping("/mathang/pagination/{offset}/{pageSize}")
//private ResponseEntity<Page<MatHang>> getProductsWithSort(@PathVariable int offset, @PathVariable int pageSize,@RequestParam(required = false) Integer danhMucId){
//	Page<MatHang> mathang = matHangService.getMatHangByDanhMuc(offset, pageSize);
//	
//	return ResponseEntity.ok(mathang);
//
 @PreAuthorize("hasRole('ADMIN')")
@GetMapping("/find/{id}")
public ResponseEntity<Object> getIdDanhMuc(@PathVariable("id") Integer id) {
    Banner banner = bannerservice.getById(id);
    if (banner != null) {
     
        return ResponseEntity.ok(banner);
    } else {
        return ResponseEntity.notFound().build();
    }
}

@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteProducts(@PathVariable Integer id) throws Exception{
	try {
			Boolean isDeleted = bannerservice.deleteBanner(id);
			if(isDeleted) {
				return ResponseEntity.ok("Xóa thành công");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sản phẩm không tồn tại");
			}
	} catch (Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi");
	}
	
	
	
}


@GetMapping("/pagination/{offset}/{pageSize}")
private ResponseEntity<Page<Banner>> getBannerPagination(@PathVariable int offset,@PathVariable int pageSize){
	Page<Banner> banner = bannerservice.getbanner(offset, pageSize);
	return ResponseEntity.ok(banner);
}
}
