package com.nnk.poseidon.Retention;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(authorities = "ADMIN")
public @interface WithMockRoleAdmin {
}
