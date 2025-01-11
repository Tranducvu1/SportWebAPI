package sportshop.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import sportshop.web.Entity.Banner;
import sportshop.web.Service.BannerService;

@RestController
@RequestMapping("/api/v1/banner")
public class BannerController {
    
    @Autowired
    private BannerService bannerService;

    /**
     * Retrieves all banners from the cache or from the database if not cached.
     * 
     * @return ResponseEntity containing all banners.
     */
    @Cacheable(value = "banners")
    @GetMapping()
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(bannerService.findAll());
    }

    /**
     * Creates a new banner in the system and clears the cache for "banners".
     * 
     * @param banner - The banner object to be created.
     * @return ResponseEntity indicating whether the creation was successful.
     */
    @CacheEvict(value = "banners", allEntries = true) // Clear cache when a new banner is added
    @PostMapping(path = "/create", produces = "application/json;charset=utf-8")
    public ResponseEntity<Boolean> create(@RequestBody Banner banner) {
        Boolean result = bannerService.save(banner);
        return ResponseEntity.ok(result);
    }

    /**
     * Updates an existing banner in the system and clears the cache for "banners".
     * 
     * @param id     - The ID of the banner to be updated.
     * @param banner - The updated banner object.
     * @return ResponseEntity indicating whether the update was successful.
     */
    @CacheEvict(value = "banners", allEntries = true) // Clear cache when banner is updated
    @PutMapping(path = "/update/{id}", produces = "application/json;charset=utf-8")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id, @RequestBody @Valid Banner banner) {
        banner.setId(id);
        Boolean result = bannerService.update(banner);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a banner by its ID from the cache, or the database if not cached.
     * Access is restricted to users with the 'ADMIN' role.
     * 
     * @param id - The ID of the banner to retrieve.
     * @return ResponseEntity containing the banner if found, or a 404 status if not.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Cacheable(value = "bannerById", key = "#id") // Cache result by banner ID
    @GetMapping("/find/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Integer id) {
        Banner banner = bannerService.getById(id);
        if (banner != null) {
            return ResponseEntity.ok(banner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a banner by its ID and clears the cache for "banners".
     * 
     * @param id - The ID of the banner to be deleted.
     * @return ResponseEntity indicating whether the deletion was successful or not.
     */
    @CacheEvict(value = "banners", allEntries = true) // Clear cache when banner is deleted
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) throws Exception {
        try {
            Boolean isDeleted = bannerService.deleteBanner(id);
            if (isDeleted) {
                return ResponseEntity.ok("Banner deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Banner not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Retrieves banners with pagination and caches the result.
     * 
     * @param offset   - The starting point for pagination.
     * @param pageSize - The number of banners per page.
     * @return ResponseEntity containing the paginated list of banners.
     */
    @Cacheable(value = "bannerPagination", key = "{#offset, #pageSize}")
    @GetMapping("/pagination/{offset}/{pageSize}")
    private ResponseEntity<Page<Banner>> getBannerPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<Banner> banners = bannerService.getBanner(offset, pageSize);
        return ResponseEntity.ok(banners);
    }
}
