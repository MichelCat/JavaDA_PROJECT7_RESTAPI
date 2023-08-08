package com.nnk.springboot.business;

import com.nnk.springboot.exception.MyExceptionBadRequestException;
import com.nnk.springboot.exception.MyExceptionNotFoundException;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.utils.MyDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * CurveBusiness is the CurvePoints page processing service
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Service
public class CurveBusiness {

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Find list of CurvePoints
     *
     * @return List of CurvePoints
     */
    public List<CurvePoint> getCurvePointsList() {
        return curvePointRepository.findAll();
    }

    /**
     * Create new CurvePoint
     *
     * @param curvePoint The CurvePoint object added
     *
     * @return CurvePoint added
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public CurvePoint createCurvePoint(final CurvePoint curvePoint)
                                        throws MyExceptionBadRequestException {
        // CurvePoint parameter is null
        if (curvePoint == null) {
            log.debug("THROW, CurvePoint is null.");
            throw new MyExceptionBadRequestException("throw.curvePoint.nullCurvePoint");
        }
        // CurvePoint exist
        Integer id = curvePoint.getId();
        if (id != null) {
            Optional<CurvePoint> optCurvePointEntity = curvePointRepository.findById(id);
            if (optCurvePointEntity.isPresent()) {
                log.debug("THROW, CurvePoint exist ({}).", optCurvePointEntity.get());
                throw new MyExceptionBadRequestException("throw.curvePoint.curvePointExists");
            }
        }
        // CurvePoint saved
        curvePoint.setCreationDate(MyDateUtils.getcurrentTime());
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Find CurvePoint
     *
     * @param id CurvePoint ID founded
     *
     * @return CurvePoint founded
     * @throws MyExceptionNotFoundException Exception not found
     */
    public CurvePoint getCurvePointById(final Integer id)
                                        throws MyExceptionNotFoundException {
        // CurvePoint does not exist
        Optional<CurvePoint> optCurvePointEntity = curvePointRepository.findById(id);
        if (optCurvePointEntity.isPresent() == false) {
            log.debug("THROW, CurvePoint not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.curvePoint.unknown", id);
        }
        // CurvePoint found
        return optCurvePointEntity.get();
    }

    /**
     * Updated CurvePoint
     *
     * @param id CurvePoint ID updated
     * @param curvePoint The CurvePoint object updated
     *
     * @return CurvePoint updated
     * @throws MyExceptionNotFoundException Exception not found
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public CurvePoint updateCurvePoint(final Integer id
                                        , final CurvePoint curvePoint)
                                        throws MyExceptionNotFoundException {
        // CurvePoint does not exist
        Optional<CurvePoint> optCurvePointEntity = curvePointRepository.findById(id);
        if (optCurvePointEntity.isPresent() == false) {
            log.debug("THROW, CurvePoint not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.curvePoint.unknown", id);
        }
        // CurvePoint parameter is null
        if (curvePoint == null) {
            log.debug("THROW, CurvePoint is null.");
            throw new MyExceptionBadRequestException("throw.curvePoint.nullCurvePoint");
        }
        // CurvePoint updated
        CurvePoint curvePointEntity = optCurvePointEntity.get();
        curvePointEntity.setCurveId(curvePoint.getCurveId());
        curvePointEntity.setTerm(curvePoint.getTerm());
        curvePointEntity.setValue(curvePoint.getValue());
        curvePointEntity.setAsOfDate(MyDateUtils.getcurrentTime());
        return curvePointRepository.save(curvePointEntity);
    }

    /**
     * Deleted CurvePoint
     *
     * @param id CurvePoint ID deleted
     *
     * @throws MyExceptionNotFoundException Exception not found
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteCurvePoint(final Integer id)
                                    throws MyExceptionNotFoundException {
        // CurvePoint does not exist
        Optional<CurvePoint> optCurvePointEntity = curvePointRepository.findById(id);
        if (optCurvePointEntity.isPresent() == false) {
            log.debug("THROW, CurvePoint not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.curvePoint.unknown", id);
        }
        // CurvePoint deleted
        curvePointRepository.deleteById(id);
    }
}
