package com.nnk.springboot.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@DynamicUpdate
@Table(name = "curvepoint")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
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
