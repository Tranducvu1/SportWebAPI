package sportshop.web.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "hangsanxuat")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class HangSanXuat {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "tenhang", nullable = false)
	private String tenhang;

	@OneToMany(mappedBy = "hangSanXuat")	
	@JsonIgnore	
	private List<MatHang> mathangs ;

	@Override
	public String toString() {
		return "HangSanXuat [id=" + id + ", tenhang=" + tenhang + ", mathangs=" + mathangs + "]";
	}
}
