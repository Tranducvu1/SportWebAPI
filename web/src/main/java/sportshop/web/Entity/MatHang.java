package sportshop.web.Entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "mathang")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"danhMuc", "hangSanXuat"}) 
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}) 
public class MatHang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "mamathang", nullable = false, length = 50)
    private String mamathang;

    @Column(name = "tenmathang", nullable = false, length = 100)
    private String tenmathang;


    @Column(name = "hinhanh", nullable = true)
    private String hinhanh;

    @Column(name = "dongia", nullable = false)
    private int dongia;

    @Column(name = "danhgia")
    private int danhgia;

    @Column(name = "soluong", nullable = false)
    private int soluong;

    @Column(name = "size", length = 20)
    private String size;

    @Column(name = "mota", nullable = false, columnDefinition = "TEXT")
    private String mota;

    @Column(name = "giamgia", nullable = false)
    private int giamgia;

    @Column(name = "gioi_tinh", nullable = false)
    private String gender;

    @CreationTimestamp
    @Column(name = "ngaythem", updatable = false)
    private String ngaythem;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "danhmuc_id", nullable = false)
    @JsonBackReference 
    private DanhMuc danhMuc;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "ma_hang_sx", nullable = false)
    private HangSanXuat hangSanXuat;
}
