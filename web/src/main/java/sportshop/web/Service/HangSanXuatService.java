package sportshop.web.Service;

import sportshop.web.Entity.HangSanXuat;

import java.util.List;

public interface HangSanXuatService {

    List<HangSanXuat> findAll();

    List<HangSanXuat> searchByKeyword(String Keyword);

    Boolean save(HangSanXuat hangSanXuat);

    Boolean update(HangSanXuat hangSanXuat);

    HangSanXuat getById(Integer id);
}
