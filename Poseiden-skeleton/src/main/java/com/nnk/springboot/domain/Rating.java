package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "rating")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
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
