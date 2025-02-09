package sportshop.web.Controller;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sportshop.web.Entity.Banner;
import sportshop.web.Service.BannerService;
import sportshop.web.Service.FileService;

@RestController
@RequestMapping("/api/v1/banner")
public class BannerController {
    
    @Autowired
    private BannerService bannerService;
    @Autowired
    private  FileService fileService;
    /**
     * Retrieves all banners from the cache or from the database if not cached.
     * 
     * @return ResponseEntity containing all banners.
     */
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
  
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestParam String mota,
            @RequestParam(required = false) MultipartFile imageFile) {
        if (mota.isEmpty() || imageFile == null || imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Vui lòng điền đầy đủ thông tin và đảm bảo giá trị hợp lệ.");
        }

        try {
            Banner banner = new Banner();
            banner.setMota(mota);

            // Đảm bảo tên file ảnh hợp lệ, sử dụng mô tả và thay thế khoảng trắng bằng dấu gạch dưới
            String fileName = mota.replaceAll("\\s+", "_") + ".png";  // Tên file: mô tả + .png

            String uploadDir = "src/main/resources/images/";  // Đảm bảo đường dẫn tới thư mục chứa ảnh

            // Tạo thư mục nếu chưa tồn tại
            java.nio.file.Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Lưu file vào thư mục images
            java.nio.file.Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath);  // Lưu tệp ảnh vào thư mục

            // Cập nhật đường dẫn ảnh vào đối tượng Banner
            banner.setHinhanh("/images/" + fileName);  // Lưu đường dẫn ảnh trong cơ sở dữ liệu

            
            Boolean result = bannerService.save(banner);
            if (result) {
                return ResponseEntity.ok("Banner đã được thêm thành công.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Có lỗi xảy ra khi thêm banner.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Có lỗi xảy ra. Vui lòng thử lại.");
        }
    }


    /**
     * Updates an existing banner in the system and clears the cache for "banners".
     * 
     * @param id     - The ID of the banner to be updated.
     * @param banner - The updated banner object.
     * @return ResponseEntity indicating whether the update was successful.
     */
    /**
     * Updates an existing banner in the system with a new description and/or image.
     * 
     * @param id         - The ID of the banner to be updated.
     * @param mota       - The updated description for the banner.
     * @param imageFile  - The updated image file for the banner.
     * @return ResponseEntity indicating whether the update was successful.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable Integer id,
            @RequestParam String mota,
            @RequestParam(required = false) MultipartFile imageFile) {

        try {
            // Tìm banner hiện tại từ database
            Banner existingBanner = bannerService.getById(id);
            if (existingBanner == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Banner không tồn tại.");
            }

            // Cập nhật mô tả
            existingBanner.setMota(mota);

            if (imageFile != null && !imageFile.isEmpty()) {
                String uploadDir = "src/main/resources/images/";

                // Đảm bảo thư mục tồn tại
                java.nio.file.Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Lưu file với tên mới dựa trên mô tả
                String fileName = mota.replaceAll("\\s+", "_") + ".png";
                java.nio.file.Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath);

                // Cập nhật đường dẫn file vào banner
                existingBanner.setHinhanh("/images/" + fileName);
            }

            // Gọi service để lưu lại banner đã cập nhật
            Boolean result = bannerService.update(existingBanner);
            if (result) {
                return ResponseEntity.ok("Cập nhật banner thành công.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Có lỗi xảy ra khi cập nhật banner.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi. Vui lòng thử lại.");
        }
    }


    /**
     * Retrieves a banner by its ID from the cache, or the database if not cached.
     * Access is restricted to users with the 'ADMIN' role.
     * 
     * @param id - The ID of the banner to retrieve.
     * @return ResponseEntity containing the banner if found, or a 404 status if not.
     */
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
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
  
    @GetMapping("/pagination/{offset}/{pageSize}")
    private ResponseEntity<Page<Banner>> getBannerPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<Banner> banners = bannerService.getBanner(offset, pageSize);
        return ResponseEntity.ok(banners);
    }
}
