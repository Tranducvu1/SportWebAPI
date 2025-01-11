package sportshop.web.Service;

import sportshop.web.Entity.ChiMucGioHang;
import sportshop.web.Entity.GioHang;
import sportshop.web.Entity.NguoiDung;

public interface GioHangService {

    ChiMucGioHang getGioHangByNguoiDung(NguoiDung n);

    GioHang save(GioHang g);
}
