package sportshop.web.Entity;

import java.util.Map;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class ResponseObject {

	private Object data;
	private Map<String, String> errorMessages = null ;
	private String status;
}