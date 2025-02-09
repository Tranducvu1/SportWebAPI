package sportshop.web.DTO;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequest {
	private String email;
    private List<ChiTietDonHangDTO> chiTietDonHangs;

}
