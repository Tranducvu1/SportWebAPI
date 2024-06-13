package sportshop.web.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sportshop.web.Model.Banner;
import sportshop.web.Model.HangSanXuat;

@Repository
public interface HangSanXuatRepository extends JpaRepository<HangSanXuat,Integer>, JpaSpecificationExecutor<HangSanXuat> {
	@Query("SELECT h FROM HangSanXuat h WHERE h.tenhang LIKE %:keyword%")
	List<HangSanXuat> searchByKeyword(@Param("keyword") String keyword);
}