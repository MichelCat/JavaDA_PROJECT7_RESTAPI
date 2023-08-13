package com.nnk.poseidon.data;

import com.nnk.poseidon.model.CurvePoint;
import com.nnk.poseidon.mapper.MultiValueMapMapper;
import org.springframework.util.MultiValueMap;

/**
 * CurvePointData is the class containing the Curve Point test data
 *
 * @author MC
 * @version 1.0
 */
public class CurvePointData {

    public static CurvePoint getCurvePointSource() {
        return CurvePoint.builder()
                .curveId(10)
                .asOfDate(GlobalData.CURRENT_TIMESTAMP)
                .term(11d)
                .value(12d)
                .creationDate(GlobalData.CURRENT_TIMESTAMP)
                .build();
    }

    public static CurvePoint getCurvePointSave() {
        CurvePoint curvePoint = getCurvePointSource();
        curvePoint.setId(1);
        return curvePoint;
    }

    public static MultiValueMap<String, String> getCurvePointSourceController() {
        CurvePoint curvePoint = CurvePoint.builder()
                                .curveId(10)
                                .term(11d)
                                .value(12d)
                                .build();
        return MultiValueMapMapper.convert(curvePoint);
    }

    public static MultiValueMap<String, String> getCurvePointSaveController() {
        CurvePoint curvePoint = CurvePoint.builder()
                                .id(1)
                                .curveId(10)
                                .term(11d)
                                .value(12d)
                                .build();
        return MultiValueMapMapper.convert(curvePoint);
    }

    public final static String scriptCreateCurvePoint = "/data/createCurvePoint.sql";
}
