package com.nnk.poseidon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Rating is entity model
 *
 * @author MC
 * @version 1.0
 */
@Entity
@DynamicUpdate
@Table(name = "rating")
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank(message = "{constraint.notBlank.rating.moodysRating}")
    @Size(max = 125, message = "{constraint.size.global}")
    String moodysRating;

    @NotBlank(message = "{constraint.notBlank.rating.sandPRating}")
    @Size(max = 125, message = "{constraint.size.global}")
    @Column(name = "sand_p_rating")
    String sandPRating;

    @NotBlank(message = "{constraint.notBlank.rating.fitchRating}")
    @Size(max = 125, message = "{constraint.size.global}")
    String fitchRating;

    @NotNull(message = "{constraint.notNull.rating.orderNumber}")
    Integer orderNumber;
}
