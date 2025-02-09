package sportshop.web.Controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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

import com.querydsl.core.types.Path;

import sportshop.web.DTO.MatHangDto;
import sportshop.web.Entity.DanhMuc;
import sportshop.web.Entity.HangSanXuat;
import sportshop.web.Entity.MatHang;
import sportshop.web.Service.FileService;
import sportshop.web.Service.MatHangService;

/**
 * REST Controller for managing products (MatHang).
 * Handles HTTP requests for product operations.
 * All caching is managed at the service layer.
 */
@RestController
@RequestMapping("/api/v1/mathang")
public class MatHangController {
    
    private final MatHangService matHangService;
    private final FileService fileService;
    @Autowired
    public MatHangController(MatHangService matHangService,FileService fileService) {
        this.matHangService = matHangService;
        this.fileService = fileService;
    }

    /**
     * GET /api/v1/mathang : Retrieve all products.
     * Data is cached at service layer for optimal performance.
     *
     * @return ResponseEntity<List<MatHang>> containing all products
     */
    @GetMapping
    public ResponseEntity<List<MatHang>> findAll() {
    	System.out.println(matHangService.findAll());
        return ResponseEntity.ok(matHangService.findAll());
    }

    /**
     * GET /api/v1/mathang/search : Search products by keyword.
     * Search results are cached at service layer.
     *
     * @param keyword search term for filtering products
     * @return ResponseEntity<List<MatHang>> containing matching products
     */
    @GetMapping("/search/keyword")
    public ResponseEntity<List<MatHang>> searchByKeyword(@RequestParam(required = false) String keyword) {
    	return ResponseEntity.ok(matHangService.searchByKeyword(keyword));
        //return ResponseEntity.ok(matHangService.searchByKeyword(keyword));
    }
    /**
     * POST /api/v1/mathang : Create a new product.
     * Cache is automatically invalidated at service layer.
     *
     * @param matHang product to be created
     * @return ResponseEntity<Boolean> indicating success/failure
     */
    @PostMapping("/create")
    public ResponseEntity<String> create(
            @RequestParam String mamathang,
            @RequestParam String tenmathang,
            @RequestParam Integer dongia,
            @RequestParam Integer danhgia,
            @RequestParam String mota,
            @RequestParam Integer danhmuc_id,
            @RequestParam Integer ma_hang_sx,
            @RequestParam(required = false) MultipartFile imageFile,
            @RequestParam String gioi_tinh,
            @RequestParam String size,
            @RequestParam Integer soluong,
            @RequestParam Integer giamgia,
            @RequestParam(required = false) String ngaythem) {

        // Kiểm tra dữ liệu đầu vào
        if (mamathang.isEmpty() || tenmathang.isEmpty() || dongia <= 0 || danhgia < 0 || mota.isEmpty()
                || danhmuc_id == null || ma_hang_sx == null || gioi_tinh.isEmpty()
                || size.isEmpty() || soluong <= 0 || giamgia < 0) {
            return ResponseEntity.badRequest().body("Vui lòng điền đầy đủ thông tin và đảm bảo giá trị hợp lệ.");
        }

        try {
            // Tạo đối tượng MatHang
            MatHang matHang = new MatHang();
            matHang.setMamathang(mamathang);
            matHang.setTenmathang(tenmathang);
            matHang.setDongia(dongia);
            matHang.setDanhgia(danhgia);
            matHang.setMota(mota);

            // Liên kết DanhMuc và HangSanXuat
            DanhMuc danhMuc = new DanhMuc();
            danhMuc.setId(danhmuc_id);
            matHang.setDanhMuc(danhMuc);
            
            HangSanXuat hangSanXuat = new HangSanXuat();
            hangSanXuat.setId(ma_hang_sx);
            matHang.setHangSanXuat(hangSanXuat);

            // Ánh xạ các trường còn lại
            matHang.setGender(gioi_tinh);
            matHang.setSize(size);
            matHang.setSoluong(soluong);
            matHang.setGiamgia(giamgia);
            matHang.setNgaythem(ngaythem);

            // Xử lý hình ảnh
            if (imageFile != null && !imageFile.isEmpty()) {
                // Tạo tên file dựa trên mã sản phẩm
                String fileName = mamathang + ".png";
                String uploadDir = "src/main/resources/images/";

                // Lưu file ảnh vào thư mục images
                java.nio.file.Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                
                // Lưu file vào thư mục
                java.nio.file.Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath);
                
                // Cập nhật đường dẫn ảnh vào database
                matHang.setHinhanh("/images/" + fileName);
            }

            // Lưu sản phẩm vào cơ sở dữ liệu
            boolean result = matHangService.save(matHang);

            if (result) {
                return ResponseEntity.ok("Sản phẩm đã được thêm thành công.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body("Có lỗi xảy ra khi thêm sản phẩm.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Có lỗi xảy ra. Vui lòng thử lại.");
        }
    }


    /**
     * GET /api/v1/mathang/product/{id} : Get product by ID.
     * Product data is cached at the service layer.
     *
     * @param id product identifier
     * @return ResponseEntity<MatHang> containing product details or 404 if not found
     */
    @GetMapping("/{productId}")
    public ResponseEntity<MatHang> getById(@PathVariable Integer productId) {
        // Ensure that id is valid
        if (productId == null || productId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
        }
        MatHang matHang = matHangService.getById(productId);
        return matHang != null ? 
               ResponseEntity.ok(matHang) : 
               ResponseEntity.notFound().build();
    }
    // Search by preoducts
    @GetMapping("/search/{tenmathang}")
    public ResponseEntity<MatHang> findByProductName(@PathVariable String tenmathang) {
        
        String normalizedTenmathang = removeVietnameseDiacritics(tenmathang.replace("-", " ")).toLowerCase();
        MatHang matHang = matHangService.findByNormalizedProductName(normalizedTenmathang);
        return matHang != null ? ResponseEntity.ok(matHang) : ResponseEntity.notFound().build();
    }


    public String removeVietnameseDiacritics(String input) {
        String normalized = input.replaceAll("[áàảãạăắằẳẵặâấầẩẫậ]", "a")
                                  .replaceAll("[éèẻẽẹêếềểễệ]", "e")
                                  .replaceAll("[íìỉĩị]", "i")
                                  .replaceAll("[óòỏõọôốồổỗộơớờởỡợ]", "o")
                                  .replaceAll("[úùủũụưứừửữự]", "u")
                                  .replaceAll("[ýỳỷỹỵ]", "y")
                                  .replaceAll("[đ]", "d")
                                  .replaceAll("[ÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬ]", "A")
                                  .replaceAll("[ÉÈẺẼẸÊẾỀỂỄỆ]", "E")
                                  .replaceAll("[ÍÌỈĨỊ]", "I")
                                  .replaceAll("[ÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢ]", "O")
                                  .replaceAll("[ÚÙỦŨỤƯỨỪỬỮỰ]", "U")
                                  .replaceAll("[ÝỲỶỸỴ]", "Y")
                                  .replaceAll("[Đ]", "D");
        return normalized;
    }




    /**
     * PUT /api/v1/mathang/{id} : Update an existing product.
     * Cache is automatically invalidated at the service layer.
     *
     * @param id product identifier
     * @param matHang updated product data
     * @return ResponseEntity<Boolean> indicating success/failure
     */
    @PutMapping(value = "/update/{id}", consumes = "multipart/form-data", produces = "application/json;charset=utf-8")
    public ResponseEntity<String> update(
            @PathVariable Integer id,
            @RequestParam String mamathang,
            @RequestParam String tenmathang,
            @RequestParam Integer dongia,
            @RequestParam Integer danhgia,
            @RequestParam String mota,
            @RequestParam Integer danhmuc_id,
            @RequestParam Integer ma_hang_sx,
            @RequestParam(required = false) MultipartFile imageFile,
            @RequestParam String gioi_tinh,
            @RequestParam String size,
            @RequestParam Integer soluong,
            @RequestParam Integer giamgia,
            @RequestParam(required = false) String ngaythem) {

        try {
            // Validate input data
            if (dongia == null || dongia <= 0 ||
                danhgia == null || danhgia < 0 ||
                soluong == null || soluong < 0 || 
                giamgia == null || giamgia < 0 ||
                danhmuc_id == null || ma_hang_sx == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Dữ liệu không hợp lệ, vui lòng kiểm tra lại.");
            }

            // Find existing product
            MatHang existingProduct = matHangService.getById(id);
            if (existingProduct == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy sản phẩm để cập nhật.");
            }

            // Update basic fields
            existingProduct.setMamathang(mamathang);
            existingProduct.setTenmathang(tenmathang);
            existingProduct.setDongia(dongia);
            existingProduct.setDanhgia(danhgia);
            existingProduct.setMota(mota);
            existingProduct.setGender(gioi_tinh);
            existingProduct.setSize(size);
            existingProduct.setSoluong(soluong);
            existingProduct.setGiamgia(giamgia);

            // Update date if provided
            if (ngaythem != null && !ngaythem.isEmpty()) {
                existingProduct.setNgaythem(ngaythem);
            }

            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = mamathang + ".png";
                String uploadDir = "src/main/resources/images/";
                java.nio.file.Path uploadPath = Paths.get(uploadDir);
                
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Xóa file ảnh cũ nếu tồn tại
                if (existingProduct.getHinhanh() != null) {
                    String oldImagePath = uploadDir + existingProduct.getHinhanh().replace("/images/", "");
                    try {
                        Files.deleteIfExists(Paths.get(oldImagePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // Lưu file ảnh mới
                java.nio.file.Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                
                // Cập nhật đường dẫn mới trong database
                existingProduct.setHinhanh("/images/" + fileName);
            }

            // Update category and manufacturer references
            // Instead of creating new instances, set only the IDs
            existingProduct.getDanhMuc().setId(danhmuc_id);
            existingProduct.getHangSanXuat().setId(ma_hang_sx);

            // Save updated product
            Boolean result = matHangService.update(existingProduct);
            
            if (result) {
                return ResponseEntity.ok("Sản phẩm đã được cập nhật thành công.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Có lỗi xảy ra khi cập nhật sản phẩm.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Có lỗi xảy ra. Vui lòng thử lại.");
        }
    }


    /**
     * GET /api/v1/mathang/sort/{field} : Get sorted products.
     * Sorted results are cached at service layer.
     *
     * @param field field to sort by
     * @return ResponseEntity<List<MatHang>> containing sorted products
     */
    @GetMapping("/sort/{field}")
    public ResponseEntity<List<MatHang>> getSortedProducts(
            @PathVariable String field) {
        return ResponseEntity.ok(matHangService.findProductsWtihSorting(field));
    }

    /**
     * GET /api/v1/mathang/filter : Get filtered and paginated products.
     * Filtered results are cached at service layer.
     *
     * @param offset starting point for pagination
     * @param pageSize number of items per page
     * @param danhMucId category filter
     * @param hangSanXuatId manufacturer filter
     * @param priceRange price range filter
     * @param sortOrder sort direction
     * @return ResponseEntity<Page<MatHangDto>> containing filtered products
     */
    @GetMapping("/filter")
    public ResponseEntity<Page<MatHangDto>> getFilteredProducts(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) Integer danhMucId,
            @RequestParam(required = false) Integer hangSanXuatId,
            @RequestParam(required = false) String priceRange,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        
        // Validate page size
        if (pageSize <= 0) {
            return ResponseEntity.badRequest().build();
        }

        // Get filtered and paginated results
        Page<MatHangDto> result = matHangService.filterAndSortProducts(
            danhMucId, 
            hangSanXuatId, 
            priceRange, 
            sortOrder, 
            offset, 
            pageSize
        );

        return ResponseEntity.ok(result);
    }

    /**
     * DELETE /api/v1/mathang/{id} : Delete a product.
     * Cache is automatically invalidated at service layer.
     *
     * @param id product identifier to delete
     * @return ResponseEntity<String> containing status message
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        try {
            Boolean isDeleted = matHangService.deleteProduct(id);
            
            if (isDeleted) {
                return ResponseEntity.ok("Product successfully deleted");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                       .body("Product not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                   .body("An error occurred while deleting product");
        }
    }

}