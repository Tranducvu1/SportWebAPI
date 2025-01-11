package sportshop.web.Service;

import org.springframework.data.domain.Page;
import sportshop.web.Entity.DanhMuc;
import sportshop.web.Entity.MatHang;

import java.util.List;

public interface DanhMucService {

    List<DanhMuc> findAll();

    List<DanhMuc> searchByKeyword(String Keyword);

    Boolean save(DanhMuc danhmuc);

    Boolean update(DanhMuc danhmuc);

    DanhMuc getById(Integer id);

    MatHang getProductByIdFromCategory(DanhMuc danhMuc, Integer productId);

    Boolean deleteProduct(Integer id);

    Page<DanhMuc> getDanhMucPagination(int offset, int pageSize);
}
