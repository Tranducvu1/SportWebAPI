package sportshop.web.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sportshop.web.Model.DanhMuc;
import sportshop.web.Model.HangSanXuat;
import sportshop.web.Model.MatHang;


@Repository
public interface MatHangRepository extends JpaRepository<MatHang,Integer>, JpaSpecificationExecutor<MatHang> {
	//search keyword không phân biệt chữ hoa hay thương
	@Query("SELECT mh FROM MatHang mh WHERE LOWER(mh.tenmathang) LIKE LOWER(CONCAT('%', :keyword, '%'))") 
	List<MatHang> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT m FROM MatHang m LEFT JOIN FETCH m.danhMuc WHERE m.id = :id")
    MatHang findByIdWithDanhMuc(@Param("id") Integer id);
    
    @Query("SELECT p FROM MatHang p WHERE p.dongia BETWEEN :fromPrice AND :toPrice")
    List<MatHang> findByPriceRange(double fromPrice, double toPrice);
    
    List<MatHang> findByDanhMucId(Integer categoryId);
    
    Page<MatHang> findByDanhMuc(DanhMuc danhMuc, PageRequest pageRequest);
    
	Page<MatHang> findByHangSanXuat(HangSanXuat hangSanXuat, PageRequest of);

	boolean existsById(Long id);

	//List<MatHang> findByDanhMuc(DanhMuc danhMuc);
}