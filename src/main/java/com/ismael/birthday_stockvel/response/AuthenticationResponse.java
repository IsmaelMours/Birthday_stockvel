package com.ismael.birthday_stockvel.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private Long id;
    private String email;
    private String roles;
    private String token;



}
