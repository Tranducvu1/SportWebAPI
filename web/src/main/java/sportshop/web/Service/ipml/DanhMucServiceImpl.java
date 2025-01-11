package sportshop.web.Service.ipml;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import sportshop.web.Entity.DanhMuc;
import sportshop.web.Entity.Log;
import sportshop.web.Entity.MatHang;
import sportshop.web.Repository.DanhMucRepository;
import sportshop.web.Repository.LogRepository;
import sportshop.web.Service.DanhMucService;
import sportshop.web.Service.LogService;

@Service
@Slf4j
public class DanhMucServiceImpl implements DanhMucService {

    @Autowired
    private DanhMucRepository danhmucRepository;
    @Autowired
    private LogService logService;

    @Autowired
    private LogRepository logRepository;

    @Override
    public List<DanhMuc> findAll() {
        return danhmucRepository.findAll();
    }

    @Override
    public List<DanhMuc> searchByKeyword(String keyword) {
        return danhmucRepository.searchByKeyword(keyword);
    }

    @Override
    public Boolean save(DanhMuc danhmuc) {
        DanhMuc savedDanhMuc = danhmucRepository.save(danhmuc);
        if (savedDanhMuc != null) {
            logService.saveLog("Thêm mới danh mục id = " + savedDanhMuc.getId());
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(DanhMuc danhmuc) {
        DanhMuc updatedDanhMuc = danhmucRepository.save(danhmuc);
        if (updatedDanhMuc != null) {
            logService.saveLog("Sửa danh mục id = " + updatedDanhMuc.getId());
            return true;
        }
        return false;
    }

    @Override
    public DanhMuc getById(Integer id) {
        return requireOne(id);
    }

    private DanhMuc requireOne(Integer id) {
        return danhmucRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    @Override
    public MatHang getProductByIdFromCategory(DanhMuc danhMuc, Integer productId) {
        List<MatHang> products = danhMuc.getMathangs();
        for (MatHang product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null; 
    }

    @Override
    public Boolean deleteProduct(Integer id) {
        Optional<DanhMuc> danhmucOptional = danhmucRepository.findById(id);
        if (danhmucOptional.isPresent()) {
            DanhMuc danhMuc = danhmucOptional.get();
            danhmucRepository.delete(danhMuc);
            if (!danhmucRepository.existsById(id)) {
                logService.saveLog("Xóa danh mục id = " + id);
                return true;
            }
        }
        return false;
    }

    @Override
    public Page<DanhMuc> getDanhMucPagination(int offset, int pageSize) {
        return danhmucRepository.findAll(PageRequest.of(offset, pageSize));
    }

  
}
