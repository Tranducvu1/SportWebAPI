package sportshop.web.Controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sportshop.web.Entity.DonHang;
import sportshop.web.Entity.MatHang;
import sportshop.web.Entity.NguoiDung;
import sportshop.web.Service.DonHangService;
import sportshop.web.Service.MatHangService;
import sportshop.web.Service.NguoiDungService;

@RestController
@RequestMapping("/api/v1/orders")
public class DonHangController {

    // Injecting services responsible for business logic related to orders, products, and users
    @Autowired
    private DonHangService orderService;

    @Autowired
    private MatHangService productService;

    @Autowired
    private NguoiDungService nguoiDungService;

    /**
     * Creates a new order for a given product and quantity.
     * This method evicts the cache associated with a user’s orders to ensure data freshness.
     *
     * @param productId - The ID of the product being ordered.
     * @param quantity - The quantity of the product.
     * @param userId - The ID of the user placing the order.
     * @return - Response containing the created order details, or an error message if the process fails.
     */
    @PostMapping("/{productId}")
    public ResponseEntity<?> createOrder(@PathVariable Integer productId, @RequestParam Integer quantity, @RequestParam Long userId) {
        try {
            // Retrieve product details from the database
            MatHang mathang = productService.getId(productId);
            if (mathang == null) {
                return ResponseEntity.notFound().build(); // Return a 404 if the product is not found
            }

            // Validate if the available stock is sufficient for the requested quantity
            if (mathang.getSoluong() < quantity) {
                return ResponseEntity.badRequest().body("Insufficient stock"); // Return a 400 if there is not enough stock
            }
            
            // Create a new order object
            DonHang donhang = new DonHang();
            donhang.setTenmathang(mathang.getTenmathang()); // Set the product name
            donhang.setSoluong(quantity); // Set the ordered quantity
            donhang.setHinhanh(mathang.getHinhanh()); // Set the product image
            donhang.setNgaydat(new Timestamp(System.currentTimeMillis())); // Set the order date
            donhang.setNgaydukiennhan(Timestamp.valueOf(LocalDateTime.now().plusDays(4))); // Set the expected delivery date

            // Calculate the total order price
            Integer price = mathang.getDongia();
            BigDecimal totalMoney = new BigDecimal(price).multiply(new BigDecimal(quantity));
            donhang.setMoney(totalMoney); // Set the total price
            donhang.setPhivanchuyen(new BigDecimal("30000")); // Set a fixed shipping fee

            // Fetch and validate the user information
            NguoiDung nguoiDung = nguoiDungService.getUserById(userId);
            if (nguoiDung == null) {
                return ResponseEntity.badRequest().body("User not found"); // Return 400 if the user does not exist
            }
            donhang.setNguoiDung(nguoiDung); // Associate the user with the order
            
            // Associate products with the order
            List<MatHang> matHangs = new ArrayList<>();
            matHangs.add(mathang); 
            donhang.setMatHangs(matHangs);

            // Update the product stock after an order is placed
            mathang.setSoluong(mathang.getSoluong() - quantity); 
            productService.save(mathang); // Persist the updated product information

            // Save and return the order to the database
            DonHang savedonhang = orderService.save(donhang);
            return ResponseEntity.ok(savedonhang); // Return the saved order with status OK
        } catch (Exception e) {
            // Handle any errors that occur during the order creation process
            return ResponseEntity.internalServerError().body("Error while creating order: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of all orders placed by a given user.
     * The result is fetched from cache or from the database if cache is empty.
     *
     * @param userId - The ID of the user whose orders are to be fetched.
     * @return - Response containing the list of orders or an error message if the retrieval fails.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserOrders(@PathVariable Long userId) {
        try {
            // Fetch the user's orders from the database or cache
            List<DonHang> userOrders = orderService.findByNguoiDungId(userId);
            return ResponseEntity.ok(userOrders); // Return the orders
        } catch (Exception e) {
            // Return an error message in case of any failure during the retrieval
            return ResponseEntity.internalServerError().body("Error fetching user orders: " + e.getMessage());
        }
    }

    /**
     * Searches for orders based on a keyword.
     * The result is cached for future search operations with the same keyword.
     *
     * @param keyword - The search keyword to find matching orders.
     * @return - Response containing the search results or an error message.
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchOrders(@RequestParam String keyword) {
        try {
            // Perform the search for orders that match the keyword
            List<DonHang> listdonHang = orderService.searchByKeyword(keyword);
            return ResponseEntity.ok(listdonHang); // Return the matching orders
        } catch (Exception e) {
            // Handle any errors that might occur during the search
            return ResponseEntity.internalServerError().build(); // Return a generic 500 error
        }
    }

    /**
     * Updates the details of an existing order.
     * The order is updated in both the database and the cache to reflect the changes.
     *
     * @param orderId - The ID of the order to be updated.
     * @param donHang - The updated order information.
     * @return - Response indicating whether the update was successful.
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateDonhang(@PathVariable Integer orderId, @RequestBody DonHang donHang) {    
        try {
            // Retrieve the order by its ID
            DonHang updateId = orderService.getById(orderId);
            if(updateId == null) {
                return ResponseEntity.notFound().build(); // If the order does not exist, return a 404 error
            }
            
            // Update the order's product name
            updateId.setTenmathang(updateId.getTenmathang());
            Boolean isUpdated = orderService.update(donHang); // Perform the update in the database
            if(isUpdated) {
                return ResponseEntity.ok(isUpdated); // Return success if update is successful
            } else {
                return ResponseEntity.badRequest().body("Update failed"); // Return an error message if the update fails
            }
        } catch (Exception e) {
            // Handle errors that may occur during the update process
            return ResponseEntity.internalServerError().body("Error updating order: " + e.getMessage());
        }
    }
}
