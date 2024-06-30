package sportshop.web.Repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sportshop.web.Model.NguoiDung;
import sportshop.web.Model.VaiTro;


@Repository
public interface UserRepository  extends JpaRepository<NguoiDung,Integer>, JpaSpecificationExecutor<NguoiDung> {

	Page<NguoiDung> findByVaiTro(Set<VaiTro> vaiTro, PageRequest of);

	NguoiDung findByEmail(String email);

	List<NguoiDung> findByVaiTro(Set<VaiTro> vaiTro);

	

}

