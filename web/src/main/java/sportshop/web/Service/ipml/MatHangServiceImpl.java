package sportshop.web.Service.ipml;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import sportshop.web.DTO.BienTheMatHangDTO;
import sportshop.web.DTO.BinhLuanDTO;
import sportshop.web.DTO.HinhAnhMatHangDTO;
import sportshop.web.DTO.MatHangDto;
import sportshop.web.Entity.BienTheMatHang;
import sportshop.web.Entity.MatHang;
import sportshop.web.Repository.MatHangRepository;
import sportshop.web.Service.LogService;
import sportshop.web.Service.MatHangService;
import sportshop.web.constants.PriceRangeConstants;
import org.springframework.data.domain.Pageable;

@Service
@Slf4j
public class MatHangServiceImpl implements MatHangService {

    private final MatHangRepository mathangRepository;
    @Lazy
    private final LogService logService;
    
    @Autowired
    public MatHangServiceImpl(MatHangRepository mathangRepository,
                              LogService logService,
                              EntityManager entityManager) {
        this.mathangRepository = mathangRepository;
        this.logService = logService;
    }

    @Cacheable(value = "matHangList")
    @Transactional
    @Override
    public List<MatHang> findAll() {
        log.debug("Fetching all products");
        return mathangRepository.findAll();
    }

    @Cacheable(value = "matHang", key = "#productId")
    @Override
    public MatHang getProduct(Integer productId) {
        log.debug("Fetching product with id: {}", productId);
        return mathangRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + productId));
    }

    public MatHang findByTenMatHang(String tenMatHang) {
        return mathangRepository.findByTenmathang(tenMatHang)
            .orElse(null);
    }
    
    @Override
    public List<MatHang> searchByKeyword(String keyword) {
        log.debug("Searching for products with keyword: {}", keyword);
        return mathangRepository.findByTenmathangContainingIgnoreCase(keyword);
    }
    
    public MatHang findByNormalizedProductName(String productName) {
        return mathangRepository.findByNormalizedProductName(productName);
    }

    @Cacheable(value = "matHang", key = "#id")
    @Override
    public MatHang getId(int id) {
        return mathangRepository.findByIdWithDanhMuc(id);
    }

    @CacheEvict(value = {"matHangList", "matHang"}, allEntries = true)
    @Override
    public Boolean save(MatHang matHang) throws Exception {
        try {
            log.debug("Saving new product: {}", matHang);
            MatHang savedMatHang = mathangRepository.save(matHang);
            if (savedMatHang != null) {
                logService.saveLog("Thêm mới mặt hàng id = " + savedMatHang.getId());
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("Error saving product: {}", e.getMessage());
            throw new Exception("Error saving product: " + e.getMessage());
        }
    }

    @CacheEvict(value = {"matHangList", "matHang"}, allEntries = true)
    @Override
    @Transactional
    public Boolean update(MatHang matHang) {
        try {
            log.debug("Updating product: {}", matHang);
            MatHang updatedMatHang = mathangRepository.save(matHang);
            if (updatedMatHang != null) {
                logService.saveLog("Cập nhật mặt hàng id = " + updatedMatHang.getId());
                return true;
            }
        } catch (Exception e) {
            log.error("Error updating product: {}", e.getMessage());
        }
        return false;
    }

    @CacheEvict(value = {"matHangList", "matHang"}, allEntries = true)
    @Override
    public Boolean deleteProduct(Integer id) throws Exception {
        try {
            Optional<MatHang> matHangOptional = mathangRepository.findById(id);
            if (matHangOptional.isPresent()) {
                MatHang matHang = matHangOptional.get();
                mathangRepository.delete(matHang);
                boolean exists = mathangRepository.existsById(id);
                if (!exists) {
                    logService.saveLog("Xóa mặt hàng id = " + id);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("Error deleting product: {}", e.getMessage());
            throw new Exception("Error deleting product: " + e.getMessage());
        }
    }

    @Cacheable(value = "matHangList", key = "#categoryId")
    @Override
    public List<MatHang> findByCategoryId(Integer categoryId) {
        log.debug("Fetching products by category id: {}", categoryId);
        return mathangRepository.findByDanhMucId(categoryId);
    }

    @Override
    public MatHang getById(Integer id) {
        log.debug("Fetching products by category id: {}", id);
        return mathangRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    @Override
    public List<MatHang> findProductsWtihSorting(String field) {
        log.debug("Fetching all products sorted by: {}", field);
        return mathangRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    @Cacheable(value = "filterProducts",
            key = "{#danhMucId, #hangSanXuatId, #priceRange, #sortOrder, #page, #size}")
 @Override
 public Page<MatHangDto> filterAndSortProducts(Integer danhMucId, Integer hangSanXuatId, 
                                              String priceRange, String sortOrder, 
                                              Integer page, Integer size) {
     log.debug("Filtering products with pagination: page={}, size={}", page, size);
     validatePaginationParams(page, size);

     
     Specification<MatHang> filterSpec = createFilterSpecification(danhMucId, hangSanXuatId, priceRange);

    
     Pageable pageable = PageRequest.of(page, size);
     
     
     Page<MatHang> matHangs = mathangRepository.findAll(filterSpec, pageable);
     
   
     matHangs.forEach(matHang -> sortBienThes(matHang, sortOrder));
     
     return matHangs.map(this::convertToDto);
 }


 private void sortBienThes(MatHang matHang, String sortOrder) {
     if (matHang.getBienthes() != null && !matHang.getBienthes().isEmpty()) {
         if ("desc".equalsIgnoreCase(sortOrder)) {
             matHang.getBienthes().sort(Comparator.comparing(BienTheMatHang::getPrice).reversed());
         } else {
             matHang.getBienthes().sort(Comparator.comparing(BienTheMatHang::getPrice));
         }
     }
 }

    public static Specification<MatHang> createFilterSpecification(Integer danhMucId, Integer hangSanXuatId, String priceRange) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (priceRange != null) {
         

                switch (priceRange) {
                    case "DUOI_2_TRIEU":
                        predicates.add(cb.lessThan(root.get("price"), 200000));
                        break;
                    case "TU_2_DEN_4_TRIEU":
                        predicates.add(cb.between(root.get("price"), 200000, 399999));
                        break;
                    case "TU_4_DEN_6_TRIEU":
                        predicates.add(cb.between(root.get("price"), 400000, 599999));
                        break;
                    case "TU_6_DEN_10_TRIEU":
                        predicates.add(cb.between(root.get("price"), 600000, 999999));
                        break;
                    case "TREN_10_TRIEU":
                        predicates.add(cb.greaterThanOrEqualTo(root.get("price"), 10000000));
                        break;
                }

                query.distinct(true); // Tránh lặp dữ liệu khi JOIN
            }


            // In danh sách predicates (tùy chọn)
            System.out.println("Applied predicates: " + predicates);
            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
    
   




    private void validatePaginationParams(Integer page, Integer size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size <= 0 || size > PriceRangeConstants.MAX_PAGE_SIZE) {
            throw new IllegalArgumentException(
                String.format("Page size must be between 1 and %d", PriceRangeConstants.MAX_PAGE_SIZE));
        }
    }

    public MatHangDto convertToDto(MatHang matHang) {
        if (matHang == null) {
            return null;
        }

        MatHangDto.MatHangDtoBuilder builder = MatHangDto.builder()
                .id(matHang.getId())
                .mamathang(matHang.getMamathang())
                .tenmathang(matHang.getTenmathang())
                .mota(matHang.getMota())
                .giamgia(matHang.getGiamgia())
                .gender(matHang.getGender())
                .nhaSXId(matHang.getHangSanXuat().getId())
                .ngaythem(matHang.getNgaythem());

        // Convert bienthes
        if (matHang.getBienthes() != null) {
        	
            List<BienTheMatHangDTO> bienTheDTOs = matHang.getBienthes().stream()
                    .map(bienThe -> new BienTheMatHangDTO(
                            bienThe.getId(),
                            matHang.getId(),
                            bienThe.getSize(),
                            bienThe.getColor(),
                            bienThe.getNumber(),
                            bienThe.getPrice()
                            
                    ))
                    .collect(Collectors.toList());
            builder.bienthes(bienTheDTOs);
            System.out.println(bienTheDTOs);
        }

        // Convert hinhanhs 
        if (matHang.getHinhanhs() != null) {
            List<HinhAnhMatHangDTO> hinhAnhDTOs = matHang.getHinhanhs().stream()
                    .map(hinhAnh -> new HinhAnhMatHangDTO(
                            hinhAnh.getId(),
                            matHang.getId(),
                            hinhAnh.getImageUrl()
                    ))
                    .collect(Collectors.toList());
            builder.hinhanhs(hinhAnhDTOs);
        }

     // Convert binhluans if needed
        if (matHang.getBinhluans() != null) {
            List<BinhLuanDTO> binhLuanDTOs = matHang.getBinhluans().stream()
                    .map(binhluan -> new BinhLuanDTO(
                            binhluan.getId(),
                            binhluan.getUserId(),
                            binhluan.getUserId(),
                            binhluan.getComment(),
                            binhluan.getCreatedAt()
                          ))
                    .collect(Collectors.toList());
            builder.binhluans(binhLuanDTOs);
        } 


        // Set hang san xuat info
        if (matHang.getHangSanXuat() != null) {
            builder.nhaSXId(matHang.getHangSanXuat().getId())
                  .hangSanXuatTen(matHang.getHangSanXuat().getTenhang());
        }

        return builder.build();
    }

	

	
}