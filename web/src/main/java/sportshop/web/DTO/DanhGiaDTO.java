package sportshop.web.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhGiaDTO {
    private int id;
    private int matHangId;
    private int userId;
    private int rating;
    private String review;
    private LocalDateTime createdAt;
}
