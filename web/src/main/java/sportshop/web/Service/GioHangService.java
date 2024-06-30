package sportshop.web.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sportshop.web.Model.ChiMucGioHang;
import sportshop.web.Model.GioHang;
import sportshop.web.Model.NguoiDung;
import sportshop.web.Repository.GioHangRepository;


@Service
public class GioHangService {
	@Autowired
	private GioHangRepository repo;
	
	public ChiMucGioHang getGioHangByNguoiDung(NguoiDung n)
	{
		return repo.findByNguoiDung(n);
	}
	
	public GioHang save(GioHang g)
	{
		return repo.save(g);
	}
}
