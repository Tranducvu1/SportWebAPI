package sportshop.web.Model;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vaitro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaiTro {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(name = "ten_vai_tro")
    private String tenVaiTro;

    @JsonIgnore
    @ManyToMany(mappedBy = "vaiTro")
    private Set<User> nguoiDung;

    public VaiTro(String tenVaiTro) {
        this.tenVaiTro = tenVaiTro;
    }
}