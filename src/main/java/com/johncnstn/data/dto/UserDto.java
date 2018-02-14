package com.johncnstn.data.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserDto {

    @NotNull
    @NotEmpty
    @Setter
    @Getter
    private String userName;

    @NotNull
    @NotEmpty
    @Setter
    @Getter
    private String password;
}
