package sportshop.web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sportshop.web.DTO.Role;
import sportshop.web.Entity.NguoiDung;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Long> {
    NguoiDung findByEmail(String email);
    List<NguoiDung> findByhoten(String hoten);
	List<NguoiDung> findByEmailContainingAndHotenContainingAndRole(String email, String hoten, Role valueOf); 
}
