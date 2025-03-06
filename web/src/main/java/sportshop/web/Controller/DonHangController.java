package sportshop.web.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sportshop.web.DTO.ChiTietDonHangDTO;
import sportshop.web.DTO.OrderRequest;
import sportshop.web.Entity.BienTheMatHang;
import sportshop.web.Entity.DonHang;
import sportshop.web.Entity.GioHang;
import sportshop.web.Entity.HinhAnhMatHang;
import sportshop.web.Entity.MatHang;
import sportshop.web.Entity.NguoiDung;
import sportshop.web.Service.DonHangService;
import sportshop.web.Service.GioHangService;
import sportshop.web.Service.MatHangService;
import sportshop.web.Service.NguoiDungService;

@RestController
@RequestMapping("/api/v1/order")
public class DonHangController {

    @Autowired
    private DonHangService orderService;

    @Autowired
    private MatHangService productService;

    @Autowired
    private NguoiDungService nguoiDungService;
    
    @Autowired
    public GioHangService chitietservice;

    @GetMapping()
    public ResponseEntity<List<DonHang>> findAll(){
    	return ResponseEntity.ok(orderService.findAll());
    }
    /**
     * Creates a new order for a given product and quantity.
     * This method evicts the cache associated with a user’s orders to ensure data freshness.
     *
     * @param productId - The ID of the product being ordered.
     * @param quantity - The quantity of the product.
     * @param userId - The ID of the user placing the order.
     * @return - Response containing the created order details, or an error message if the process fails.
     */
    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest donHangRequest) {
        try {
            // Kiểm tra email của người dùng
            System.out.println("Đang tìm email: " + donHangRequest.getEmail());
            NguoiDung nguoiDung = nguoiDungService.findByEmail(donHangRequest.getEmail());

            // Nếu người dùng không tồn tại
            if (nguoiDung == null) {
                System.out.println("Người dùng không tồn tại: " + donHangRequest.getEmail());
                return ResponseEntity.badRequest().body("Người dùng không tồn tại");
            }
            System.out.println("Đã tìm thấy người dùng: " + nguoiDung.getEmail());

            // Tạo đơn hàng chính
            DonHang donHang = new DonHang();
            donHang.setNguoiDung(nguoiDung);

            // Tính tổng tiền và kiểm tra sản phẩm
            List<GioHang> chiTietDonHangs = new ArrayList<>();
            double tongTien = 0.0;
            int tongSoLuong = 0; // Đảm bảo cập nhật tổng số lượng sản phẩm trong đơn hàng
            
            // Kiểm tra từng chi tiết đơn hàng
            for (ChiTietDonHangDTO chiTiet : donHangRequest.getChiTietDonHangs()) {
                System.out.println("Đang tìm sản phẩm: " + chiTiet.getTenmathang());
                // Tìm sản phẩm
                MatHang matHang = productService.findByTenMatHang(chiTiet.getTenmathang());
                if (matHang == null) {
                    System.out.println("Sản phẩm không tồn tại: " + chiTiet.getTenmathang());
                    return ResponseEntity.notFound().build();
                }
                BienTheMatHang bienThe = matHang.getBienthes().isEmpty() ?
                		new BienTheMatHang() : matHang.getBienthes().get(0);
                
                HinhAnhMatHang hinhanh = matHang.getHinhanhs().isEmpty() ?
                		new HinhAnhMatHang() : matHang.getHinhanhs().get(0);
                
                if (bienThe.getNumber() < chiTiet.getSoluong()) {
                    System.out.println("Không đủ số lượng sản phẩm: " + chiTiet.getTenmathang());
                    return ResponseEntity.badRequest().body("Sản phẩm " + chiTiet.getTenmathang() + " không đủ số lượng");
                }
                
                
                // delete product
                bienThe.setNumber(bienThe.getNumber() - chiTiet.getSoluong());
                productService.save(matHang);

                // Tạo chi tiết đơn hàng
                GioHang chiTietDonHang = new GioHang();
                chiTietDonHang.setMatHang(matHang);
                chiTietDonHang.setSoLuong(chiTiet.getSoluong());
                chiTietDonHang.setMoney(bienThe.getNumber() * chiTiet.getSoluong());
                chiTietDonHang.setHinhanh(hinhanh.getImageUrl());
                chiTietDonHangs.add(chiTietDonHang);
                tongTien += chiTietDonHang.getMoney();
                tongSoLuong += chiTiet.getSoluong();
            }

            // Set thông tin tổng tiền và số lượng cho đơn hàng
            donHang.setMoney(tongTien);
            donHang.setSoluong(tongSoLuong); // Cập nhật số lượng đơn hàng tổng cộng
            
            // Lưu đơn hàng chính
            DonHang savedOrder = orderService.save(donHang);
            System.out.println("Lưu đơn hàng thành công: " + savedOrder.getId());

//            // Lưu chi tiết đơn hàng
//            for (GioHang chiTiet : chiTietDonHangs) {
//                chiTiet.setMatHang(ma); 
//                chitietservice.save(chiTiet);
//            }
            return ResponseEntity.ok(savedOrder); // Trả về đơn hàng vừa lưu

        } catch (Exception e) {
            // Ghi lại lỗi chi tiết nếu có
            System.out.println("Lỗi khi tạo đơn hàng: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Lỗi khi tạo đơn hàng: " + e.getMessage());
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

   
}
