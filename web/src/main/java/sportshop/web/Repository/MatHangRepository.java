package sportshop.web.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;

import sportshop.web.Entity.DanhMuc;
import sportshop.web.Entity.HangSanXuat;
import sportshop.web.Entity.MatHang;


@RedisHash("matHang")
@Repository
public interface MatHangRepository extends JpaRepository<MatHang,Integer>, JpaSpecificationExecutor<MatHang> {
	//search keyword không phân biệt chữ hoa hay thương
	// @Query("SELECT mh FROM MatHang mh WHERE LOWER(mh.tenmathang) LIKE LOWER(CONCAT('%', :keyword, '%'))") 
	// List<MatHang> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT m FROM MatHang m LEFT JOIN FETCH m.danhMuc WHERE m.id = :id")
    MatHang findByIdWithDanhMuc(@Param("id") Integer id);
    
    // @Query("SELECT p FROM MatHang p WHERE p.dongia BETWEEN :fromPrice AND :toPrice")
    // List<MatHang> findByPriceRange(double fromPrice, double toPrice);

    
    List<MatHang> findByDanhMucId(Integer categoryId);
    

    
    Page<MatHang> findByDanhMuc(DanhMuc danhMuc, PageRequest pageRequest);
    
	Page<MatHang> findByHangSanXuat(HangSanXuat hangSanXuat, PageRequest of);

	boolean existsById(Long id);

	@Query("SELECT DISTINCT m FROM MatHang m WHERE " +
	        "(LOWER(m.tenmathang) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	        "LOWER(m.mota) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	        "LOWER(m.gender) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
	        "AND (LOWER(m.tenmathang) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	        "LOWER(m.mota) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	        "LOWER(m.gender) LIKE LOWER(CONCAT('%', :keyword, '%')))") 
	List<MatHang> findByTenmathangContainingIgnoreCase(@Param("keyword") String keyword);
	
    @Query("SELECT m FROM MatHang m WHERE LOWER(FUNCTION('REPLACE', m.tenmathang, 'á', 'a')) LIKE LOWER(CONCAT('%', :tenmathang, '%'))")
    MatHang findByNormalizedProductName(@Param("tenmathang") String tenmathang);
    Optional<MatHang> findByTenmathang(String tenMatHang);
    
    @EntityGraph(attributePaths = {"bienthes", "hinhanhs"})
    @Query("SELECT m FROM MatHang m WHERE m.id IN :ids")
    List<MatHang> findByIdsWithDetails(@Param("ids") List<Integer> ids);

    @Query("SELECT m FROM MatHang m " +
            "JOIN m.bienthes b " +
            "GROUP BY m " +
            "ORDER BY CASE WHEN :sortOrder = 'asc' THEN MIN(b.price) END ASC, " +
            "CASE WHEN :sortOrder = 'desc' THEN MAX(b.price) END DESC")
     Page<MatHang> findAllSortedByPrice(@Param("sortOrder") String sortOrder, Pageable pageable);



	//List<MatHang> findByDanhMuc(DanhMuc danhMuc);
}