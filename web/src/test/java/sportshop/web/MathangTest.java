//package sportshop.web;
//
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import sportshop.web.Entity.DanhMuc;
//import sportshop.web.Entity.MatHang;
//import sportshop.web.Repository.MatHangRepository;
//import sportshop.web.Repository.DanhMucRepository;
//
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//public class MathangTest {
//
//    @Autowired
//    private MatHangRepository matHangRepository;
//
//    @Autowired
//    private DanhMucRepository danhMucRepository;
//
//    private DanhMuc danhMuc;
//
//    @BeforeEach
//    void setup() {
//        System.out.println("Thiết lập dữ liệu trước mỗi test...");
//        danhMuc = new DanhMuc();
//        danhMuc.setId(1);
//        danhMuc = danhMucRepository.save(danhMuc);
//        System.out.println("Danh mục mẫu đã được tạo: " + danhMuc);
//    }
//
//    @AfterEach
//    void cleanup() {
//        System.out.println("Dọn dẹp dữ liệu sau mỗi test...");
//        matHangRepository.deleteAll();
//        danhMucRepository.deleteAll();
//        System.out.println("Dữ liệu đã được xóa sạch.");
//    }
//
//    @Test
//    public void testSaveMatHang() {
//        System.out.println("Bắt đầu testSaveMatHang...");
//        MatHang matHang = new MatHang();
//        matHang.setMamathang("MH123");
//        matHang.setTenmathang("Áo thể thao");
//        matHang.setDongia(50000);
//        matHang.setSoluong(100);
//        matHang.setMota("Áo thể thao thoáng mát");
//        matHang.setHinhanh("image_url");
//        matHang.setGiamgia(10);
//        matHang.setMota("Hello");
//        matHang.setGender("Nam");
//        matHang.setMaphanloai("MH370");
//        matHang.setDanhMuc(danhMuc);
//
//        MatHang savedMatHang = matHangRepository.save(matHang);
//        System.out.println("Mặt hàng đã lưu: " + savedMatHang);
//
//        assertNotNull(savedMatHang);
//        assertNotNull(savedMatHang.getId());
//        assertEquals("Áo thể thao", savedMatHang.getTenmathang());
//        System.out.println("testSaveMatHang hoàn thành thành công!");
//    }
//
//    @Test
//    public void testFindById() {
//        System.out.println("Bắt đầu testFindById...");
//        MatHang matHang = new MatHang();
//        matHang.setMamathang("MH124");
//        matHang.setTenmathang("Quần thể thao");
//        matHang.setDongia(60000);
//        matHang.setDanhMuc(danhMuc);
//        matHang.setGender("Nam");
//        matHang.setMaphanloai("IYUIYUOI");
//        matHang.setMota("Hello");
//        matHang.setHinhanh("image_url");
//        matHang = matHangRepository.save(matHang);
//
//        System.out.println("Mặt hàng vừa lưu: " + matHang);
//        Optional<MatHang> matHangOptional = matHangRepository.findById(matHang.getId());
//        System.out.println("Kết quả tìm kiếm: " + matHangOptional);
//
//        assertTrue(matHangOptional.isPresent());
//        assertEquals("Quần thể thao", matHangOptional.get().getTenmathang());
//        System.out.println("testFindById hoàn thành thành công!");
//    }
//
//    @Test
//    public void testUpdateMatHang() {
//        System.out.println("Bắt đầu testUpdateMatHang...");
//        MatHang matHang = new MatHang();
//        matHang.setMamathang("MH125");
//        matHang.setTenmathang("Giày thể thao");
//        matHang.setDongia(70000);
//        matHang.setDanhMuc(danhMuc);
//        matHang.setMaphanloai("IYUIYUOI");
//        matHang.setMota("Hello");
//        matHang.setGender("Nam");
//        matHang.setHinhanh("image_url");
//        matHang = matHangRepository.save(matHang);
//
//        System.out.println("Mặt hàng trước khi cập nhật: " + matHang);
//        matHang.setTenmathang("Giày thể thao nam");
//        matHang.setDongia(75000);
//        MatHang updatedMatHang = matHangRepository.save(matHang);
//
//        System.out.println("Mặt hàng sau khi cập nhật: " + updatedMatHang);
//        assertEquals("Giày thể thao nam", updatedMatHang.getTenmathang());
//        assertEquals(75000, updatedMatHang.getDongia());
//        System.out.println("testUpdateMatHang hoàn thành thành công!");
//    }
//
//    @Test
//    public void testDeleteMatHang() {
//        System.out.println("Bắt đầu testDeleteMatHang...");
//        MatHang matHang = new MatHang();
//        matHang.setMamathang("MH126");
//        matHang.setTenmathang("Áo khoác");
//        matHang.setDongia(80000);
//        matHang.setDanhMuc(danhMuc);
//        matHang.setMota("Hello");
//        matHang.setGender("Nam");
//        matHang.setHinhanh("image_url");
//        matHang = matHangRepository.save(matHang);
//
//        System.out.println("Mặt hàng chuẩn bị xóa: " + matHang);
//        matHangRepository.deleteById(matHang.getId());
//
//        Optional<MatHang> matHangOptional = matHangRepository.findById(matHang.getId());
//        System.out.println("Kết quả sau khi xóa: " + matHangOptional);
//
//        assertFalse(matHangOptional.isPresent());
//        System.out.println("testDeleteMatHang hoàn thành thành công!");
//    }
//
//    @Test
//    public void testFindAll() {
//        System.out.println("Bắt đầu testFindAll...");
//        MatHang matHang1 = new MatHang();
//        matHang1.setMamathang("MH127");
//        matHang1.setTenmathang("Túi xách");
//        matHang1.setDongia(90000);
//        matHang1.setDanhMuc(danhMuc);
//        matHang1.setGender("Nam");
//        matHang1.setMota("Hello");
//        matHang1.setMaphanloai("IYUIYUOI");
//        MatHang matHang2 = new MatHang();
//        matHang2.setMamathang("MH128");
//        matHang2.setTenmathang("Mũ thời trang");
//        matHang2.setDongia(30000);
//        matHang2.setDanhMuc(danhMuc);
//        matHang2.setMota("Hello");
//        matHang2.setGender("Nữ");
//        matHang2.setMaphanloai("IYUIYUOI");
//
//        matHangRepository.save(matHang1);
//        matHangRepository.save(matHang2);
//
//        Iterable<MatHang> matHangs = matHangRepository.findAll();
//        System.out.println("Danh sách tất cả mặt hàng: ");
//        matHangs.forEach(System.out::println);
//
//        assertNotNull(matHangs);
//        assertTrue(matHangs.iterator().hasNext());
//        System.out.println("testFindAll hoàn thành thành công!");
//    }
//}
