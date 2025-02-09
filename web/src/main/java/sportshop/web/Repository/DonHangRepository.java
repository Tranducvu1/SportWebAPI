package sportshop.web.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sportshop.web.Entity.DonHang;

@RedisHash("donHang")
@Repository
public interface DonHangRepository extends JpaRepository<DonHang,Integer>, JpaSpecificationExecutor<DonHang> {
	
	@Query("SELECT dh FROM DonHang dh WHERE dh.nguoiDung.id = :id")
	List<DonHang> findByNguoiDung(@Param("id") Long id);


}
