package sportshop.web.Service.ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import sportshop.web.Entity.GioHang;
import sportshop.web.Repository.ChiTietDonHangRepository;
import sportshop.web.Service.GioHangService;

@Service
public  class GioHangServiceImpl implements GioHangService{
    @Autowired
    private ChiTietDonHangRepository repository;
    
    @CachePut(value = "chiTietDonHang",key = "#result.id")
    public GioHang save(GioHang chiTiet) {
        return repository.save(chiTiet);
    }
}