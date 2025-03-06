package sportshop.web.DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinhLuanDTO  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int matHangId;
    private int userId;
    private String comment;
    private LocalDateTime createdAt;
}
