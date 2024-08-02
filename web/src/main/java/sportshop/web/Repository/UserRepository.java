package sportshop.web.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sportshop.web.Model.DanhMuc;
import sportshop.web.Model.NguoiDung;


@Repository
public interface UserRepository  extends JpaRepository<NguoiDung,Long>, JpaSpecificationExecutor<NguoiDung> {

//	Page<NguoiDung> findByVaiTro(Set<VaiTro> vaiTro, PageRequest of);
	//@Query("SELECT DISTINCT n FROM nguoidung n JOIN n.vaiTro v WHERE v IN :vaiTro")
	Optional<NguoiDung> findById(Integer id);
//	Page<NguoiDung> findByVaiTro(@Param("vaiTro") Set<VaiTro> vaiTro, Pageable pageable);
	Optional<NguoiDung> findByEmail(String email);
	@Query("Select nguoidung FROM NguoiDung nguoidung WHERE nguoidung.email = :keyword")
	NguoiDung searchByEmail(@Param("keyword") String keyword);

	//VaiTro findByVaiTro(String vaiTro);

	

}

