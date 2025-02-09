package sportshop.web.Entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chitietdonhang")
public class ChiTietDonHang implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "donhang_id", nullable = false)
    private DonHang donHang;

    @Column(name = "tenmathang", nullable = false)
    private String tenmathang;
    
    @ManyToOne
    @JoinColumn(name = "mathang_id", nullable = false)
    private MatHang matHang;

    @Column(name = "soluong", nullable = false)
    private int soLuong;

    @Column(name = "money", nullable = false)
    private int money;

    @Column(name = "hinhanh")
    private String hinhanh;
}
