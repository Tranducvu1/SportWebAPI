//package sportshop.web.Model;
//
//
//import java.sql.Timestamp;
//import java.util.HashSet;
//import java.util.Set;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinTable;
//import jakarta.persistence.ManyToMany;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Data
//@Table(name = "account")
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
//public class Account {
//	  @Id
//	  @GeneratedValue(strategy = GenerationType.IDENTITY)
//	  private Long id;
//
//	  @NotBlank
//	  @Size(max = 20)
//	  private String username;
//
//	  @NotBlank
//	  @Size(max = 50)
//	  @Email
//	  private String email;
//
//	  @NotBlank
//	  @Size(max = 120)
//	  private String password;
//	  
//
//	  @ManyToMany(fetch = FetchType.LAZY)
//	  @JoinTable(name = "user_roles", 
//	             joinColumns = @JoinColumn(name = "user_id"),
//	             inverseJoinColumns = @JoinColumn(name = "role_id"))
//	  private Set<Role> roles = new HashSet<>();
//	  
//	  @CreationTimestamp
//	  @Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
//	  private Timestamp createTime;
//
//	  @UpdateTimestamp
//	  @Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = true, nullable = false)
//	  private Timestamp updateTime;
//	  
//	  @Column(columnDefinition = "tinyInt(1)")
//	  private boolean status;
//	  
//	  
//	  public Account(String username, String email, String password) {
//	    this.username = username;
//	    this.email = email;
//	    this.password = password;
//	    this.createTime = createTime;
//	    this.updateTime = updateTime;
//	    this.status = status;
//	  }
//}
