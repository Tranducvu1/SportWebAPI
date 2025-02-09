package sportshop.web.Service.ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sportshop.web.Entity.NguoiDung;
import sportshop.web.Repository.UserRepository;
import sportshop.web.Service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<NguoiDung> findAll() {
        return userRepository.findAll();
    }

    @Override
    public NguoiDung findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public NguoiDung findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public NguoiDung updateUser(NguoiDung nd) {
        return userRepository.save(nd);
    }

    @Override
    public void changePass(NguoiDung nd, String newPass) {
        nd.setPassword(newPass);
        userRepository.save(nd);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public NguoiDung searchByEmail(String username) {
        return userRepository.findByEmail(username).orElse(null);
    }
    
 
}
