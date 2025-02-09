package sportshop.web.Service.ipml;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import sportshop.web.Entity.Banner;
import sportshop.web.Repository.BannerRepository;
import sportshop.web.Service.BannerService;

@Service
@Slf4j
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    @Autowired
    public BannerServiceImpl(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Cacheable(value = "bannerList")
    @Override
    public List<Banner> findAll() {
        log.debug("Fetching all banners");
        return bannerRepository.findAll();
    }

    @CacheEvict(value = "bannerList", allEntries = true)
    @Override
    public Boolean save(Banner banner) {
        Banner savedBanner = bannerRepository.save(banner);
        if (savedBanner != null) {
            log.info("Banner saved successfully: {}", savedBanner);
            return true;
        }
        log.warn("Failed to save banner: {}", banner);
        return false;
    }

    @CacheEvict(value = "bannerList", allEntries = true)
    @Override
    public Boolean update(Banner banner) {
        Banner updatedBanner = bannerRepository.save(banner);
        if (updatedBanner != null) {
            log.info("Banner updated successfully: {}", updatedBanner);
            return true;
        }
        log.warn("Failed to update banner: {}", banner);
        return false;
    }

    @Cacheable(value = "banner", key = "#id")
    @Override
    public Banner getById(Integer id) {
        Banner banner = requireOne(id);
        log.debug("Fetched banner by ID {}: {}", id, banner);
        return banner;
    }

    private Banner requireOne(Integer id) {
        return bannerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Banner not found with ID: {}", id);
                    return new NoSuchElementException("Resource not found: " + id);
                });
    }

    @Cacheable(value = "pagedBannerList", key = "{#offset, #pageSize}")
    @Override
    public Page<Banner> getBanner(int offset, int pageSize) {
        log.debug("Fetching banners with offset {} and pageSize {}", offset, pageSize);
        return bannerRepository.findAll(PageRequest.of(offset, pageSize));
    }

    @CacheEvict(value = {"bannerList", "banner"}, allEntries = true)
    @Override
    public Boolean deleteBanner(Integer id) throws Exception {
        Optional<Banner> bannerOptional = bannerRepository.findById(id);
        if (bannerOptional.isPresent()) {
            Banner banner = bannerOptional.get();
            bannerRepository.delete(banner);
            if (!bannerRepository.existsById(id)) {
                log.info("Banner deleted successfully with ID: {}", id);
                return true;
            }
        } else {
            log.error("Failed to delete banner. Banner not found with ID: {}", id);
        }
        return false;
    }
}
