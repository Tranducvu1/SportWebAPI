package sportshop.web.Service;

import sportshop.web.Entity.ChiMucGioHang;
import sportshop.web.Entity.GioHang;
import sportshop.web.Entity.MatHang;

import java.util.List;

public interface ChiMucGioHangService {
    ChiMucGioHang getChiMucGioHangBySanPhamAndGioHang(MatHang mt, GioHang g);
    ChiMucGioHang saveChiMucGiohang(ChiMucGioHang c);
    void deleteChiMucGiohang(ChiMucGioHang c);
    List<ChiMucGioHang> getChiMucGioHangByGioHang(GioHang g);
    void deleteAllChiMucGiohang(List<ChiMucGioHang> c);
}
