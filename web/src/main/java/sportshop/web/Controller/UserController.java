package sportshop.web.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sportshop.web.Entity.NguoiDung;
import sportshop.web.Service.NguoiDungService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestParam String hoten, @RequestParam String email) {
        try {
            // Tìm tất cả người dùng trùng tên
            List<NguoiDung> users = nguoiDungService.findByhoten(hoten);

            // Kiểm tra nếu có hơn 2 người dùng với tên này
            if (users.size() > 2) {
                // Nếu có hơn 2 người, tìm người dùng có email trùng
                NguoiDung user = users.stream()
                    .filter(u -> u.getEmail().equals(email))
                    .findFirst()
                    .orElse(null); // Trả về null nếu không tìm thấy người dùng nào với email này

                // Kiểm tra nếu không tìm thấy người dùng có email trùng
                if (user == null) {
                    return ResponseEntity.notFound().build(); // Không tìm thấy
                }

                // Trả về thông tin người dùng nếu tìm thấy
                return ResponseEntity.ok(Map.of(
                    "id", user.getId(),
                    "hoten", user.getHoten(),
                    "email", user.getEmail(),
                    "sdt", user.getSo_dien_thoai(),
                    "diachi", user.getAddress()
                ));
            } else {
                // Trường hợp tên chỉ có 1 người, trả về thông tin
                NguoiDung user = users.get(0); // Giả sử chỉ có 1 người trùng tên
                return ResponseEntity.ok(Map.of(
                    "id", user.getId(),
                    "hoten", user.getHoten(),
                    "email", user.getEmail(),
                    "phone", user.getSo_dien_thoai(),
                    "address", user.getAddress()
                ));
            }

        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.notFound().build();  // Nếu không tìm thấy người dùng
        }
    }
}
