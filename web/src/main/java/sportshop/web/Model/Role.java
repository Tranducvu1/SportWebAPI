//package sportshop.web.Model;
//
//
//import java.sql.Timestamp;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Data;
//
//@Entity
//@Data
//@Table(name = "role")
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
//public class Role {
//	  @Id
//	  @GeneratedValue(strategy = GenerationType.IDENTITY)
//	  private Integer id;
//
//	  @Enumerated(EnumType.STRING)
//	  @Column(length = 20)
//	  private ERole Role;
//	  
//	  @Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
//	  private Timestamp createTime;
//		
//		
//		@Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = true, nullable = false)
//		private Timestamp updateTime;
//		
//		
//		 @Column(nullable = false)
//		    private boolean status;
//		 
//		 @Column(name = "description", nullable = false)
//			private String description;
//		 
//	
//}
//
