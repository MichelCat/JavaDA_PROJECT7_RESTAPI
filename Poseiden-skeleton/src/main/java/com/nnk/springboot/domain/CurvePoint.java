package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Entity
@DynamicUpdate
@Table(name = "curvepoint")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull(message = "Curve Id cannot be null")
    Integer curveId;

    Timestamp asOfDate;

    @NotNull(message = "Term cannot be null")
    Double term;

    @NotNull(message = "Value cannot be null")
    Double value;

    Timestamp creationDate;
}
