package com.nnk.poseidon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

/**
 * Trade is entity model
 *
 * @author MC
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "trade")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer tradeId;

    @NotBlank(message = "{constraint.notBlank.trade.account}")
    @Size(max = 30, message = "{constraint.size.global}")
    String account;

    @NotBlank(message = "{constraint.notBlank.trade.type}")
    @Size(max = 30, message = "{constraint.size.global}")
    String type;

    @NotNull(message = "{constraint.notNull.trade.buyQuantity}")
    Double buyQuantity;

    Double sellQuantity;

    Double buyPrice;

    Double sellPrice;

    @Size(max = 125, message = "{constraint.size.global}")
    String benchmark;

    Timestamp tradeDate;

    @Size(max = 125, message = "{constraint.size.global}")
    String security;

    @Size(max = 10, message = "{constraint.size.global}")
    String status;

    @Size(max = 125, message = "{constraint.size.global}")
    String trader;

    @Size(max = 125, message = "{constraint.size.global}")
    String book;

    @Size(max = 125, message = "{constraint.size.global}")
    String creationName;

    Timestamp creationDate;

    @Size(max = 125, message = "{constraint.size.global}")
    String revisionName;

    Timestamp revisionDate;

    @Size(max = 125, message = "{constraint.size.global}")
    String dealName;

    @Size(max = 125, message = "{constraint.size.global}")
    String dealType;

    @Size(max = 125, message = "{constraint.size.global}")
    String sourceListId;

    @Size(max = 125, message = "{constraint.size.global}")
    String side;
}
