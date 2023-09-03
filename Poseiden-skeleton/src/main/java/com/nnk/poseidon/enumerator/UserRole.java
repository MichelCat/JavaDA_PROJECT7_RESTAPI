package com.nnk.poseidon.enumerator;


/**
 * UserRole is Enumeration for list of user roles
 * 
 * @author MC
 * @version 1.0
 */
public enum UserRole {
  USER("USER")
  , ADMIN("ADMIN");

  public final String role;

  private UserRole(String role) {
    this.role = role;
  }
}
