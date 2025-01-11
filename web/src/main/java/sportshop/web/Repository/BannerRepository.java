package sportshop.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import sportshop.web.Entity.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Integer>, JpaSpecificationExecutor<Banner> {
	

	
}
