package sportshop.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sportshop.web.Entity.ChiMucGioHang;
import sportshop.web.Entity.GioHang;
import sportshop.web.Entity.NguoiDung;


@Repository
public interface GioHangRepository extends JpaRepository<GioHang,Integer>, JpaSpecificationExecutor<GioHang> {

	ChiMucGioHang findByNguoiDung(NguoiDung n);
	
}
