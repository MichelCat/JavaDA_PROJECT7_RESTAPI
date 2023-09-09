package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.model.CurvePoint;
import com.nnk.poseidon.repository.CurvePointRepository;
import com.nnk.poseidon.utils.MyDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * CurvePointBusiness is the CurvePoints page processing service
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Service
public class CurvePointBusiness {

    @Autowired
    private CurvePointRepository curvePointRepository;
    @Autowired
    private MessageSource messageSource;

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
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public CurvePoint createCurvePoint(final CurvePoint curvePoint)
                                        throws MyException {
        // CurvePoint parameter is null
        if (curvePoint == null) {
            String msgSource = messageSource.getMessage("exception.curvePoint.nullCurvePoint"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // CurvePoint exist
        Integer id = curvePoint.getId();
        if (id != null) {
            Optional<CurvePoint> optCurvePointEntity = curvePointRepository.findById(id);
            if (optCurvePointEntity.isPresent()) {
                String msgSource = messageSource.getMessage("exception.curvePoint.curvePointExists"
                                    , new Object[] { id }
                                    , LocaleContextHolder.getLocale());
                log.debug("Exception, " + msgSource);
                throw new MyException(msgSource);
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
     * @throws MyException Exception
     */
    public CurvePoint getCurvePointById(final Integer id)
                                        throws MyException {
        // CurvePoint does not exist
        Optional<CurvePoint> optCurvePointEntity = curvePointRepository.findById(id);
        if (optCurvePointEntity.isEmpty()) {
            String msgSource = messageSource.getMessage("exception.curvePoint.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
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
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public CurvePoint updateCurvePoint(final Integer id
                                        , final CurvePoint curvePoint)
                                        throws MyException {
        // CurvePoint does not exist
        Optional<CurvePoint> optCurvePointEntity = curvePointRepository.findById(id);
        if (optCurvePointEntity.isEmpty()) {
            String msgSource = messageSource.getMessage("exception.curvePoint.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // CurvePoint parameter is null
        if (curvePoint == null) {
            String msgSource = messageSource.getMessage("exception.curvePoint.nullCurvePoint"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
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
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteCurvePoint(final Integer id)
                                    throws MyException {
        // CurvePoint does not exist
        if (curvePointRepository.existsById(id) == false) {
            String msgSource = messageSource.getMessage("exception.curvePoint.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // CurvePoint deleted
        curvePointRepository.deleteById(id);
    }
}
