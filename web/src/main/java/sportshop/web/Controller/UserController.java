//package sportshop.web.Controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import sportshop.web.Model.NguoiDung;
//import sportshop.web.Service.UserService;
//
//@RestController
//@RequestMapping("/api")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/find")
//    public ResponseEntity<?> findUserByUsername(@RequestParam String hoten) {
//        try {
//            NguoiDung nguoiDung = userService.findByUserName(hoten);
//            return ResponseEntity.ok(nguoiDung);
//        } catch (UsernameNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }
//}