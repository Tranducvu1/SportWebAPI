package sportshop.web.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import sportshop.web.Model.VaiTro;
import sportshop.web.Repository.LogRepository;
import sportshop.web.Repository.UserRepository;
import sportshop.web.Repository.VaiTroRepository;
import sportshop.web.DTO.TaiKhoanDTO;
import sportshop.web.Model.Log;
import sportshop.web.Model.NguoiDung;


@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository UserRepo;

	@Autowired
	private VaiTroRepository vaiTroRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	
	public NguoiDung findByEmail(String email) {
		return UserRepo.findByEmail(email);
	}

	
	public NguoiDung findByConfirmationToken(String confirmationToken) {
		return null;
	}

	
	public NguoiDung saveUserForMember(NguoiDung nd) {
		nd.setPassword(bCryptPasswordEncoder.encode(nd.getPassword()));
		Set<VaiTro> setVaiTro = new HashSet<>();
		setVaiTro.add(vaiTroRepo.findByTenVaiTro("ROLE_MEMBER"));
		nd.setVaiTro(setVaiTro);
		return UserRepo.save(nd);
	}

	
	public NguoiDung findById(Integer id) {
		NguoiDung nd = UserRepo.findById(id).get();
		return nd;
	}

	
	public NguoiDung updateUser(NguoiDung nd) {
		return UserRepo.save(nd);
	}

	
	public void changePass(NguoiDung nd, String newPass) {
		nd.setPassword(bCryptPasswordEncoder.encode(newPass));
		UserRepo.save(nd);
	}

	
	public Page<NguoiDung> getUserByVaiTro(Set<VaiTro> vaiTro,  int page) {
		return UserRepo.findByVaiTro(vaiTro, PageRequest.of(page - 1, 6));
	}

	
	public List<NguoiDung> getUserByVaiTro(Set<VaiTro> vaiTro) {
		return UserRepo.findByVaiTro(vaiTro);
	}

	
	public NguoiDung saveUserForAdmin(TaiKhoanDTO dto) {
		NguoiDung nd = new NguoiDung();
		nd.setHoTen(dto.getHoTen());
		nd.setDiaChi(dto.getDiaChi());
		nd.setEmail(dto.getEmail());
		nd.setSoDienThoai(dto.getSdt());
		nd.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
		
		Set<VaiTro> vaiTro  = new HashSet<>();
		vaiTro.add(vaiTroRepo.findByTenVaiTro(dto.getTenVaiTro()));
		nd.setVaiTro(vaiTro);
		
		return UserRepo.save(nd);
	}

	
	public void deleteById(Integer id) {
		UserRepo.deleteById(id);
	}

}
