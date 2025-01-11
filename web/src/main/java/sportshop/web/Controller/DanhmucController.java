package sportshop.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sportshop.web.Entity.DanhMuc;
import sportshop.web.Entity.MatHang;
import sportshop.web.Service.DanhMucService;

@RestController
@RequestMapping("/api/v1/danhmuc")
public class DanhmucController {
    
    // Injecting the DanhMucService to handle business logic for category management
    @Autowired
    private DanhMucService danhMucService;

    /**
     * Retrieves all categories from the database.
     * 
     * @return ResponseEntity with a list of all categories.
     */
    @GetMapping()
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.ok(danhMucService.findAll());
    }

    /**
     * Creates a new category in the system.
     * 
     * @param danhmuc - The category object to be created.
     * @return ResponseEntity indicating whether the category was successfully created.
     */
    @PostMapping(path="/danhmuc/create", produces = "application/json;charset=utf-8")
    public ResponseEntity<Boolean> createCategory(@RequestBody DanhMuc danhmuc) {
        System.out.println(danhmuc);
        Boolean result = danhMucService.save(danhmuc);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a product from a specific category by their IDs.
     * 
     * @param danhMucId - The ID of the category.
     * @param productId - The ID of the product.
     * @return ResponseEntity with the product if found, or a 404 status if not.
     */
    @GetMapping("/danhmuc/{danhMucId}/products/{productId}")
    public ResponseEntity<Object> getProductByCategory(@PathVariable("danhMucId") Integer danhMucId, 
                                                       @PathVariable("productId") Integer productId) {
        DanhMuc category = danhMucService.getById(danhMucId);
        if (category != null) {
            MatHang product = danhMucService.getProductByIdFromCategory(category, productId);
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an existing category with new data.
     * 
     * @param danhmuc - The updated category object.
     * @return ResponseEntity indicating whether the update was successful.
     */
    @PutMapping(path="/danhmuc/update", consumes = "application/json", produces = "application/json;charset=utf-8")
    public ResponseEntity<Boolean> updateCategory(@RequestBody @Valid DanhMuc danhmuc) {
        Boolean result = danhMucService.update(danhmuc);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a category by its ID.
     * 
     * @param id - The ID of the category to retrieve.
     * @return The category object if found, otherwise a 404 status.
     */
    @GetMapping("/danhmuc/{id}")
    public DanhMuc getCategoryById(@PathVariable("id") Integer id) {
        return danhMucService.getById(id);
    }

    /**
     * Searches for categories based on a keyword.
     * 
     * @param keyword - The keyword used to filter categories.
     * @return ResponseEntity with a list of matching categories.
     */
    @GetMapping("/danhmuc/search")
    public ResponseEntity<Object> findByKeyword(@RequestParam(value = "keyword", required = false) String keyword) {        
        return ResponseEntity.ok(danhMucService.searchByKeyword(keyword));
    }

    /**
     * Deletes a category by its ID.
     * 
     * @param id - The ID of the category to be deleted.
     * @return ResponseEntity indicating the outcome of the deletion operation.
     */
    @DeleteMapping("/danhmuc/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        try {
            Boolean isDeleted = danhMucService.deleteProduct(id);
            if(isDeleted) {
                return ResponseEntity.ok("Category deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
            }
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Retrieves categories with pagination support.
     * 
     * @param offset - The starting point for pagination.
     * @param pageSize - The number of categories per page.
     * @return ResponseEntity with the paginated categories.
     */
    @GetMapping("/danhmuc/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<DanhMuc>> getCategoryPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<DanhMuc> categories = danhMucService.getDanhMucPagination(offset, pageSize);
        return ResponseEntity.ok(categories);
    }
}
