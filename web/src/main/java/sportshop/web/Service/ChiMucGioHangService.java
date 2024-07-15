package sportshop.web.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sportshop.web.Model.ChiMucGioHang;
import sportshop.web.Model.GioHang;
import sportshop.web.Model.MatHang;
import sportshop.web.Repository.ChiMucGioHangRepository;



@Service
public class ChiMucGioHangService {
	
	@Autowired
	private ChiMucGioHangRepository repo;
	
	
	public ChiMucGioHang getChiMucGioHangBySanPhamAndGioHang(MatHang mt,GioHang g)
	{
		return repo.findByMatHangAndGioHang(mt,g);
	}
	
	public ChiMucGioHang saveChiMucGiohang(ChiMucGioHang c)
	{
		return repo.save(c);
	}
	
	public void deleteChiMucGiohang(ChiMucGioHang c)
	{
		repo.delete(c);
	}
	
	
	public List<ChiMucGioHang> getChiMucGioHangByGioHang(GioHang g)
	{
		return repo.findByGioHang(g);
	}
	
	
	public void deleteAllChiMucGiohang(List<ChiMucGioHang> c)
	{
		repo.deleteAll(c);
	}

}
