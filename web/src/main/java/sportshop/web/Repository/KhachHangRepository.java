package sportshop.web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import sportshop.web.Model.DonHang;
import sportshop.web.Model.KhachHang;


@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang,Integer>, JpaSpecificationExecutor<KhachHang> {
	@Query("Select kh FROM KhachHang kh WHERE kh.hoten = :keyword")
	List<KhachHang> searchByKeyword(@Param("keyword") String keyword);

	
}