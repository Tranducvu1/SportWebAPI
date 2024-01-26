package sportshop.web.Model;

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
@Table(name = "nhacungcap")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class NhaCungCap {
	@Id
	@Column(name = "id", nullable = false)
	@PrimaryKeyJoinColumn(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "manhacungcap", nullable = false)
	private String manhacungcap;
	
	
	@Column(name = "mamathang", nullable = false)
	private String mamathang;
	
	
	@Column(name = "tennhacungcap", nullable = false)
	private String tennhacungcap;
	
	@Column(name = "diachi", nullable = false)
	private String diachi;
	
	
	
	@Column(name = "sdt", nullable = false)
	private String sdt;
}