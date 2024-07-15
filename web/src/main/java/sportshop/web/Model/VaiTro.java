//package sportshop.web.Model;
//
//import java.util.Set;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.*;
//import sportshop.web.DTO.Role;
//
//@Entity
//@Table(name = "vaitro")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class VaiTro {
//	
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//	
//    @Column(name = "ten_vai_tro")
//    private String tenVaiTro;
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//}