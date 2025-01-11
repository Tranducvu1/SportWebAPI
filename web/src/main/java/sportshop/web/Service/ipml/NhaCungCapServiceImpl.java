package sportshop.web.Service.ipml;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sportshop.web.Service.NhaCungCapService;
import sportshop.web.Repository.NhaCungCapRepository;
import sportshop.web.Entity.NhaCungCap;
import sportshop.web.Entity.Log;
import sportshop.web.Repository.LogRepository;

@Service
public class NhaCungCapServiceImpl implements NhaCungCapService {
    
    @Autowired
    private NhaCungCapRepository nhacungcapRepository;
    
    @Autowired
    private LogRepository logRepository;

    //get all suppliers
    @Override
    public List<NhaCungCap> findAll() {
        return nhacungcapRepository.findAll();
    }

    //search by keyword
    @Override
    public List<NhaCungCap> searchByKeyword(String Keyword) {
        return nhacungcapRepository.searchByKeyword(Keyword);
    }

    //save new supplier
    @Override
    public Boolean save(NhaCungCap nhaCungCap) {
        NhaCungCap savedNhaCungCap = nhacungcapRepository.save(nhaCungCap);
        if (savedNhaCungCap != null) {
            Log log = new Log();
            log.setLogString("Thêm mới nhà cung cấp id = " + savedNhaCungCap.getId());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            logRepository.save(log);
            return true;
        }
        return false;
    }

    //update supplier
    @Override
    public Boolean update(NhaCungCap nhaCungCap) {
        NhaCungCap updatedNhaCungCap = nhacungcapRepository.save(nhaCungCap);
        if (updatedNhaCungCap != null) {
            Log log = new Log();
            log.setLogString("Cập nhật nhà cung cấp id = " + updatedNhaCungCap.getId());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            logRepository.save(log);
            return true;
        }
        return false;
    }

    //get supplier by id
    @Override
    public NhaCungCap getById(Integer id) {
        return requireOne(id);
    }

    //helper method to get supplier or throw exception
    private NhaCungCap requireOne(Integer id) {
        return nhacungcapRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
