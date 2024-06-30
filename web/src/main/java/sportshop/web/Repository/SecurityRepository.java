package sportshop.web.Repository;

import org.springframework.stereotype.Repository;

@Repository
public interface SecurityRepository {

	String findLoggedInUsername();

	void autologin(String email, String password);

}
