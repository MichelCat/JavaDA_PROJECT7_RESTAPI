package com.nnk.springboot.mapper;

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

    /**
     * Mapper Mapper to MultiValueMapMapper
     *
     * @param obj Object to convert
     * @return MultiValueMapMapper result
     */
    public static MultiValueMap<String, String> convert(Object obj) {
        MultiValueMap parameters = new LinkedMultiValueMap<String, String>();
        Map<String, String> maps = new ObjectMapper().convertValue(
                                                obj
                                                , new TypeReference<Map<String, String>>() {});
        parameters.setAll(maps);

        return parameters;
    }
}
