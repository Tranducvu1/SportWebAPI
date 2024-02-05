package sportshop.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sportshop.web.Model.DanhMuc;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc,Integer>, JpaSpecificationExecutor<DanhMuc> {
	
}
