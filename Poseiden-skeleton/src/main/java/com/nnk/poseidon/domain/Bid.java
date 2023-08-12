package com.nnk.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

/**
 * Bid is entity model
 *
 * @author MC
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "bid")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer bidListId;

    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "Maximum length of {max} characters")
    String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "Maximum length of {max} characters")
    String type;

    @NotNull(message = "Bid quantity must not be null")
    Double bidQuantity;

    Double askQuantity;

    Double bid;

    Double ask;

    @Size(max = 125, message = "Maximum length of {max} characters")
    String benchmark;

    Timestamp bidListDate;

    @Size(max = 125, message = "Maximum length of {max} characters")
    String commentary;

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
