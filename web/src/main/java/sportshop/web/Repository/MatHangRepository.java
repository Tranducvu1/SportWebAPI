package sportshop.web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import sportshop.web.Model.MatHang;


@Repository
public interface MatHangRepository extends JpaRepository<MatHang,Integer>, JpaSpecificationExecutor<MatHang> {
	@Query("Select mh FROM MatHang mh WHERE mh.tenmathang = :keyword" )
	List<MatHang> searchByKeyword(@Param("keyword") String keyword);

	
}