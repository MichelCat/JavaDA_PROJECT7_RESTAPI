package com.nnk.springboot.data;

import com.nnk.springboot.domain.User;

public class UserData {

    public static User getUserSource() {
        return User.builder()
                .username("user@gmail.com")
                .password("$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa")
                .fullname("User")
                .role("USER")
                .expired(false)
                .locked(false)
                .credentialsExpired(false)
                .enabled(true)
                .build();
    }
}
