package sportshop.web.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	
		
	@OneToMany(mappedBy = "danhMuc", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<MatHang> mathangs = new ArrayList<>();

}