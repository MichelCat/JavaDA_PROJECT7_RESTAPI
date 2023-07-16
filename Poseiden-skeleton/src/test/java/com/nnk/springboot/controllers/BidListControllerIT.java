package com.nnk.springboot.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class BidListControllerIT {

    @Autowired
    private BidListController bidListController;

    @Test
    public void contextLoads() throws Exception {
        // GIVEN
        // WHEN
        assertThat(bidListController).isNotNull();
        // THEN
    }

}
