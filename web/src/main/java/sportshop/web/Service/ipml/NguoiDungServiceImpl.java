package sportshop.web.Service.ipml;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sportshop.web.DTO.Role;
import sportshop.web.Entity.NguoiDung;
import sportshop.web.Repository.NguoiDungRepository;
import sportshop.web.Service.NguoiDungService;

@Service
public class NguoiDungServiceImpl implements NguoiDungService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override
    public List<NguoiDung> getAllUsers() {
        return nguoiDungRepository.findAll();
    }

    @Override
    public NguoiDung getUserById(Long id) {
        Optional<NguoiDung> optionalUser = nguoiDungRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public List<NguoiDung> filterUsers(String email, String hoten, String role) {
        // Example logic for filtering
        return nguoiDungRepository.findByEmailContainingAndHotenContainingAndRole(email, hoten, Role.valueOf(role));
    }

    
    @Override
    public NguoiDung createUser(NguoiDung nguoiDung) {
        return nguoiDungRepository.save(nguoiDung);
    }

    @Override
    public NguoiDung updateUser(Long id, NguoiDung nguoiDung) {
        Optional<NguoiDung> optionalUser = nguoiDungRepository.findById(id);
        if (optionalUser.isPresent()) {
            NguoiDung existingUser = optionalUser.get();
            existingUser.setHoten(nguoiDung.getHoten());
            existingUser.setEmail(nguoiDung.getEmail());
            existingUser.setPassword(nguoiDung.getPassword());
            existingUser.setAddress(nguoiDung.getAddress());
            existingUser.setSo_dien_thoai(nguoiDung.getSo_dien_thoai());
            existingUser.setDayofbirth(nguoiDung.getDayofbirth());
            existingUser.setGender(nguoiDung.getGender());
            existingUser.setRole(nguoiDung.getRole());
            return nguoiDungRepository.save(existingUser);
        }
        return null;
    }
    
    @Override
    public void deleteUser(Long id) {
        nguoiDungRepository.deleteById(id);
    }

    @Override
    public NguoiDung findByEmail(String email) {
        return nguoiDungRepository.findByEmail(email);
    }
    
    @Override
    public List<NguoiDung> findByhoten(String username) {
    	return nguoiDungRepository.findByhoten(username);
    }
    
}
