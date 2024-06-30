package sportshop.web.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sportshop.web.Model.DanhMuc;
import sportshop.web.Model.DonHang;
import sportshop.web.Model.Log;
import sportshop.web.Model.MatHang;
import sportshop.web.Repository.DanhMucRepository;
import sportshop.web.Repository.LogRepository;

@Service
public class DanhMucService{
	@Autowired
	private DanhMucRepository danhmucRepository;
	@Autowired
	LogRepository logRepository;
	//getall
	public List<DanhMuc> findAll(){
		
		return danhmucRepository.findAll();
	}
	 public List<DanhMuc> searchByKeyword(String Keyword) {
		 return danhmucRepository.searchByKeyword(Keyword);
	 }
	public Boolean save(DanhMuc danhmuc) {
		DanhMuc danhmucs = danhmucRepository.save(danhmuc);
		 if (danhmucs != null) {
				Log log = new Log();
				log.setLogString("Thêm mới danh mục id = " + danhmucs.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
			return true;
		}
		return false; 
	}
	public Boolean update(DanhMuc danhmuc) {
		DanhMuc danhmucs = danhmucRepository.save(danhmuc);
		 if (danhmucs != null) {
				Log log = new Log();
				log.setLogString("Sửa danh mục id = " + danhmucs.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
			return true;
		}
		return false; 
	}
		public DanhMuc getById(Integer id) {
			DanhMuc original = requireOne(id);
			return original;
		}
		
		private DanhMuc requireOne(Integer id) {
			return danhmucRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
		}
		
		public MatHang getProductByIdFromCategory(DanhMuc danhMuc, Integer productId) {
		    List<MatHang> products = danhMuc.getMathangs();
		    for (MatHang product : products) {
		        if (product.getId() == productId) {
		            return product;
		        }
		    }
		    return null; // Trả về null nếu không tìm thấy sản phẩm
		}
		public Boolean deleteProduct(Integer id) {
			Optional<DanhMuc>  danhmuc = danhmucRepository.findById(id);
			if(danhmuc.isPresent()) {
				DanhMuc dm = danhmuc.get();
				danhmucRepository.delete(dm);
				Boolean existDanhMuc = danhmucRepository.existsById(id);
				if (!existDanhMuc) {
	                Log log = new Log();
	                log.setLogString("Xóa danh mục id = " + id);
	                log.setCreateTime(new Timestamp(System.currentTimeMillis()));
	                logRepository.save(log);
	                return true;
	            }

			}
			return null;
		}
		
		public Page<DanhMuc> getDanhMucPagination(int offset,int pageSize){
			Page<DanhMuc> danhmuc = danhmucRepository.findAll(PageRequest.of(offset, pageSize));
			return danhmuc;
			
		}
}
