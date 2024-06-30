package sportshop.web.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.BooleanBuilder;

import sportshop.web.Model.Log;
import sportshop.web.Model.MatHang;
import sportshop.web.Repository.LogRepository;
import sportshop.web.Repository.MatHangRepository;

@Service
public class MatHangService {
	private Environment env;
    @Autowired
    private MatHangRepository mathangRepository;

    private HttpServletRequest request;
    
    @Autowired
    private LogRepository logRepository;

    // Lấy tất cả sản phẩm
    public List<MatHang> findAll() {
        return mathangRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public MatHang getProduct(Integer productId) {
        return mathangRepository.findById(productId).orElseThrow(() -> new NoSuchElementException("Resource not found: " + productId));
    }

    // Tìm kiếm sản phẩm theo từ khóa
    public List<MatHang> searchByKeyword(String keyword) {
        return mathangRepository.searchByKeyword(keyword);
    }

    // Lấy sản phẩm theo ID với danh mục
    public MatHang getId(int id) {
        return mathangRepository.findByIdWithDanhMuc(id);
    }

    // Lưu sản phẩm mới
    public Boolean save(MatHang matHang) throws Exception {
    	matHang.setNgaythem(new Timestamp(System.currentTimeMillis()));
    
        MatHang savedMatHang = mathangRepository.save(matHang);
        if (savedMatHang != null) {
        	//saveImageForMatHang(savedMatHang, hinhanh);
            Log log = new Log();
            log.setLogString("Thêm mới mặt hàng id = " + savedMatHang.getId());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            logRepository.save(log);
            return true;
        }
        return false;
    }

    // Cập nhật sản phẩm
    public Boolean update(MatHang matHang) {
        MatHang updatedMatHang = mathangRepository.save(matHang);
        if (updatedMatHang != null) {
            Log log = new Log();
            log.setLogString("Cập nhật mặt hàng id = " + updatedMatHang.getId());
            log.setCreateTime(new Timestamp(System.currentTimeMillis()));
            logRepository.save(log);
            return true;
        }
        return false;
    }
    
 // Xóa sản phẩm
    public Boolean deleteProduct(Integer id) throws Exception {
        Optional<MatHang> matHangOptional = mathangRepository.findById(id);
        if (matHangOptional.isPresent()) {
            MatHang matHang = matHangOptional.get();
            mathangRepository.delete(matHang);
            
            // Verify if the deletion was successful
            boolean exists = mathangRepository.existsById(id);
            if (!exists) {
                Log log = new Log();
                log.setLogString("Xóa mặt hàng id = " + id);
                log.setCreateTime(new Timestamp(System.currentTimeMillis()));
                logRepository.save(log);
                return true;
            }
        }
        return false;
    }

    // Lấy sản phẩm theo ID danh mục
    public List<MatHang> findByCategoryId(Integer categoryId) {
        return mathangRepository.findByDanhMucId(categoryId);
    }

    // Lấy sản phẩm theo ID
    public MatHang getById(Integer id) {
        return mathangRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
    
    

    public List<MatHang> findProductsWtihSorting(String field){
    	return mathangRepository.findAll(Sort.by(Sort.Direction.ASC,field));
    }
    
   
//    public Page<MatHang> getMatHangByDanhMuc(int offset,int pagesize) {
//    	Page<MatHang> mathang =  mathangRepository.findAll(PageRequest.of(offset,pagesize));
//        return mathang;
//    }
//
//    public List<MatHang> filterAndSortProducts(Integer danhMucId, Integer hangSanXuatId, String priceRange, String sortOrder) {
//        List<MatHang> products = mathangRepository.findAll();
//        
//        // Filter by DanhMucId
//        if (danhMucId != null) {
//            products = products.stream()
//                    .filter(product -> product.getDanhMuc().getId().equals(danhMucId))
//                    .collect(Collectors.toList());
//        }
//        
//        // Filter by HangSanXuatId
//        if (hangSanXuatId != null) {
//            products = products.stream()
//                    .filter(product -> product.getHangSanXuat().getId().equals(hangSanXuatId))
//                    .collect(Collectors.toList());
//        }
//        
//        // Filter by PriceRange
//        if (priceRange != null) {
//            products = switch (priceRange) {
//                case "duoi-2-trieu" -> products.stream()
//                        .filter(product -> product.getDongia() < 2000000)
//                        .collect(Collectors.toList());
//                case "2-trieu-den-4-trieu" -> products.stream()
//                        .filter(product -> product.getDongia() >= 2000000 && product.getDongia() < 4000000)
//                        .collect(Collectors.toList());
//                case "4-trieu-den-6-trieu" -> products.stream()
//                        .filter(product -> product.getDongia() >= 4000000 && product.getDongia() < 6000000)
//                        .collect(Collectors.toList());
//                case "6-trieu-den-10-trieu" -> products.stream()
//                        .filter(product -> product.getDongia() >= 6000000 && product.getDongia() < 10000000)
//                        .collect(Collectors.toList());
//                case "tren-10-trieu" -> products.stream()
//                        .filter(product -> product.getDongia() >= 10000000)
//                        .collect(Collectors.toList());
//                default -> products;
//            };
//        }
//        
//        // Sort
//        if (sortOrder != null) {
//            if (sortOrder.equals("asc")) {
//                products.sort(Comparator.comparing(MatHang::getDongia));
//            } else if (sortOrder.equals("desc")) {
//                products.sort(Comparator.comparing(MatHang::getDongia).reversed());
//            }
//        }
//        
//        return products;
//    }

    public Page<MatHang> filterAndSortProducts(
            Integer danhMucId, 
            Integer hangSanXuatId, 
            String priceRange, 
            String sortOrder, 
            Integer page, 
            Integer size) {

        Specification<MatHang> spec = Specification.where(null);

        // Filter by DanhMucId
        if (danhMucId != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("danhMuc").get("id"), danhMucId)
            );
        }

        // Filter by HangSanXuatId
        if (hangSanXuatId != null) {
            spec = spec.and((root, query, cb) -> 
                cb.equal(root.get("hangSanXuat").get("id"), hangSanXuatId)
            );
        }

        // Filter by PriceRange
        if (priceRange != null) {
            spec = spec.and((root, query, cb) -> {
                switch (priceRange) {
                    case "duoi-2-trieu":
                        return cb.lessThan(root.get("dongia"), 2000000);
                    case "2-trieu-den-4-trieu":
                        return cb.between(root.get("dongia"), 2000000, 3999999);
                    case "4-trieu-den-6-trieu":
                        return cb.between(root.get("dongia"), 4000000, 5999999);
                    case "6-trieu-den-10-trieu":
                        return cb.between(root.get("dongia"), 6000000, 9999999);
                    case "tren-10-trieu":
                        return cb.greaterThanOrEqualTo(root.get("dongia"), 10000000);
                    default:
                        return null;
                }
            });
        }

        // Sort
        Sort sort = Sort.unsorted();
        if (sortOrder != null) {
            sort = sortOrder.equals("asc") ? Sort.by("dongia").ascending() : Sort.by("dongia").descending();
        }

        // Create PageRequest with sorting
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // Use the specification and PageRequest to get a Page of MatHang
        return mathangRepository.findAll(spec, pageRequest);
    }


//	// Tim kiem san pham theo keyword, sap xep, phan trang, loc theo muc gia, lay 12
//	// san pham moi trang
//	@Override
//	public Page<SanPham> getSanPhamByTenSanPham(SearchSanPhamObject object, int page, int resultPerPage) {
//		BooleanBuilder builder = new BooleanBuilder();
////		int resultPerPage = 12;
//		String[] keywords = object.getKeyword();
//		String sort = object.getSort();
//		String price = object.getDonGia();
//		String brand = object.getBrand();
//		String manufactor = object.getManufactor();
//		// Keyword
//		builder.and(QSanPham.sanPham.tenSanPham.like("%" + keywords[0] + "%"));
//		if (keywords.length > 1) {
//			for (int i = 1; i < keywords.length; i++) {
//				builder.and(QSanPham.sanPham.tenSanPham.like("%" + keywords[i] + "%"));
//			}
//		}
//		// Muc gia
//		switch (price) {
//		case "duoi-2-trieu":
//			builder.and(QSanPham.sanPham.donGia.lt(2000000));
//			break;
//
//		case "2-trieu-den-4-trieu":
//			builder.and(QSanPham.sanPham.donGia.between(2000000, 4000000));
//			break;
//
//		case "4-trieu-den-6-trieu":
//			builder.and(QSanPham.sanPham.donGia.between(4000000, 6000000));
//			break;
//
//		case "6-trieu-den-10-trieu":
//			builder.and(QSanPham.sanPham.donGia.between(6000000, 10000000));
//			break;
//
//		case "tren-10-trieu":
//			builder.and(QSanPham.sanPham.donGia.gt(10000000));
//			break;
//
//		default:
//			break;
//		}
//
//		// Danh muc va hang san xuat
//		if (brand.length()>1) {
//			builder.and(QSanPham.sanPham.danhMuc.tenDanhMuc.eq(brand));
//		}
//		if (manufactor.length()>1) {
//			builder.and(QSanPham.sanPham.hangSanXuat.tenHangSanXuat.eq(manufactor));
//		}
//
//		// Sap xep
//		if (sort.equals("newest")) {
//			return sanPhamRepo.findAll(builder, PageRequest.of(page - 1, resultPerPage, Sort.Direction.DESC, "id"));
//		} else if (sort.equals("priceAsc")) {
//			return sanPhamRepo.findAll(builder, PageRequest.of(page - 1, resultPerPage, Sort.Direction.ASC, "donGia"));
//		} else if (sort.equals("priceDes")) {
//			return sanPhamRepo.findAll(builder, PageRequest.of(page - 1, resultPerPage, Sort.Direction.DESC, "donGia"));
//		}
//		return sanPhamRepo.findAll(builder, PageRequest.of(page - 1, resultPerPage));
//	}
//
//	public List<SanPham> getAllSanPhamByList(Set<Long> idList) {
//		return sanPhamRepo.findByIdIn(idList);
//	}
//
//	@Override
//	public Iterable<MatHang> getSanPhamByTenDanhMuc(String brand) {
//		BooleanBuilder builder = new BooleanBuilder();
//		builder.and(QSanPham.sanPham.danhMuc.tenDanhMuc.eq(brand));
//		return sanPhamRepo.findAll(builder);
//	}
//	
//	@Override
//	public Page<MatHang> getSanPhamByBrand(SearchSanPhamObject object, int page, int resultPerPage) {
//		BooleanBuilder builder = new BooleanBuilder();
//		String price = object.getDonGia();
//		String brand = object.getBrand();
//		String manufactor = object.getManufactor();
//		String os = object.getOs();
//		String ram = object.getRam();
//		String pin = object.getPin();
//		// Muc gia
//		switch (price) {
//		case "duoi-2-trieu":
//			builder.and(QSanPham.sanPham.donGia.lt(2000000));
//			break;
//
//		case "2-trieu-den-4-trieu":
//			builder.and(QSanPham.sanPham.donGia.between(2000000, 4000000));
//			break;
//
//		case "4-trieu-den-6-trieu":
//			builder.and(QSanPham.sanPham.donGia.between(4000000, 6000000));
//			break;
//
//		case "6-trieu-den-10-trieu":
//			builder.and(QSanPham.sanPham.donGia.between(6000000, 10000000));
//			break;
//
//		case "tren-10-trieu":
//			builder.and(QSanPham.sanPham.donGia.gt(10000000));
//			break;
//
//		default:
//			break;
//		}
//		// Danh muc va hang san xuat
//		if (brand.length()>1) {
//			builder.and(QSanPham.sanPham.danhMuc.tenDanhMuc.eq(brand));
//		}
//		if (manufactor.length()>1) {
//			builder.and(QSanPham.sanPham.hangSanXuat.tenHangSanXuat.eq(manufactor));
//		}
//		if (os.length()>1) {
//			builder.and(QSanPham.sanPham.heDieuHanh.like("%"+os+"%"));
//		}
//		if (ram.length()>1) {
//			builder.and(QSanPham.sanPham.ram.like("%"+ram+"%"));
//		}
//		if (pin.length()>1) {
//			builder.and(QSanPham.sanPham.dungLuongPin.eq(pin));
//		}
//
//		return sanPhamRepo.findAll(builder, PageRequest.of(page - 1, resultPerPage));
//	}

}
