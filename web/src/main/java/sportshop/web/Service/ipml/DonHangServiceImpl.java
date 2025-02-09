package sportshop.web.Service.ipml;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import sportshop.web.Entity.DonHang;
import sportshop.web.Repository.DonHangRepository;
import sportshop.web.Service.DonHangService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;


@Service
@Slf4j
public class DonHangServiceImpl implements DonHangService {
	@Autowired
    private DonHangRepository donHangRepository;

    @Override
    @Cacheable(value = "donHangList", key = "'allDonHangs'")
    public List<DonHang> findAll() {
        log.debug("Fetching all DonHang records");
        return donHangRepository.findAll();
    }

   
    @Override
    @CacheEvict(value = "donHangList", allEntries = true)  // Clear the cache after update
    public Boolean update(DonHang donHang) {
        DonHang updatedDonHang = donHangRepository.save(donHang);
        log.info("Updated DonHang with id = {}", updatedDonHang.getId());
		return true;
    }

    @Override
    @CachePut(value = "donHangList", key = "#donHang.id") // Update the cache with the saved DonHang
    public DonHang save(DonHang donHang) {
        log.debug("Saving DonHang: {}", donHang);
        return donHangRepository.save(donHang);
    }

    @Override
    @Cacheable(value = "donHangList", key = "#id")  // Cache by DonHang id
    public DonHang getById(Integer id) {
        log.debug("Fetching DonHang by id: {}", id);
        return donHangRepository.findById(id).orElse(null);
    }

    @Override
    @Cacheable(value = "donHangList", key = "#id")  // Cache based on NguoiDungId
    public List<DonHang> findByNguoiDungId(Long id) {
        log.debug("Fetching DonHang for user id: {}", id);
        return donHangRepository.findByNguoiDung(id);
    }
}
