package sportshop.web.Service.ipml;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sportshop.web.Entity.HangSanXuat;
import sportshop.web.Entity.Log;
import sportshop.web.Repository.HangSanXuatRepository;
import sportshop.web.Repository.LogRepository;
import sportshop.web.Service.HangSanXuatService;

@Service
public class HangSanXuatServiceImpl implements HangSanXuatService {

    @Autowired
    private HangSanXuatRepository hangSanXuatRepository;

    @Autowired
    private LogRepository logRepository;

    @Override
    public List<HangSanXuat> findAll() {
        return hangSanXuatRepository.findAll();
    }

    @Override
    public List<HangSanXuat> searchByKeyword(String keyword) {
        return hangSanXuatRepository.searchByKeyword(keyword);
    }

    @Override
    public Boolean save(HangSanXuat hangSanXuat) {
        HangSanXuat savedHangSanXuat = hangSanXuatRepository.save(hangSanXuat);
        if (savedHangSanXuat != null) {
            Log log = new Log();
            log.setLogString("Thêm mới nhà sản xuất id = " + savedHangSanXuat.getId());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            logRepository.save(log);
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(HangSanXuat hangSanXuat) {
        HangSanXuat updatedHangSanXuat = hangSanXuatRepository.save(hangSanXuat);
        if (updatedHangSanXuat != null) {
            Log log = new Log();
            log.setLogString("Cập nhật nhà sản xuất id = " + updatedHangSanXuat.getId());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            logRepository.save(log);
            return true;
        }
        return false;
    }

    @Override
    public HangSanXuat getById(Integer id) {
        return requireOne(id);
    }

    private HangSanXuat requireOne(Integer id) {
        return hangSanXuatRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
