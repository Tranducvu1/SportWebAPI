package sportshop.web.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import sportshop.web.Model.Banner;
import sportshop.web.Model.DonHang;
import sportshop.web.Model.Log;
import sportshop.web.Model.MatHang;
import sportshop.web.Repository.BannerRepository;

import sportshop.web.Repository.LogRepository;

@Service
public class BannerService {
	@Autowired
	private BannerRepository bannerRepository;
	@Autowired
	LogRepository logRepository;
	//getall
	public List<Banner> findAll(){
		return bannerRepository.findAll();
	}	
	 public Boolean save(Banner banner) {
		 Banner banners = bannerRepository.save(banner);
		 if (banners != null) {
				Log log = new Log();
				log.setLogString("Thêm mới banner id = " + banners.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}	
		 	return false;
		}
	 
		public Boolean update(Banner banner) {
			Banner banners = bannerRepository.save(banner);
			if (banners != null) {
				Log log = new Log();
				log.setLogString("Cập nhật banner id = " + banners.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}
			return false; 
		}
		
		public Banner getById(Integer id) {
			Banner original = requireOne(id);
			return original;
		}
		
		private Banner requireOne(Integer id) {
			return bannerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
		}
//	    public Page<MatHang> getMatHangByDanhMuc(int offset,int pagesize) {
//    	Page<MatHang> mathang =  mathangRepository.findAll(PageRequest.of(offset,pagesize));
//        return mathang;
//    }
		public Page<Banner> getbanner(int offset,int pagesize){
			Page<Banner> bn = bannerRepository.findAll(PageRequest.of(offset, pagesize)); 
			return bn;
		}
		
		  public Boolean deleteBanner(Integer id) throws Exception {
		        Optional<Banner> matHangOptional = bannerRepository.findById(id);
		        if (matHangOptional.isPresent()) {
		            Banner bn = matHangOptional.get();
		            bannerRepository.delete(bn);
		            
		            // Verify if the deletion was successful
		            boolean exists = bannerRepository.existsById(id);
		            if (!exists) {
		                Log log = new Log();
		                log.setLogString("Xóa banner id = " + id);
		                log.setCreateTime(new Timestamp(System.currentTimeMillis()));
		                logRepository.save(log);
		                return true;
		            }
		        }
		        return false;
		    }
		  
}