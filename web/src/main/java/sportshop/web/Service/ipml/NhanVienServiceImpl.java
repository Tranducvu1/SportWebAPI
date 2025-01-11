package sportshop.web.Service.ipml;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sportshop.web.Service.NhanVienService;
import sportshop.web.Repository.NhanVienRepository;
import sportshop.web.Entity.NhanVien;
import sportshop.web.Entity.Log;
import sportshop.web.Repository.LogRepository;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private NhanVienRepository nhannienRepository;
    
    @Autowired
    private LogRepository logRepository;

    //get all employees
    @Override
    public List<NhanVien> findAll() {
        return nhannienRepository.findAll();
    }

    //search by keyword
    @Override
    public List<NhanVien> searchByKeyword(String keyword) {
        return nhannienRepository.searchByKeyword(keyword);
    }

    //save new employee
    @Override
    public Boolean save(NhanVien nhanVien) {
        NhanVien savedNhanVien = nhannienRepository.save(nhanVien);
        if (savedNhanVien != null) {
            Log log = new Log();
            log.setLogString("Thêm mới nhân viên id = " + savedNhanVien.getId());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            logRepository.save(log);
            return true;
        }
        return false;
    }

    //update employee
    @Override
    public Boolean update(NhanVien nhanVien) {
        NhanVien updatedNhanVien = nhannienRepository.save(nhanVien);
        if (updatedNhanVien != null) {
            Log log = new Log();
            log.setLogString("Cập nhật nhân viên id = " + updatedNhanVien.getId());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            logRepository.save(log);
            return true;
        }
        return false;
    }

    //get employee by id
    @Override
    public NhanVien getById(Integer id) {
        return requireOne(id);
    }

    //helper method to get employee or throw exception
    private NhanVien requireOne(Integer id) {
        return nhannienRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
