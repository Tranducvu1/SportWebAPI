package sportshop.web.Service;

import java.util.List;
import sportshop.web.Entity.NhaCungCap;

public interface NhaCungCapService {
    List<NhaCungCap> findAll();
    List<NhaCungCap> searchByKeyword(String Keyword);
    Boolean save(NhaCungCap nhaCungCap);
    Boolean update(NhaCungCap nhaCungCap);
    NhaCungCap getById(Integer id);
}
