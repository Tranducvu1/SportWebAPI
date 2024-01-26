package sportshop.web.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sportshop.web.Model.NhaCungCap;
import sportshop.web.Model.Log;
import sportshop.web.Repository.NhaCungCapRepository;
import sportshop.web.Repository.LogRepository;

@Service
public class NhaCungCapService {
	@Autowired
	private NhaCungCapRepository nhacungcapRepository;
	@Autowired
	LogRepository logRepository;
	//getall
	public List<NhaCungCap> findAll(){
		return nhacungcapRepository.findAll();
	}
	//search by name
	 public List<NhaCungCap> searchByKeyword(String Keyword) {
		 return nhacungcapRepository.searchByKeyword(Keyword);
	 }
	 
		
	 public Boolean save(NhaCungCap NhaCungCap) {
		 NhaCungCap NhaCungCaps = nhacungcapRepository.save(NhaCungCap);
		 if (NhaCungCaps != null) {
				Log log = new Log();
				log.setLogString("Thêm mới lĩnh vực id = " + NhaCungCaps.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}	
		 	return false;
		}
	 
		public Boolean update(NhaCungCap NhaCungCap) {
			NhaCungCap nhacuhngcap = nhacungcapRepository.save(NhaCungCap);
			if (NhaCungCap != null) {
				Log log = new Log();
				log.setLogString("Cập nhật sinh viên id = " + NhaCungCap.getId());
				log.setCreateTime(new Timestamp(System.currentTimeMillis()));
				logRepository.save(log);
				return true;
			}
			return false; 
		}
		
		public NhaCungCap getById(Integer id) {
			NhaCungCap original = requireOne(id);
			return original;
		}
		
		private NhaCungCap requireOne(Integer id) {
			return nhacungcapRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
		}
}