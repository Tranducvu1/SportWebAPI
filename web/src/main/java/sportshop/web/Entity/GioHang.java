package sportshop.web.Entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "giohang")
public class GioHang implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    
    @ManyToOne
    @JoinColumn(name = "mathang_id", nullable = false)
    private MatHang matHang;

    @Column(name = "soluong", nullable = false)
    private int soLuong;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private NguoiDung user;

    @Column(name = "money", nullable = false)
    private double money;

    @Column(name = "hinhanh")
    private String hinhanh;
}
