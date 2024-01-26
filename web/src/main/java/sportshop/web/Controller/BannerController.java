package sportshop.web.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sportshop.web.Model.Banner;
import sportshop.web.Service.BannerService;
import jakarta.validation.Valid;
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
}
