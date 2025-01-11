package sportshop.web.Service;

public interface SecurityService {
    String findLoggedInUsername();
    void autoLogin(String email, String password);
}
