package sportshop.web.DTO;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDTO {

    @NotEmpty(message = "Old password is required")
    private String oldPassword;

    @NotEmpty(message = "New password is required")
    @Length(min = 8, max = 32, message = "Password must be between 8 and 32 characters long")
    private String newPassword;

    @NotEmpty(message = "Please confirm the new password")
    private String confirmNewPassword;

}
