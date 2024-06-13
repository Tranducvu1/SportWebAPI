package sportshop.web.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sportshop.web.Model.HangSanXuat;
import sportshop.web.Model.Log;
import sportshop.web.Repository.HangSanXuatRepository;
import sportshop.web.Repository.LogRepository;


@Service
public class HangSanXuatService {
	@Autowired
	private HangSanXuatRepository hangSanXuatRepository;
	@Autowired
	LogRepository logRepository;
	
	public List<HangSanXuat> findAll(){
		return hangSanXuatRepository.findAll();
	}
	
	
	 public List<HangSanXuat> searchByKeyword(String Keyword) {
		 return hangSanXuatRepository.searchByKeyword(Keyword);
	 }

	 public Boolean save(HangSanXuat HangSanXuat) {
		 HangSanXuat HangSanXuats = hangSanXuatRepository.save(HangSanXuat);
		 if (HangSanXuats != null) {
				Log log = new Log();
				log.setLogString("Thêm mới đơn hàng id = " + HangSanXuats.getId());
				
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}	
		 	return false;
		}
	 
		public Boolean update(HangSanXuat hangsanxuat) {
			HangSanXuat HangSanXuat = hangSanXuatRepository.save(hangsanxuat);
			if (HangSanXuat != null) {
				Log log = new Log();
				log.setLogString("Cập nhật đơn hàng id = " + HangSanXuat.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}
			return false; 
		}
		
		public HangSanXuat getById(Integer id) {
			HangSanXuat original = requireOne(id);
			return original;
		}
		
		private HangSanXuat requireOne(Integer id) {
			return hangSanXuatRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
		}
}
