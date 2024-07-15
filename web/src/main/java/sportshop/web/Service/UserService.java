package sportshop.web.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sportshop.web.Model.NguoiDung;
import sportshop.web.Repository.UserRepository;

@Service
@Transactional
public class UserService {
@Autowired
private UserRepository userRepo;
 @Autowired
private PasswordEncoder passwordEncoder;

   
    public NguoiDung findByEmail(String email) {
        return userRepo.findByEmail(email)
        		 .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public NguoiDung findByConfirmationToken(String confirmationToken) {
        // Implement this method if needed
        return null;
    }
//
//    public NguoiDung saveUserForMember(NguoiDung nd) {
//        nd.setPassword(bCryptPasswordEncoder.encode(nd.getPassword()));
//        Set<Role> setVaiTro = new HashSet<>();
//        setVaiTro.add(vaiTroRepo.findByTenVaiTro("MEMBER"));
//        nd.setVaiTro(setVaiTro);
//        return userRepo.save(nd);
//    }

    
    
    public NguoiDung findById(Integer id) {
        return userRepo.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    public NguoiDung updateUser(NguoiDung nd) {
        return userRepo.save(nd);
    }

    public void changePass(NguoiDung nd, String newPass) {
        nd.setPassword(passwordEncoder.encode(newPass));
        userRepo.save(nd);
    }

//    public Page<NguoiDung> getUserByVaiTro(Set<VaiTro> vaiTro, int page) {
//        return userRepo.findByVaiTro(vaiTro, PageRequest.of(page - 1, 6));
//    }
//
//    public VaiTro getUserByVaiTro(String tenVaiTro) {
//        return userRepo.findByVaiTro(tenVaiTro);
//    }

//    public NguoiDung saveUserForAdmin(TaiKhoanDTO dto) {
//        NguoiDung nd = new NguoiDung();
//        nd.setHoTen(dto.getHoTen());
//        nd.setDiaChi(dto.getDiaChi());
//        nd.setEmail(dto.getEmail());
//        nd.setSoDienThoai(dto.getSdt());
//        nd.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
//
//        Set<VaiTro> vaiTro = new HashSet<>();
//        vaiTro.add(vaiTroRepo.findByTenVaiTro(dto.getTenVaiTro()));
//        nd.setVaiTro(vaiTro);
//
//        return userRepo.save(nd);
//    }

    public void deleteById(Integer id) {
        userRepo.deleteById(id);
    }

	
}