package sportshop.web.DTO;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MatHangDto {

		private Integer id;
		
		private String mamathang;
		
		private String tenmathang;
		
		private String maphanloai;
		
		private String hinhanh;
		
		private Integer dongia;
		
		private Integer  danhgia;
		
		private Integer  soluong;
		
		private String size;
		
		private String  mota;
		
		private Integer giamgia;
		
		private String gender;
		
		private Timestamp ngaythem;
		
		private Integer danhMucId;
		private Integer nhaSXId;
}
