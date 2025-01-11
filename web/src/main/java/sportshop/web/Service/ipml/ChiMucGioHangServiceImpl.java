package sportshop.web.Service.ipml;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sportshop.web.Repository.ChiMucGioHangRepository;
import sportshop.web.Service.ChiMucGioHangService;
import sportshop.web.Entity.ChiMucGioHang;
import sportshop.web.Entity.GioHang;
import sportshop.web.Entity.MatHang;

@Service
public class ChiMucGioHangServiceImpl implements ChiMucGioHangService {
    
    @Autowired
    private ChiMucGioHangRepository repo;
    
    @Override
    public ChiMucGioHang getChiMucGioHangBySanPhamAndGioHang(MatHang mt, GioHang g) {
        return repo.findByMatHangAndGioHang(mt, g);
    }
    
    @Override
    public ChiMucGioHang saveChiMucGiohang(ChiMucGioHang c) {
        return repo.save(c);
    }
    
    @Override
    public void deleteChiMucGiohang(ChiMucGioHang c) {
        repo.delete(c);
    }
    
    @Override
    public List<ChiMucGioHang> getChiMucGioHangByGioHang(GioHang g) {
        return repo.findByGioHang(g);
    }
    
    @Override
    public void deleteAllChiMucGiohang(List<ChiMucGioHang> c) {
        repo.deleteAll(c);
    }
}
