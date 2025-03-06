package sportshop.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sportshop.web.Entity.GioHang;

@Repository
public interface ChiTietDonHangRepository extends JpaRepository<GioHang, Integer> {
}

