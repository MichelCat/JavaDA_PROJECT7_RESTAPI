package com.nnk.poseidon.model;

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
@Table(name = "curve_point")
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

    @NotNull(message = "{constraint.notNull.curvePoint.curveId}")
    Integer curveId;

    Timestamp asOfDate;

    @NotNull(message = "{constraint.notNull.curvePoint.term}")
    Double term;

    @NotNull(message = "{constraint.notNull.curvePoint.value}")
    Double value;

    Timestamp creationDate;
}
