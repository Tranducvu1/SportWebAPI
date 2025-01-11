package sportshop.web.DTO;

import lombok.Data;
import sportshop.web.Entity.MatHang;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DonHangDTO {
    private String tenmathang;
    private String ngaydat;
    private String ngaydukiennhan;
    private BigDecimal phivanchuyen;
    private Integer soluong;
    private BigDecimal money;
    private List<MatHang> matHangs;
}
