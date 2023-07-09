package com.nnk.springboot.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@DynamicUpdate
@Table(name = "bidlist")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer BidListId;

    @NotBlank(message = "Account is mandatory")
    @Size(max = 30, message = "Maximum length of {max} characters")
    String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 30, message = "Maximum length of {max} characters")
    String type;

    @NotNull(message = "Bid quantity cannot be null")
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
