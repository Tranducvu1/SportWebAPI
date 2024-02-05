package sportshop.web.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sportshop.web.Model.KhachHang;
import sportshop.web.Model.Log;
import sportshop.web.Repository.KhachHangRepository;
import sportshop.web.Repository.LogRepository;

@Service
public class KhachHangService {
	@Autowired
	private KhachHangRepository khachhangRepository;
	@Autowired
	LogRepository logRepository;
	//getall
	public List<KhachHang> findAll(){
		return khachhangRepository.findAll();
	}
	//search by name
	 public List<KhachHang> searchByKeyword(String Keyword) {
		 return khachhangRepository.searchByKeyword(Keyword);
	 }
	 
		
	 public Boolean save(KhachHang KhachHang) {
		 KhachHang KhachHangs = khachhangRepository.save(KhachHang);
		 if (KhachHangs != null) {
				Log log = new Log();
				log.setLogString("Thêm mới khách hàng id = " + KhachHangs.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}	
		 	return false;
		}
	 
		public Boolean update(KhachHang khachhang) {
			KhachHang khachHang = khachhangRepository.save(khachhang);
			if (khachHang != null) {
				Log log = new Log();
				log.setLogString("Cập nhật khách hàng id = " + khachHang.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}
			return false; 
		}
		
		public KhachHang getById(Integer id) {
			KhachHang original = requireOne(id);
			return original;
		}
		
		private KhachHang requireOne(Integer id) {
			return khachhangRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
		}
}