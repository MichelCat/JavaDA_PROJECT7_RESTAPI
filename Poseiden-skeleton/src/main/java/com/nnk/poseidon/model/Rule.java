package com.nnk.poseidon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Rule is entity model
 *
 * @author MC
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "rule")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank(message = "{constraint.notBlank.rule.name}")
    @Size(max = 125, message = "{constraint.size.global}")
    String name;

    @NotBlank(message = "{constraint.notBlank.rule.description}")
    @Size(max = 125, message = "{constraint.size.global}")
    String description;

    @NotBlank(message = "{constraint.notBlank.rule.json}")
    @Size(max = 125, message = "{constraint.size.global}")
    String json;

    @NotBlank(message = "{constraint.notBlank.rule.template}")
    @Size(max = 125, message = "{constraint.size.global}")
    String template;

    @NotBlank(message = "{constraint.notBlank.rule.sqlStr}")
    @Size(max = 125, message = "{constraint.size.global}")
    String sqlStr;

    @NotBlank(message = "{constraint.notBlank.rule.sqlPart}")
    @Size(max = 125, message = "{constraint.size.global}")
    String sqlPart;
}
