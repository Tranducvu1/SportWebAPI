package sportshop.web.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "giohang")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class GioHang implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // IDENTITY thường được ưu tiên hơn AUTO
    @Column(name = "id")
    private Long id;
    
    @Column(name = "tensanpham", nullable = false)
    private String tensanpham;
    
    @Column(name = "hinhanh")
    private String hinhanh;
    
    @Column(name = "dongia", nullable = false)
    private BigDecimal dongia;
    
    @Column(name = "soluong", nullable = false)
    private Integer soluong;
    
    @Column(name = "tongtien", nullable = false)
    private BigDecimal tongtien;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
   
    private NguoiDung nguoiDung;
}