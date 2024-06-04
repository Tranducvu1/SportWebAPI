package sportshop.web.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "danhmuc")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class DanhMuc {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "madanhmuc", nullable = false)
	private String madanhmuc;

	@Column(name = "tendanhmuc")
	private String tendanhmuc;

	@OneToMany(mappedBy = "danhMuc",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<MatHang> mathangs ;
	
	
	@Override
	public String toString() {
	    return "DanhMuc{id=" + id + ", tenDanhMuc=" + tendanhmuc + "}";
	}

}