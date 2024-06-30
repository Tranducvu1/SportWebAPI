package sportshop.web.Model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Nguoidung")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "fieldHandler" })
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    private String email;

    private String password;

    private String confirmPassword;

    private String hoTen;

    private String soDienThoai;

    private String diaChi;

    @ManyToMany
    @JoinTable(name = "nguoidung_vaitro",
        joinColumns = @JoinColumn(name = "ma_nguoi_dung"), 
        inverseJoinColumns = @JoinColumn(name = "ma_vai_tro"))
    private Set<VaiTro> vaiTro;
}
