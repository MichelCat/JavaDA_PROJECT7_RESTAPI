package com.nnk.poseidon.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.poseidon.data.BidData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.MultiValueMap;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class MultiValueMapMapperTest {

    // -----------------------------------------------------------------------------------------------
    // Convert method
    // -----------------------------------------------------------------------------------------------
    @Test
    void convert_ObjToMultiValueMap() {
        // GIVEN
        // Convert object to Map
        ObjectMapper objMapper = new ObjectMapper();
        Map<String, String> objMap = objMapper.convertValue(
                                                        BidData.getBidSource()
                                                        , new TypeReference<Map<String, String>>() {});
        // WHEN
        // Convert object to MultiValueMap
        MultiValueMap<String, String> objMultiValueMap = MultiValueMapMapper.convert(BidData.getBidSource());
        // THEN
        //  Compare Map to MultiValueMap
        assertThat(objMultiValueMap).isNotNull();
        assertThat(objMap).hasSize(objMultiValueMap.size());

        for (Map.Entry<String, String> entry : objMap.entrySet()) {
            assertThat((Collection<String>) objMultiValueMap.get(entry.getKey()))
                    .containsExactly(entry.getValue());
        }
    }
}
