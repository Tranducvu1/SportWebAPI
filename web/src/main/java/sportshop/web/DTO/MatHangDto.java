package sportshop.web.DTO;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatHangDto implements Serializable {
    private static final long serialVersionUID = 1L; 
    
    private Integer id;
    private String mamathang;
    private String tenmathang;
    private String hinhanh;
    private Integer dongia;
    private Integer danhgia;
    private Integer soluong;
    private String size;
    private String mota;
    private Integer giamgia;
    private String gender;
    private String ngaythem;
    private Integer danhMucId;
    private Integer nhaSXId;
    private String danhMucTen;
    private String hangSanXuatTen;
}
