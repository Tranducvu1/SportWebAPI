package sportshop.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import sportshop.web.DTO.MatHangDto;
import sportshop.web.Service.DanhMucService;


@Component
public class MatHangDtoValidator implements Validator {

    @Autowired
    private DanhMucService danhMucService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MatHangDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MatHangDto mh = (MatHangDto) target;
        ValidationUtils.rejectIfEmpty(errors, "tenmathang", "error.tenmathang", "Tên sản phẩm không được trống");
        ValidationUtils.rejectIfEmpty(errors, "dongia", "error.dongia", "Đơn giá không được trống");
        ValidationUtils.rejectIfEmpty(errors, "soluong", "error.soluong", "Số lượng không được trống");
        ValidationUtils.rejectIfEmpty(errors, "mota", "error.mota", "Mô tả không được trống");
        if (mh.getDongia() != null && mh.getDongia() < 0) {
            errors.rejectValue("dongia", "error.dongia", "Đơn giá không được âm");
        }  
    }
}
