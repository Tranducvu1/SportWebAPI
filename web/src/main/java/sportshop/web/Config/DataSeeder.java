//package sportshop.web.Config;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import sportshop.web.DTO.Role;
//import sportshop.web.Model.NguoiDung;
//import sportshop.web.Repository.UserRepository;
//import sportshop.web.Repository.VaiTroRepository;
//
//@Component
//public class DataSeeder {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private VaiTroRepository vaiTroRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @EventListener
//    @Transactional
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        seedVaiTro();
//        seedUsers();
//    }
//
//    private void seedVaiTro() {
//        seedVaiTroIfNotFound("ADMIN");
//        seedVaiTroIfNotFound("MEMBER");
//    }
//
//    private void seedVaiTroIfNotFound(String tenVaiTro) {
//        if (vaiTroRepository.findByTenVaiTro(tenVaiTro) == null) {
//            vaiTroRepository.save(new Role(tenVaiTro));
//        }
//    }
//
//    private void seedUsers() {
//        seedAdminUser();
//        seedMemberUser();
//    }
//
//    private void seedAdminUser() {
//        if (userRepository.findByEmail("admin@gmail.com") == null) {
//            NguoiDung admin = new NguoiDung();
//            admin.setEmail("admin@gmail.com");
//            admin.setPassword(passwordEncoder.encode("123456"));
//            admin.setHoTen("Tran Duc Vu");
//            admin.setSoDienThoai("0345934782");
//            Set<VaiTro> roles = new HashSet<>();
//            roles.add(vaiTroRepository.findByTenVaiTro("ADMIN"));
//            roles.add(vaiTroRepository.findByTenVaiTro("MEMBER"));
//            admin.setVaiTro(roles);
//            userRepository.save(admin);
//        }
//    }
////seed member role member user 
//    private void seedMemberUser() {
//        if (userRepository.findByEmail("member@gmail.com") == null) {
//            NguoiDung member = new NguoiDung();
//            member.setEmail("member@gmail.com");
//            member.setPassword(passwordEncoder.encode("123456"));
//            Set<Role> roles = new HashSet<>();
//            roles.add(vaiTroRepository.findByTenVaiTro("MEMBER"));
//            member.setRole("ADMIN");
//            userRepository.save(member);
//        }
//    }
//}