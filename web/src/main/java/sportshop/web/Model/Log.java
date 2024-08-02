package sportshop.web.Model;



import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "log")
public class Log {
	@Id
	@Column(name = "id", nullable = false)
	@PrimaryKeyJoinColumn(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "log", nullable = false, columnDefinition = "TEXT")
	@Lob
	private String logString;
	
	@Column(name = "create_time", nullable = false, updatable = false)
	@CreationTimestamp
	private Timestamp createTime;

}
