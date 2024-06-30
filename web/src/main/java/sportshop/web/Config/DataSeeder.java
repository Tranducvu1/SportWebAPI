package sportshop.web.Config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import sportshop.web.Model.NguoiDung;
import sportshop.web.Model.VaiTro;
import sportshop.web.Repository.UserRepository;
import sportshop.web.Repository.VaiTroRepository;

@Component
public class DataSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        // Vai trò
        if (vaiTroRepository.findByTenVaiTro("ROLE_ADMIN") == null) {
            vaiTroRepository.save(new VaiTro("ROLE_ADMIN"));
        }

        if (vaiTroRepository.findByTenVaiTro("ROLE_MEMBER") == null) {
            vaiTroRepository.save(new VaiTro("ROLE_MEMBER"));
        }

        if (vaiTroRepository.findByTenVaiTro("ROLE_SHIPPER") == null) {
            vaiTroRepository.save(new VaiTro("ROLE_SHIPPER"));
        }

        // Admin account
        if (userRepository.findByEmail("admin@gmail.com") == null) {
        	NguoiDung admin = new NguoiDung();
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setHoTen("Tran Duc Vu");
            admin.setSoDienThoai("0345934782");
            Set<VaiTro> roles = new HashSet<>();
            roles.add(vaiTroRepository.findByTenVaiTro("ROLE_ADMIN"));
            roles.add(vaiTroRepository.findByTenVaiTro("ROLE_MEMBER"));
            admin.setVaiTro(roles);
            userRepository.save(admin);
        }

        // Member account
        if (userRepository.findByEmail("member@gmail.com") == null) {
            NguoiDung member = new NguoiDung();
            member.setEmail("member@gmail.com");
            member.setPassword(passwordEncoder.encode("123456"));
            Set<VaiTro> roles = new HashSet<>();
            roles.add(vaiTroRepository.findByTenVaiTro("ROLE_MEMBER"));
            member.setVaiTro(roles);
            userRepository.save(member);
        }
    }
}