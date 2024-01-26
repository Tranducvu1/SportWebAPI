package sportshop.web.Model;

import java.sql.Timestamp;

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
@Table(name = "khachhang")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class KhachHang {
	@Id
	@Column(name = "id", nullable = false)
	@PrimaryKeyJoinColumn(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "makhachhang", nullable = false)
	private String makhachhang;
	
	
	@Column(name = "hoten", nullable = false)
	private String hoten;
	
	@Column(name = "diachi", nullable = false)
	private String diachi;
	
	@Column(name = "sdt", nullable = false)
	private String sdt;
	
	@Column(name = "email", nullable = false)
	private String email;
}
