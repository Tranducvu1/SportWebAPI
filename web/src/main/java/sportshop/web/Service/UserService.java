package sportshop.web.Service;

import sportshop.web.Entity.NguoiDung;

import java.util.List;

public interface UserService {
    List<NguoiDung> findAll();

    NguoiDung findByEmail(String email);

    NguoiDung findById(Long id);

    NguoiDung updateUser(NguoiDung nd);

    void changePass(NguoiDung nd, String newPass);

    void deleteById(Long id);

    NguoiDung searchByEmail(String username);
}
