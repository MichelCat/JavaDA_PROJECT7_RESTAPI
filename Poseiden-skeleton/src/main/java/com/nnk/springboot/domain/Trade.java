package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Entity
@DynamicUpdate
@Table(name = "trade")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "Maximum length of {max} characters")
    String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "Maximum length of {max} characters")
    String type;

    @NotNull(message = "Buy quantity cannot be null")
    Double buyQuantity;

    Double sellQuantity;

    Double buyPrice;

    Double sellPrice;

    @Size(max = 125, message = "Maximum length of {max} characters")
    String benchmark;

    Timestamp tradeDate;

    @Size(max = 125, message = "Maximum length of {max} characters")
    String security;

    @Size(max = 10, message = "Maximum length of {max} characters")
    String status;

    @Size(max = 125, message = "Maximum length of {max} characters")
    String trader;

    @Size(max = 125, message = "Maximum length of {max} characters")
    String book;

    @Size(max = 125, message = "Maximum length of {max} characters")
    String creationName;

    Timestamp creationDate;

    @Size(max = 125, message = "Maximum length of {max} characters")
    String revisionName;

    Timestamp revisionDate;

    @Size(max = 125, message = "Maximum length of {max} characters")
    String dealName;

    @Size(max = 125, message = "Maximum length of {max} characters")
    String dealType;

    @Size(max = 125, message = "Maximum length of {max} characters")
    String sourceListId;

    @Size(max = 125, message = "Maximum length of {max} characters")
    String side;
}
