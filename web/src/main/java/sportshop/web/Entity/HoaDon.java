package sportshop.web.Entity;

import java.time.LocalDate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "hoadon")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class HoaDon {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mahoadon", nullable = false)
    private String mahoadon;
    
    @Column(name = "ngaygiaodich", nullable = false)
    private LocalDate ngaygiaodich;
    
    @Column(name = "mamathang", nullable = false)
    private String mamathang;
    
    @Column(name = "motagiaodich", nullable = false)
    private String motagiaodich;
    
    @Column(name = "soluong", nullable = false)
    private Long soluong;
    
    @Column(name = "giamgia", nullable = false)
    private int giamgia;
    
    @Column(name = "maphuongthucvanchuyen", nullable = false)
    private String maphuongthucvanchuyen;
    
    @Column(name = "phivanchuyen", nullable = false)
    private Long phivanchuyen;
    
    @Column(name = "tongtien", nullable = false)
    private Long tongtien;
}