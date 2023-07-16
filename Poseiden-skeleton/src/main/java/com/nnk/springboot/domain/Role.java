package com.nnk.springboot.domain;

// User role list

/**
 * AppUserRole is Enumeration for list of user roles
 * 
 * @author MC
 * @version 1.0
 */
public enum Role {
  USER_ROLE("USER_ROLE")
  , ADMIN_ROLE("ADMIN_ROLE");

  public final String role;

  private Role(String role) {
    this.role = role;
  }
}
