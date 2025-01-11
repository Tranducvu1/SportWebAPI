package sportshop.web.Service.ipml;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.commons.lang3.time.StopWatch;
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
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import sportshop.web.DTO.MatHangDto;
import sportshop.web.Entity.MatHang;
import sportshop.web.Repository.MatHangRepository;
import sportshop.web.Service.LogService;
import sportshop.web.Service.MatHangService;
import sportshop.web.constants.PriceRangeConstants;

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

    @Override
    public List<MatHang> searchByKeyword(String keyword) {
        log.debug("Searching for products with keyword: {}", keyword);
        return mathangRepository.findByTenmathangContainingIgnoreCase(keyword);
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
            matHang.setNgaythem(new Timestamp(System.currentTimeMillis()));
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
    public Page<MatHangDto> filterAndSortProducts(Integer danhMucId, Integer hangSanXuatId, String priceRange, String sortOrder, Integer page, Integer size) {
        log.debug("Filtering products with params: category={}, manufacturer={}, priceRange={}", danhMucId, hangSanXuatId, priceRange);
        validatePaginationParams(page, size);

        StopWatch watch = new StopWatch();
        watch.start();
        try {
            Specification<MatHang> spec = createSpecification(danhMucId, hangSanXuatId, priceRange);
            Sort sort = createSortOrder(sortOrder);
            PageRequest pageRequest = PageRequest.of(page, size, sort);
            Page<MatHang> result = mathangRepository.findAll(spec, pageRequest);
            Page<MatHangDto> dtoPage = result.map(this::convertToDto);
            watch.stop();
            log.debug("Filtering products completed in {} ms", watch.getTime());
            return dtoPage;
        } catch (Exception e) {
            log.error("Error filtering products: ", e);
            throw e;
        }
    }

    private Specification<MatHang> createSpecification(Integer danhMucId, Integer hangSanXuatId, String priceRange) {
        return (root, query, cb) -> {
            if (query.getResultType().equals(MatHang.class)) {
                root.fetch("danhMuc", JoinType.LEFT);
                root.fetch("hangSanXuat", JoinType.LEFT);
            }
            List<Predicate> predicates = new ArrayList<>();

            if (danhMucId != null) {
                log.debug("Filtering by category id: {}", danhMucId);
                predicates.add(cb.equal(root.get("danhMuc").get("id"), danhMucId));
            }

            if (hangSanXuatId != null) {
                log.debug("Filtering by manufacturer id: {}", hangSanXuatId);
                predicates.add(cb.equal(root.get("hangSanXuat").get("id"), hangSanXuatId));
            }

            if (priceRange != null) {
                switch (priceRange) {
                    case PriceRangeConstants.DUOI_2_TRIEU:
                        predicates.add(cb.lessThan(root.get("dongia"), 2000000));
                        break;
                    case PriceRangeConstants.TU_2_DEN_4_TRIEU:
                        predicates.add(cb.between(root.get("dongia"), 2000000, 3999999));
                        break;
                    case PriceRangeConstants.TU_4_DEN_6_TRIEU:
                        predicates.add(cb.between(root.get("dongia"), 4000000, 5999999));
                        break;
                    case PriceRangeConstants.TU_6_DEN_10_TRIEU:
                        predicates.add(cb.between(root.get("dongia"), 6000000, 9999999));
                        break;
                    case PriceRangeConstants.TREN_10_TRIEU:
                        predicates.add(cb.greaterThanOrEqualTo(root.get("dongia"), 10000000));
                        break;
                }
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void validatePaginationParams(Integer page, Integer size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size <= 0 || size > PriceRangeConstants.MAX_PAGE_SIZE) {
            throw new IllegalArgumentException(String.format("Page size must be between 1 and %d", PriceRangeConstants.MAX_PAGE_SIZE));
        }
    }

    private Sort createSortOrder(String sortOrder) {
        return sortOrder != null && sortOrder.equals("asc") ? Sort.by("dongia").ascending() : Sort.by("dongia").descending();
    }

    public MatHangDto convertToDto(MatHang matHang) {
        return MatHangDto.builder()
                .id(matHang.getId())
                .mamathang(matHang.getMamathang())
                .tenmathang(matHang.getTenmathang())
                .maphanloai(matHang.getMaphanloai())
                .hinhanh(matHang.getHinhanh())
                .dongia(matHang.getDongia())
                .danhgia(matHang.getDanhgia())
                .soluong(matHang.getSoluong())
                .size(matHang.getSize())
                .mota(matHang.getMota())
                .giamgia(matHang.getGiamgia())
                .gender(matHang.getGender())
                .ngaythem(matHang.getNgaythem())
                .danhMucId(matHang.getDanhMuc() != null ? matHang.getDanhMuc().getId() : null)
                .nhaSXId(matHang.getHangSanXuat() != null ? matHang.getHangSanXuat().getId() : null)
                .danhMucTen(matHang.getDanhMuc() != null ? matHang.getDanhMuc().getTendanhmuc() : null)
                .hangSanXuatTen(matHang.getHangSanXuat() != null ? matHang.getHangSanXuat().getTenhang() : null)
                .build();
    }


 
}
