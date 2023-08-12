package com.nnk.poseidon.data;

import java.sql.Timestamp;

/**
 * GlobalData is the class containing the global test data
 *
 * @author MC
 * @version 1.0
 */
public class GlobalData {

    public final static Timestamp CURRENT_TIMESTAMP = Timestamp.valueOf("2020-01-01 01:02:03");

    public final static String scriptClearDataBase = "/data/clearDataBase.sql";
}
