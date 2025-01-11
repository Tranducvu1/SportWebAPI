package sportshop.web.Service;

import org.springframework.data.domain.Page;
import sportshop.web.Entity.Banner;

import java.util.List;

public interface BannerService {
    List<Banner> findAll();
    Boolean save(Banner banner);
    Boolean update(Banner banner);
    Banner getById(Integer id);
    Page<Banner> getBanner(int offset, int pageSize);
    Boolean deleteBanner(Integer id) throws Exception;
}
