package sportshop.web.Controller;





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

import jakarta.validation.Valid;
import sportshop.web.Model.DonHang;
import sportshop.web.Model.NguoiDung;
import sportshop.web.Service.DonHangService;
import sportshop.web.Service.EmailService;

@RestController
@RequestMapping("/api/donhang")
public class DonHangController {
 @Autowired
 private DonHangService donhangservice;
 @Autowired
 private EmailService emailService;
	@GetMapping()
 public ResponseEntity<Object> findall(){
	return ResponseEntity.ok(donhangservice.findAll());	 
 }


@GetMapping("/search")
public ResponseEntity<Object> findByKeyWord(@RequestParam(value = "keyword",required = false) String keyword){		
	return ResponseEntity.ok(donhangservice.searchByKeyword(keyword)); 
}

//@PostMapping(path="/create",produces = "application/json;charset=utf-8")
//public ResponseEntity<Boolean>  taomoi(@RequestBody DonHang donhang){
//	Boolean result = donhangservice.save(donhang);
//	return ResponseEntity.ok(result);
//	
//}
	@PostMapping(path = "/create", produces = "application/json;charset=utf-8")
    public ResponseEntity<Boolean> create(@RequestBody DonHang donhang) {
        Boolean result = donhangservice.save(donhang);


        return ResponseEntity.ok(result);
    }




@PutMapping(path="/update/{id}",produces ="application/json;charset = utf-8")
public ResponseEntity<Boolean> capnhat(@RequestBody @Valid DonHang donhang ){
	Boolean result = donhangservice.update(donhang);
	return ResponseEntity.ok(result);
}
}
