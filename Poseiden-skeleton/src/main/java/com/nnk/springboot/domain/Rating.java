package com.nnk.springboot.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@DynamicUpdate
@Table(name = "rating")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank(message = "Moodys rating is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String moodysRating;

    @NotBlank(message = "Sand PRating is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String sandPRating;

    @NotBlank(message = "Fitch rating is mandatory")
    @Size(max = 125, message = "Maximum length of {max} characters")
    String fitchRating;

    @NotNull(message = "Order number cannot be null")
    Integer orderNumber;
}
