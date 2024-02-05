package sportshop.web.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sportshop.web.Model.NhanVien;
import sportshop.web.Model.Log;
import sportshop.web.Repository.NhanVienRepository;
import sportshop.web.Repository.LogRepository;

@Service
public class NhanVienService {
	@Autowired
	private NhanVienRepository nhannienRepository;
	@Autowired
	LogRepository logRepository;
	//getall
	public List<NhanVien> findAll(){
		return nhannienRepository.findAll();
	}
	//search by name
	 public List<NhanVien> searchByKeyword(String Keyword) {
		 return nhannienRepository.searchByKeyword(Keyword);
	 }
	 
		
	 public Boolean save(NhanVien NhanVien) {
		 NhanVien NhanViens = nhannienRepository.save(NhanVien);
		 if (NhanViens != null) {
				Log log = new Log();
				log.setLogString("Thêm mới nhân viên id = " + NhanViens.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}	
		 	return false;
		}
	 
		public Boolean update(NhanVien NhanVien) {
			NhanVien nhanvien = nhannienRepository.save(NhanVien);
			if (nhanvien != null) {
				Log log = new Log();
				log.setLogString("Cập nhật nhân viên id = " + nhanvien.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}
			return false; 
		}
		
		public NhanVien getById(Integer id) {
			NhanVien original = requireOne(id);
			return original;
		}
		
		private NhanVien requireOne(Integer id) {
			return nhannienRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
		}
}
