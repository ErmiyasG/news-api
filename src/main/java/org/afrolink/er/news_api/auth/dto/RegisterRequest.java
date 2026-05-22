package org.afrolink.er.news_api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.afrolink.er.news_api.user.entity.Role;

@Data
public class RegisterRequest {
    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private Role role;
}
