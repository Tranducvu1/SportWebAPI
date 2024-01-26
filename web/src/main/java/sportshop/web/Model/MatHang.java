package sportshop.web.Model;

import java.sql.Timestamp;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Data
@Table(name = "mathang")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class MatHang {
	@Id
	@Column(name = "id", nullable = false)
	@PrimaryKeyJoinColumn(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "mamathang", nullable = false)
	private String mamathang;
	
	
	@Column(name = "tenmathang", nullable = false)
	private String tenmathang;
	
	
	@Column(name = "maphanloai", nullable = false)
	private String maphanloai;
	
	@Column(name = "hinhanh", nullable = false)
	private String hinhanh;
	
	@Column(name = "dongia", nullable = false)
	private Long  dongia;
	
	@Column(name = "danhgia", nullable = false)
	private int  danhgia;
	
	@Column(name = "soluong", nullable = false)
	private String  soluong;
	
	@Column(name = "mota", nullable = false)
	private String  mota;
}