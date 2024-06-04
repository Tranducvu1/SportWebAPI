package sportshop.web.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sportshop.web.Model.MatHang;
import sportshop.web.Model.DanhMuc;
import sportshop.web.Model.Log;
import sportshop.web.Repository.MatHangRepository;
import sportshop.web.Repository.LogRepository;

@Service
public class MatHangService {
	@Autowired
	private MatHangRepository mathangRepository;
	@Autowired
	LogRepository logRepository;
	//getall
	public List<MatHang> findAll(){
		return mathangRepository.findAll();
	}
	
	 public MatHang getProduct(Integer productId){
	        return mathangRepository.findById(productId).get(); 
	    }
	 
	
	 
	//search by name
	 public List<MatHang> searchByKeyword(String Keyword) {
		 return mathangRepository.searchByKeyword(Keyword);
	 }
	 
		
	 public MatHang getId(int id) {
		 
		return mathangRepository.findByIdWithDanhMuc(id);
		 
	 }
	 public Boolean save(MatHang MatHang) {
		 MatHang MatHangs = mathangRepository.save(MatHang);
		 if (MatHangs != null) {
				Log log = new Log();
				log.setLogString("Thêm mới mặt hàng id = " + MatHangs.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}	
		 	return false;
		}
	 
		public Boolean update(MatHang MatHang) {
			MatHang MatHangs = mathangRepository.save(MatHang);
			if (MatHangs != null) {
				Log log = new Log();
				log.setLogString("Cập nhật mặt hàng id = " + MatHangs.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}
			return false; 
		}
		
		public MatHang getById(Integer id) {
			MatHang original = requireOne(id);
			return original;
		}
		
		private MatHang requireOne(Integer id) {
			return mathangRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
		}

		public List<MatHang> getMatHangByDanhMuc(DanhMuc danhMuc) {
	        return mathangRepository.findByDanhMuc(danhMuc);
	    }

		
}
