package com.example.FireFly_backend.models.dto.auth;


import com.example.FireFly_backend.enums.Provider;
import com.example.FireFly_backend.models.dto.common.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class OAuth2UserInfoDTO extends BaseDTO {
    private String sub;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String email;
    private boolean email_verified;
    private String locale;
    private Provider provider = Provider.LOCAL;
}