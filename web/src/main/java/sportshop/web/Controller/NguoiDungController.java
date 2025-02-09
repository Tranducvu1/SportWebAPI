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

    // Get all users (with optional filters)
    @GetMapping
    public ResponseEntity<List<NguoiDung>> getAllUsers(
            @RequestParam(required = false) String email, 
            @RequestParam(required = false) String hoten, 
            @RequestParam(required = false) String role) {

        List<NguoiDung> users;
        
        // If query parameters are provided, filter based on them
        if (email != null || hoten != null || role != null) {
            users = nguoiDungService.filterUsers(email, hoten, role);
        } else {
            users = nguoiDungService.getAllUsers();
        }
        return ResponseEntity.ok(users);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<NguoiDung> getUserById(@PathVariable Long id) {
        NguoiDung user = nguoiDungService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // Add new user
    @PostMapping("/create")
    public ResponseEntity<NguoiDung> createUser(@RequestBody NguoiDung nguoiDung) {
        return ResponseEntity.ok(nguoiDungService.createUser(nguoiDung));
    }

    // Update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<NguoiDung> updateUser(@PathVariable Long id, @RequestBody NguoiDung nguoiDung) {
        NguoiDung updatedUser = nguoiDungService.updateUser(id, nguoiDung);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        nguoiDungService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
