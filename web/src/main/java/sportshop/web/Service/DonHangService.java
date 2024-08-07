package sportshop.web.Service;




import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sportshop.web.Model.Log;
import sportshop.web.Model.MatHang;

import sportshop.web.Model.DonHang;
import sportshop.web.Repository.DonHangRepository;
import sportshop.web.Repository.LogRepository;
import sportshop.web.Repository.MatHangRepository;





@Service
public class DonHangService {
	@Autowired
	private DonHangRepository DonHangRepository;
	@Autowired
	LogRepository logRepository;
	//getall
	public List<DonHang> findAll(){
		return DonHangRepository.findAll();
	}
	
	 
	//search by name
	 public List<DonHang> searchByKeyword(String Keyword) {
		 return DonHangRepository.searchByKeyword(Keyword);
	 }
	 
		
	 public Boolean save(DonHang donhang) {
		 donhang.setNgaydat(new Timestamp(System.currentTimeMillis()));
		 DonHang DonHangs = DonHangRepository.save(donhang);
		 if (DonHangs != null) {
				Log log = new Log();
				log.setLogString("Thêm mới đơn hàng id = " + DonHangs.getId());
				
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}	
		 	return false;
		}
	 
		public Boolean update(DonHang DonHang) {
			DonHang donhang = DonHangRepository.save(DonHang);
			if (donhang != null) {
				Log log = new Log();
				log.setLogString("Cập nhật đơn hàng id = " + donhang.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}
			return false; 
		}
		
		public DonHang getById(Integer id) {
			DonHang original = requireOne(id);
			return original;
		}
		
		private DonHang requireOne(Integer id) {
			return DonHangRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
		}
}