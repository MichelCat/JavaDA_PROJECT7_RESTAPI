package com.nnk.springboot.utils;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class MyDateUtils {

    public static Timestamp getcurrentTime() {
        long currentTimeMillis = System.currentTimeMillis();
        return new Timestamp(currentTimeMillis);
    }
}
