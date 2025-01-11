package sportshop.web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sportshop.web.Entity.NhanVien;


@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien,Integer>, JpaSpecificationExecutor<NhanVien> {
	@Query("Select nv FROM NhanVien nv WHERE nv.hoten = :keyword")
	List<NhanVien> searchByKeyword(@Param("keyword") String keyword);
}

