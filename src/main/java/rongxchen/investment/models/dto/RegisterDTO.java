package rongxchen.investment.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Min(value = 8, message = "Password must be at least 8 characters long")
    private String password;

}
