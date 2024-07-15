package sportshop.web.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sportshop.web.Model.NguoiDung;


@Repository
public interface UserRepository  extends JpaRepository<NguoiDung,Integer>, JpaSpecificationExecutor<NguoiDung> {

//	Page<NguoiDung> findByVaiTro(Set<VaiTro> vaiTro, PageRequest of);
	//@Query("SELECT DISTINCT n FROM nguoidung n JOIN n.vaiTro v WHERE v IN :vaiTro")
	Optional<NguoiDung> findById(Integer id);
//	Page<NguoiDung> findByVaiTro(@Param("vaiTro") Set<VaiTro> vaiTro, Pageable pageable);
	Optional<NguoiDung> findByEmail(String email);

	//VaiTro findByVaiTro(String vaiTro);

	

}

