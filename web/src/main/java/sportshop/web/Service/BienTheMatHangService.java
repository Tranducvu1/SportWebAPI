package sportshop.web.Service;

import java.util.List;

import sportshop.web.Entity.BienTheMatHang;

public interface BienTheMatHangService {
	 List<BienTheMatHang> findByPriceBetween(double fromPrice, double toPrice);
}
