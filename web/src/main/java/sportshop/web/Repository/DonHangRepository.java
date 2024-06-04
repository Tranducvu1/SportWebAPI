package sportshop.web.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sportshop.web.Model.DonHang;


@Repository
public interface DonHangRepository extends JpaRepository<DonHang,Integer>, JpaSpecificationExecutor<DonHang> {
	@Query("Select dh FROM DonHang dh WHERE dh.tenmathang = :keyword")
	List<DonHang> searchByKeyword(@Param("keyword") String keyword);
	

}
