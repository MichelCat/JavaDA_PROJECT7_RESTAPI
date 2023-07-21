package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "users")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    @NotBlank(message = "Username is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String username;

    @ToString.Exclude
    @NotBlank(message = "Password is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String password;

    @NotBlank(message = "FullName is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String fullname;

    @NotBlank(message = "Role is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String role;
}
