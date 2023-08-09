package com.nnk.springboot.data;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.mapper.MultiValueMapMapper;
import org.springframework.util.MultiValueMap;

public class RatingData {

    public static Rating getRatingSource() {
        return Rating.builder()
                .moodysRating("Moodys Rating")
                .sandPRating("Sand PRating")
                .fitchRating("Fitch Rating")
                .orderNumber(10)
                .build();
    }

    public static Rating getRatingSave() {
        Rating rating = getRatingSource();
        rating.setId(1);
        return rating;
    }

    public static MultiValueMap<String, String> getRatingSourceController() {
        Rating rating = Rating.builder()
                .moodysRating("Moodys Rating")
                .sandPRating("Sand PRating")
                .fitchRating("Fitch Rating")
                .orderNumber(10)
                .build();
        return MultiValueMapMapper.convert(rating);
    }

    public static MultiValueMap<String, String> getRatingSaveController() {
        Rating rating = Rating.builder()
                .id(1)
                .moodysRating("Moodys Rating")
                .sandPRating("Sand PRating")
                .fitchRating("Fitch Rating")
                .orderNumber(10)
                .build();
        return MultiValueMapMapper.convert(rating);
    }

    public final static String scriptCreateRating = "/data/createRating.sql";
}
