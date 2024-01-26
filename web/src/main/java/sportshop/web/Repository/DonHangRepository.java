package sportshop.web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sportshop.web.Model.DonHang;


@Repository
public interface DonHangRepository extends JpaRepository<DonHang,Integer>, JpaSpecificationExecutor<DonHang> {
	@Query("Select dh FROM DonHang dh WHERE dh.nhacungcap = :keyword OR dh.madonhang = :keyword")
	List<DonHang> searchByKeyword(@Param("keyword") String keyword);
}
