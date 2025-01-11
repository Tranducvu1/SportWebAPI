//package sportshop.web;
//
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//
//import lombok.extern.slf4j.Slf4j;
//import sportshop.web.Controller.DonHangController;
//import sportshop.web.Entity.DonHang;
//import sportshop.web.Entity.MatHang;
//import sportshop.web.Entity.NguoiDung;
//import sportshop.web.Repository.DonHangRepository;
//import sportshop.web.Repository.MatHangRepository;
//import sportshop.web.Repository.NguoiDungRepository;
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.List;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//@Slf4j
//public class DonHangControllerTest {
//
//    @Autowired
//    private DonHangController donHangController;
//
//    @Autowired
//    private MatHangRepository matHangRepository;
//
//    @Autowired
//    private NguoiDungRepository nguoiDungRepository;
//
//    @Autowired
//    private DonHangRepository donHangRepository;
//
//    private MatHang matHang;
//    private NguoiDung nguoiDung;
//
//    @BeforeEach
//    void setup() {
//        log.info("Setting up test data...");
//        matHang = new MatHang();
//        matHang.setMamathang("MH129");
//        matHang.setTenmathang("Giày bóng đá");
//        matHang.setDongia(150000);
//        matHang.setSoluong(50);
//        matHang.setGender("Nam");
//        matHang = matHangRepository.save(matHang);
//
//        nguoiDung = new NguoiDung();
//        nguoiDung.setHoten("John Doe");
//        nguoiDung = nguoiDungRepository.save(nguoiDung);
//        log.info("Test data setup complete.");
//    }
//
//    @AfterEach
//    void cleanup() {
//        log.info("Cleaning up test data...");
//        donHangRepository.deleteAll();
//        matHangRepository.deleteAll();
//        nguoiDungRepository.deleteAll();
//        log.info("Cleanup complete.");
//    }
//
//    @Test
//    void testCreateOrder() {
//        log.info("Testing createOrder...");
//        ResponseEntity<?> response = donHangController.createOrder(matHang.getId(), 2, nguoiDung.getId());
//        log.info("Response: {}", response);
//
//        assertEquals(200, response.getStatusCodeValue());
//
//        DonHang donHang = (DonHang) response.getBody();
//        assertNotNull(donHang);
//        log.info("Order created successfully: {}", donHang);
//        assertEquals("Giày bóng đá", donHang.getTenmathang());
//        assertEquals(2, donHang.getSoluong());
//        assertEquals(BigDecimal.valueOf(300000), donHang.getMoney());
//    }
//
//    @Test
//    void testCreateOrderInsufficientStock() {
//        log.info("Testing createOrder with insufficient stock...");
//        ResponseEntity<?> response = donHangController.createOrder(matHang.getId(), 100, nguoiDung.getId());
//        log.info("Response: {}", response);
//
//        assertEquals(400, response.getStatusCodeValue());
//        assertEquals("Không hợp lệ", response.getBody());
//    }
//
//    @Test
//    void testGetUserOrders() {
//        log.info("Testing getUserOrders...");
//        DonHang donHang = new DonHang();
//        donHang.setTenmathang("Giày bóng đá");
//        donHang.setSoluong(2);
//        
//        donHang.setNgaydat(new Timestamp(System.currentTimeMillis()));
//        donHang.setNguoiDung(nguoiDung);
//        donHangRepository.save(donHang);
//        log.info("Created test order: {}", donHang);
//
//        ResponseEntity<?> response = donHangController.getUserOrders(nguoiDung.getId());
//        log.info("Response: {}", response);
//
//        assertEquals(200, response.getStatusCodeValue());
//        @SuppressWarnings("unchecked")
//        List<DonHang> orders = (List<DonHang>) response.getBody();
//        assertNotNull(orders);
//        log.info("User orders retrieved: {}", orders);
//        assertEquals(1, orders.size());
//    }
//
//    @Test
//    void testSearchOrders() {
//        log.info("Testing searchOrders...");
//        DonHang donHang = new DonHang();
//        donHang.setTenmathang("Áo bóng rổ");
//        donHang.setSoluong(3);
//        donHang.setNguoiDung(nguoiDung);
//        donHangRepository.save(donHang);
//        log.info("Created test order: {}", donHang);
//
//        ResponseEntity<?> response = donHangController.searchOrders("bóng");
//        log.info("Response: {}", response);
//
//        assertEquals(200, response.getStatusCodeValue());
//        @SuppressWarnings("unchecked")
//        List<DonHang> orders = (List<DonHang>) response.getBody();
//        assertNotNull(orders);
//        log.info("Search results: {}", orders);
//        assertFalse(orders.isEmpty());
//    }
//
//    @Test
//    void testUpdateDonHang() {
//        log.info("Testing updateDonHang...");
//        DonHang donHang = new DonHang();
//        donHang.setTenmathang("Bóng rổ");
//        donHang.setSoluong(2);
//        donHang.setNguoiDung(nguoiDung);
//        donHang = donHangRepository.save(donHang);
//        log.info("Created test order: {}", donHang);
//
//        DonHang updatedDonHang = new DonHang();
//        updatedDonHang.setTenmathang("Bóng đá");
//        updatedDonHang.setSoluong(3);
//
//        ResponseEntity<?> response = donHangController.updateDonhang(donHang.getId(), updatedDonHang);
//        log.info("Response: {}", response);
//
//        assertEquals(200, response.getStatusCodeValue());
//        Optional<DonHang> optionalDonHang = donHangRepository.findById(donHang.getId());
//        assertTrue(optionalDonHang.isPresent());
//        log.info("Order updated successfully: {}", optionalDonHang.get());
//        assertEquals("Bóng đá", optionalDonHang.get().getTenmathang());
//    }
//}
