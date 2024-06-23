package sportshop.web.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sportshop.web.Model.DanhMuc;
import sportshop.web.Model.KhachHang;
import sportshop.web.Model.MatHang;
import sportshop.web.Service.MatHangService;

@RestController
@RequestMapping("/api")
public class MatHangController {
	@Autowired
	private MatHangService matHangService;
	@GetMapping("/mathang")
	public ResponseEntity<Object> findAll(){
		return ResponseEntity.ok(matHangService.findAll());
	}
	@GetMapping("/mathang/search/{keyword}")
	public ResponseEntity<Object> SearchByKeyWord(@RequestParam(value = "keyword",required = false)String keyword ){
	List<MatHang> result =	matHangService.searchByKeyword(keyword);
		return ResponseEntity.ok().body(result);
	}
		@GetMapping("/mathang/product/{id}")
		public ResponseEntity<Object> getIdDanhMuc(@PathVariable("id") Integer id) {
		    MatHang matHang = matHangService.getById(id);
		    if (matHang != null) {
		        String categoryName = matHang.getDanhMuc().getTendanhmuc();
		      //  System.out.println(categoryName);
		        return ResponseEntity.ok(matHang);
		    } else {
		        return ResponseEntity.notFound().build();
		    }
		}
		

		
		@PostMapping(path = "/mathang/create",produces = "application/json;charset = utf-8")
		public ResponseEntity<Boolean>  create (@RequestBody MatHang matHang) throws Exception{
			Boolean result = matHangService.save(matHang);
			
			return ResponseEntity.ok(result); 
		}
		
		
	@PutMapping(path = "/mathang/update/{id}",  consumes = "application/json",produces ="application/json;charset = utf-8")
	public ResponseEntity<Boolean> update (@RequestBody @Valid MatHang mathang){
		Boolean rs = matHangService.update(mathang);
		return ResponseEntity.ok(rs);
	}
	@GetMapping("/mathang/{field}")
	private ResponseEntity<List<MatHang>> getProductsWithSort(@PathVariable String field){
		List<MatHang> mathang = matHangService.findProductsWtihSorting(field);
		return ResponseEntity.ok(mathang);
		
	}
	//@GetMapping("/mathangbydanhmuc/{danhMucId}")
  //  public ResponseEntity<List<MatHang>> getMatHangByDanhMuc(@PathVariable Integer danhMucId) {
  //      DanhMuc danhMuc = new DanhMuc();
  //      danhMuc.setId(danhMucId);
     //   List<MatHang> matHangs = matHangService.getMatHangByDanhMuc(danhMuc);
     //   System.out.println(matHangs);
   //     return ResponseEntity.ok().body(matHangs);
  //  }
	@GetMapping("/mathang/pagination/{offset}/{pageSize}")
	private ResponseEntity<Page<MatHang>> getProductsWithSort(@PathVariable int offset, @PathVariable int pageSize){
		Page<MatHang> mathang = matHangService.getMatHangByDanhMuc(offset, pageSize);
		return ResponseEntity.ok(mathang);
		
	}
	
	 @GetMapping("/mathang/filterByPriceRange")
	    public List<MatHang> filterByPriceRange(
	    		@RequestParam(required = false) Integer danhMucId,
	    		@RequestParam(required = false) Integer nhaSXId,
	    		@RequestParam List<MatHang> products,
	    		@RequestParam(required = false) String priceRange,
	    		@RequestParam(required = false) String sortOrder) {
	        return matHangService.filterByPriceRange(danhMucId, nhaSXId, products, priceRange, sortOrder);
	    }
	
	@DeleteMapping("/mathang/delete/{id}")
	public ResponseEntity<String> deleteProducts(@PathVariable Integer id) throws Exception{
		try {
				Boolean isDeleted = matHangService.deleteProduct(id);
				if(isDeleted) {
					return ResponseEntity.ok("Xóa thành công");
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sản phẩm không tồn tại");
				}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi");
		}
		
		
	}
}
