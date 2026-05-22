package org.afrolink.er.news_api.auth.dto;

import java.util.UUID;

import org.afrolink.er.news_api.user.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String token;

    private UUID userId;

    private String email;

    private Role role;

}
