package com.nnk.springboot.data;

import com.nnk.springboot.domain.Rating;

public class RatingData {

    public static Rating getRatingSource() {
        return Rating.builder()
                .moodysRating("Moodys Rating")
                .sandPRating("Sand PRating")
                .fitchRating("Fitch Rating")
                .orderNumber(10)
                .build();
    }
}
