package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

/**
 * CurvePoint is entity model
 *
 * @author MC
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "curvepoint")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull(message = "Curve Id must not be null")
    Integer curveId;

    Timestamp asOfDate;

    @NotNull(message = "Term must not be null")
    Double term;

    @NotNull(message = "Value must not be null")
    Double value;

    Timestamp creationDate;
}
