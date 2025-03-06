package sportshop.web.DTO;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import sportshop.web.Entity.BienTheMatHang;
import sportshop.web.Entity.HinhAnhMatHang;
import sportshop.web.Entity.MatHang;

@Data
@Builder
public class MatHangDto implements Serializable {
    private static final long serialVersionUID = 1L; 
    
    private Integer id;
    private String mamathang;
    private String tenmathang;
    private Integer danhgia;
    private String mota;
    private Integer giamgia;
    private String gender;
    private String ngaythem;
    private Integer danhMucId;
    private Integer nhaSXId;
    private String danhMucTen;
    private String hangSanXuatTen;
    private List<BienTheMatHangDTO> bienthes;
    private List<HinhAnhMatHangDTO> hinhanhs;
    private List<BinhLuanDTO> binhluans;
    private List<DanhGiaDTO> danhgias;
}




