package sportshop.web.Service.ipml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import sportshop.web.Entity.ChiTietDonHang;
import sportshop.web.Repository.ChiTietDonHangRepository;
import sportshop.web.Service.ChiTietDonHangService;

@Service
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService{
    @Autowired
    private ChiTietDonHangRepository repository;
    
    @CachePut(value = "chiTietDonHang",key = "#result.id")
    public ChiTietDonHang save(ChiTietDonHang chiTiet) {
        return repository.save(chiTiet);
    }
}