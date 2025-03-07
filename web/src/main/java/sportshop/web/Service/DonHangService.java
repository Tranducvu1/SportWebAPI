package sportshop.web.Service;

import sportshop.web.Entity.DonHang;
import java.util.List;

public interface DonHangService {
    List<DonHang> findAll();
    DonHang save(DonHang donhang);
    Boolean update(DonHang donhang);
    DonHang getById(Integer id);
	List<DonHang> findByNguoiDungId(Long userId);
}
