package sportshop.web.Service;

import java.util.List;
import sportshop.web.Entity.NguoiDung;

public interface NguoiDungService {
    List<NguoiDung> getAllUsers();
    NguoiDung getUserById(Long userid);
    NguoiDung createUser(NguoiDung nguoiDung);
    NguoiDung updateUser(Long id, NguoiDung nguoiDung);
    void deleteUser(Long id);
    NguoiDung findByEmail(String email);
}