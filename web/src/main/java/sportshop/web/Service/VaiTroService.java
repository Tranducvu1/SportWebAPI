package sportshop.web.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import sportshop.web.Model.VaiTro;
import sportshop.web.Repository.VaiTroRepository;



public class VaiTroService {
	@Autowired
	private VaiTroRepository repo;

	public VaiTro findByTenVaiTro(String tenVaiTro) {
		return repo.findByTenVaiTro(tenVaiTro);
	}

	public List<VaiTro> findAllVaiTro() {
		return repo.findAll();
	}


}
