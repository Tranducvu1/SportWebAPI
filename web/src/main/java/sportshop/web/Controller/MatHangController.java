package sportshop.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import sportshop.web.DTO.MatHangDto;
import sportshop.web.Entity.MatHang;
import sportshop.web.Service.MatHangService;
import java.util.List;

/**
 * REST Controller for managing products (MatHang).
 * Handles HTTP requests for product operations.
 * All caching is managed at the service layer.
 */
@RestController
@RequestMapping("/api/v1/mathang")
public class MatHangController {
    
    private final MatHangService matHangService;

    @Autowired
    public MatHangController(MatHangService matHangService) {
        this.matHangService = matHangService;
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
    @GetMapping("/search")
    public ResponseEntity<List<MatHang>> searchByKeyword(
            @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(matHangService.searchByKeyword(keyword));
    }

    /**
     * GET /api/v1/mathang/product/{id} : Get product by ID.
     * Product data is cached at service layer.
     *
     * @param id product identifier
     * @return ResponseEntity<MatHang> containing product details or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatHang> getById(@PathVariable Integer id) {
        MatHang matHang = matHangService.getById(id);
        return matHang != null ? 
               ResponseEntity.ok(matHang) : 
               ResponseEntity.notFound().build();
    }

    /**
     * POST /api/v1/mathang : Create a new product.
     * Cache is automatically invalidated at service layer.
     *
     * @param matHang product to be created
     * @return ResponseEntity<Boolean> indicating success/failure
     */
    @PostMapping(produces = "application/json;charset=utf-8")
    public ResponseEntity<Boolean> create(@Valid @RequestBody MatHang matHang) {
        try {
            Boolean result = matHangService.save(matHang);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body(false);
        }
    }

    /**
     * PUT /api/v1/mathang/{id} : Update an existing product.
     * Cache is automatically invalidated at service layer.
     *
     * @param id product identifier
     * @param matHang updated product data
     * @return ResponseEntity<Boolean> indicating success/failure
     */
    @PutMapping(value = "/{id}", 
                consumes = "application/json",
                produces = "application/json;charset=utf-8")
    public ResponseEntity<Boolean> update(
            @PathVariable Integer id,
            @Valid @RequestBody MatHang matHang) {
        matHang.setId(id);
        Boolean result = matHangService.update(matHang);
        return ResponseEntity.ok(result);
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