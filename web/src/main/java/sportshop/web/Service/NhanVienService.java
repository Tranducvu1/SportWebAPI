package sportshop.web.Service;

import java.util.List;
import sportshop.web.Entity.NhanVien;

public interface NhanVienService {
    List<NhanVien> findAll();
    List<NhanVien> searchByKeyword(String keyword);
    Boolean save(NhanVien nhanVien);
    Boolean update(NhanVien nhanVien);
    NhanVien getById(Integer id);
}
