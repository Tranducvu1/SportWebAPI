package sportshop.web.Service;

import java.util.List;
import org.springframework.data.domain.Page;

import sportshop.web.DTO.MatHangDto;
import sportshop.web.Entity.MatHang;

public interface MatHangService {
    List<MatHang> findAll();
    MatHang getProduct(Integer productId);
    List<MatHang> searchByKeyword(String keyword);
    MatHang getId(int id);
    Boolean save(MatHang matHang) throws Exception;
    Boolean update(MatHang matHang);
    Boolean deleteProduct(Integer id) throws Exception;
    List<MatHang> findByCategoryId(Integer categoryId);
    MatHang getById(Integer id);
    List<MatHang> findProductsWtihSorting(String field);
    Page<MatHangDto> filterAndSortProducts(Integer danhMucId, Integer hangSanXuatId, String priceRange, String sortOrder, Integer page, Integer size);
}
