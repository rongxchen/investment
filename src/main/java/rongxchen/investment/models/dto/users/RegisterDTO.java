package rongxchen.investment.models.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterDTO {

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

}
