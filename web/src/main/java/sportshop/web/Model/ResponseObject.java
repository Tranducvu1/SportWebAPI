package sportshop.web.Model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseObject {

	private Object data;
	private Map<String, String> errorMessages = null ;
	private String status;
}