package sportshop.web.Model;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "donhang")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class DonHang {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	

	@Column(name = "tenmathang")
	private String tenmathang;
	
	
	@Column(name = "ngaydat", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
	private Timestamp ngaydat;
		
	@Column(name = "ngaydukiennhan", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
	private Timestamp ngaydukiennhan;
	
	@Column(name = "phivanchuyen")
	private Long phivanchuyen;
	
	@Column(name = "soluong")
	private Integer  soluong;
	
	@Column(name = "hinhanh")
	private String hinhanh;
	
	//@Column(name = "uudai")
	//private String uudai;
	
	@Column(name = "money")
	private Long Money;
	
	 @ManyToOne(fetch = FetchType.EAGER)
	 @JoinColumn(name = "nguoidung_id")
	 private NguoiDung nguoiDung;
}
