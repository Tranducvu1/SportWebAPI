package sportshop.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sportshop.web.Entity.NguoiDung;
import sportshop.web.Service.NguoiDungService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class NguoiDungController {

    @Autowired
    private NguoiDungService nguoiDungService;

    // get all
    @GetMapping
    public ResponseEntity<List<NguoiDung>> getAllUsers() {
        return ResponseEntity.ok(nguoiDungService.getAllUsers());
    }

    // get use by id
    @GetMapping("/{id}")
    public ResponseEntity<NguoiDung> getUserById(@PathVariable Long id) {
        NguoiDung user = nguoiDungService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // addd
    @PostMapping
    public ResponseEntity<NguoiDung> createUser(@RequestBody NguoiDung nguoiDung) {
        return ResponseEntity.ok(nguoiDungService.createUser(nguoiDung));
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<NguoiDung> updateUser(@PathVariable Long id, @RequestBody NguoiDung nguoiDung) {
        NguoiDung updatedUser = nguoiDungService.updateUser(id, nguoiDung);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        nguoiDungService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
