package sportshop.web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sportshop.web.Model.DanhMuc;
import sportshop.web.Model.DonHang;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc,Integer>, JpaSpecificationExecutor<DanhMuc> {
	@Query("Select dm FROM DanhMuc dm WHERE dm.tendanhmuc = :keyword")
	List<DanhMuc> searchByKeyword(@Param("keyword") String keyword);
}
