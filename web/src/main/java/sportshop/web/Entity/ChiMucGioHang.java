package sportshop.web.Entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "chimucgiohang")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ChiMucGioHang implements Serializable{
private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Positive
    @Column(name = "soluong", nullable = false)
    private Integer soluong;

   
    @PositiveOrZero
    @Column(name = "money", nullable = false)
    private double money;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nguoidung_id", nullable = false)
    @JsonBackReference
    private NguoiDung nguoiDung;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "donhang_mathang",
        joinColumns = @JoinColumn(name = "donhang_id"),
        inverseJoinColumns = @JoinColumn(name = "mathang_id")
    )
    private List<MatHang> matHangs;
}
