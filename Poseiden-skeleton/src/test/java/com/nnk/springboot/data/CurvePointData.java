package com.nnk.springboot.data;

import com.nnk.springboot.domain.CurvePoint;

public class CurvePointData {

    public static CurvePoint getCurvePointSource() {
        return CurvePoint.builder()
                .curveId(10)
                .term(10d)
                .value(30d)
                .build();
    }
}
