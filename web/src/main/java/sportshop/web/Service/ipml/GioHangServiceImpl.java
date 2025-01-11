package sportshop.web.Service.ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sportshop.web.Entity.ChiMucGioHang;
import sportshop.web.Entity.GioHang;
import sportshop.web.Entity.NguoiDung;
import sportshop.web.Repository.GioHangRepository;
import sportshop.web.Service.GioHangService;

@Service
public class GioHangServiceImpl implements GioHangService {

    @Autowired
    private GioHangRepository repo;

    @Override
    public ChiMucGioHang getGioHangByNguoiDung(NguoiDung n) {
        return repo.findByNguoiDung(n);
    }

    @Override
    public GioHang save(GioHang g) {
        return repo.save(g);
    }
}
