package sportshop.web.Model;

import java.sql.Timestamp;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
@Table(name = "hoadon")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class HoaDon {
	@Id
	@Column(name = "id", nullable = false)
	@PrimaryKeyJoinColumn(name = "id")
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
	
	@Column(name = "maphuongthucvanchuyen", nullable = false)
	private String maphuongthucvanchuyen;
	
	@Column(name = "phivanchuyen", nullable = false)
	private Long phivanchuyen;
	
	@Column(name = "tongtien", nullable = false)
	private Long tongtien;
}
