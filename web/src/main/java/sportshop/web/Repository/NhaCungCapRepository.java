package sportshop.web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sportshop.web.Entity.NhaCungCap;

@Repository
public interface NhaCungCapRepository extends JpaRepository<NhaCungCap,Integer>, JpaSpecificationExecutor<NhaCungCap> {
	@Query("Select ncc FROM NhaCungCap ncc WHERE ncc.tennhacungcap = :keyword")
	List<NhaCungCap> searchByKeyword(@Param("keyword") String keyword);
}
