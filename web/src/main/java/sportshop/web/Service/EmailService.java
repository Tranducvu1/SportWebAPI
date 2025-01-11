package sportshop.web.Service;

public interface EmailService {
    void sendOrderConfirmation(String to, String subject, String text);
}
