package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "rulename")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String name;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String description;

    @NotBlank(message = "Json is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String json;

    @NotBlank(message = "Template is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String template;

    @NotBlank(message = "SqlStr is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String sqlStr;

    @NotBlank(message = "SqlPart is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String sqlPart;
}
