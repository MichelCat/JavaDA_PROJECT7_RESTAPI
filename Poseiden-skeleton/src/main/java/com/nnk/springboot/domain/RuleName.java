package com.nnk.springboot.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@DynamicUpdate
@Table(name = "rulename")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
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
