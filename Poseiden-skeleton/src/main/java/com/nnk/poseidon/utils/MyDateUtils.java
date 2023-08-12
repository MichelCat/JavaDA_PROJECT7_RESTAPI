package com.nnk.poseidon.utils;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * MyDateUtils is a date utility class
 *
 * @author MC
 * @version 1.0
 */
@Service
public class MyDateUtils {

    /**
     * Current date in Timestamp format
     *
     * @return Current date
     */
    public static Timestamp getcurrentTime() {
        long currentTimeMillis = System.currentTimeMillis();
        return new Timestamp(currentTimeMillis);
    }
}
