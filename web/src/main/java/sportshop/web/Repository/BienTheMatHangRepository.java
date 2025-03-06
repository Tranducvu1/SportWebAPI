package sportshop.web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sportshop.web.Entity.BienTheMatHang;
import sportshop.web.Entity.MatHang;

@Repository
public interface BienTheMatHangRepository extends JpaRepository<BienTheMatHang, Integer>, JpaSpecificationExecutor<MatHang>{
    List<BienTheMatHang> findByPriceBetween(double fromPrice, double toPrice);

}
