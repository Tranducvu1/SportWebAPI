package sportshop.web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sportshop.web.Entity.Banner;
import sportshop.web.Entity.ChiMucGioHang;
import sportshop.web.Entity.GioHang;
import sportshop.web.Entity.MatHang;


@Repository
public interface ChiMucGioHangRepository extends JpaRepository<ChiMucGioHang,Integer>, JpaSpecificationExecutor<ChiMucGioHang> {

	ChiMucGioHang findByMatHangAndGioHang(MatHang mt, GioHang g);

	List<ChiMucGioHang> findByGioHang(GioHang g);

	
	

}
