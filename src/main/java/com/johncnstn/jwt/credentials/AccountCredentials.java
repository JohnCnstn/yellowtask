package com.johncnstn.jwt.credentials;

import lombok.Getter;
import lombok.Setter;

public class AccountCredentials {

    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private String password;
}
