package com.nnk.poseidon.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * Mapper objet to MultiValueMapMapper
 *
 * @author MC
 * @version 1.0
 */
public class MultiValueMapMapper {

    private MultiValueMapMapper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Mapper Mapper to MultiValueMapMapper
     *
     * @param obj Object to convert
     * @return MultiValueMapMapper result
     */
    public static MultiValueMap<String, String> convert(Object obj) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Map<String, String> maps = new ObjectMapper().convertValue(
                                                obj
                                                , new TypeReference<Map<String, String>>() {});
        parameters.setAll(maps);

        return parameters;
    }
}
