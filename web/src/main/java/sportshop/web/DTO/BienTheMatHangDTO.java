package sportshop.web.DTO;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BienTheMatHangDTO  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int matHangId;
    private String size;
    private String color;
    private int number;
    private int price;
}
