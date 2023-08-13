package com.nnk.poseidon.model;


/**
 * Role is Enumeration for list of user roles
 * 
 * @author MC
 * @version 1.0
 */
public enum Role {
  USER("USER")
  , ADMIN("ADMIN");

  public final String role;

  private Role(String role) {
    this.role = role;
  }
}
