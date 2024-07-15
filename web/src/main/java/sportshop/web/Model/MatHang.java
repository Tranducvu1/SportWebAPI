package sportshop.web.Model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@Table(name = "mathang")
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
	
	@Column(name = "maphanloai")
	private String maphanloai;
	
	@Column(name = "hinhanh", nullable = false)
	private String hinhanh;
	
	@Column(name = "dongia", nullable = false)
	private int dongia;
	
	@Column(name = "danhgia")
	private int  danhgia;
	
	@Column(name = "soluong", nullable = false)
	private int  soluong;
	
	@Column(name = "size")
	private String size;
	
	@Column(name = "mota", nullable = false)
	private String  mota;
	
	@Column(name = "giamgia", nullable = false)
	private int giamgia;
	
	@Column(name = "gioi_tinh",nullable = false)
	private String gender;
	
	@Column(name = "ngaythem", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp ngaythem;
	
	@ManyToOne
    @JoinColumn(name = "danhmuc_id",nullable = false)
    private DanhMuc danhMuc;
	
	@ManyToOne
	@JoinColumn(name = "ma_hang_sx")
	private HangSanXuat hangSanXuat;
	@Override
	public String toString() {
	    return "MatHang{id=" + id + ", tenMatHang=" + tenmathang + "}";
	}
}
