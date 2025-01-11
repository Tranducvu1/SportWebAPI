package sportshop.web.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "donhang")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class DonHang implements Serializable{
	 private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "tenmathang", nullable = false)
    private String tenmathang;

    @Column(name = "ngaydat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp ngaydat;

    @Column(name = "ngaydukiennhan", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Timestamp ngaydukiennhan;

    @Positive
    @Column(name = "phivanchuyen", nullable = false)
    private BigDecimal phivanchuyen;

    @Positive
    @Column(name = "soluong", nullable = false)
    private Integer soluong;

    @Column(name = "hinhanh")
    private String hinhanh;

    @PositiveOrZero
    @Column(name = "money", nullable = false)
    private BigDecimal money;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoidung_id", nullable = false)
    private NguoiDung nguoiDung;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "donhang_mathang",
        joinColumns = @JoinColumn(name = "donhang_id"),
        inverseJoinColumns = @JoinColumn(name = "mathang_id")
    )
    private List<MatHang> matHangs;
}
