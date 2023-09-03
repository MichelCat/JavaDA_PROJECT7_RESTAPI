package com.nnk.poseidon.business;

import com.nnk.poseidon.model.Trade;
import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.repository.TradeRepository;
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
 * TradeBusiness is the Trades page processing service
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Service
public class TradeBusiness {

    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private MessageSource messageSource;

    /**
     * Find list of Trades
     *
     * @return List of Trades
     */
    public List<Trade> getTradesList() {
        return tradeRepository.findAll();
    }

    /**
     * Create new Trade
     *
     * @param trade The Trade object added
     *
     * @return Trade added
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Trade createTrade(final Trade trade)
                                throws MyException {
        // Trade parameter is null
        if (trade == null) {
            String msgSource = messageSource.getMessage("exception.trade.nullTrade"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Trade exist
        Integer id = trade.getTradeId();
        if (id != null) {
            Optional<Trade> optTradeEntity = tradeRepository.findById(id);
            if (optTradeEntity.isPresent()) {
                String msgSource = messageSource.getMessage("exception.trade.tradeExists"
                                    , new Object[] { id }
                                    , LocaleContextHolder.getLocale());
                log.debug("Exception, " + msgSource);
                throw new MyException(msgSource);
            }
        }
        // Trade saved
        trade.setCreationDate(MyDateUtils.getcurrentTime());
        return tradeRepository.save(trade);
    }

    /**
     * Find Trade
     *
     * @param id Trade ID founded
     *
     * @return Trade founded
     * @throws MyException Exception
     */
    public Trade getTradeById(final Integer id)
                                throws MyException {
        // Trade does not exist
        Optional<Trade> optTradeEntity = tradeRepository.findById(id);
        if (optTradeEntity.isEmpty()) {
            String msgSource = messageSource.getMessage("exception.trade.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Trade found
        return optTradeEntity.get();
    }

    /**
     * Updated Trade
     *
     * @param id Trade ID updated
     * @param trade The Trade object updated
     *
     * @return Trade updated
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Trade updateTrade(final Integer id
                                , final Trade trade)
                                throws MyException {
        // Trade does not exist
        Optional<Trade> optTradeEntity = tradeRepository.findById(id);
        if (optTradeEntity.isEmpty()) {
            String msgSource = messageSource.getMessage("exception.trade.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Trade parameter is null
        if (trade == null) {
            String msgSource = messageSource.getMessage("exception.trade.nullTrade"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Trade updated
        Trade tradeEntity = optTradeEntity.get();
        tradeEntity.setTradeId(trade.getTradeId());
        tradeEntity.setAccount(trade.getAccount());
        tradeEntity.setType(trade.getType());
        tradeEntity.setBuyQuantity(trade.getBuyQuantity());
        tradeEntity.setRevisionDate(MyDateUtils.getcurrentTime());
        return tradeRepository.save(tradeEntity);
    }

    /**
     * Deleted Trade
     *
     * @param id Trade ID deleted
     *
     * @return void
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteTrade(final Integer id)
                            throws MyException {
        // Trade does not exist
        if (tradeRepository.existsById(id) == false) {
            String msgSource = messageSource.getMessage("exception.trade.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Trade deleted
        tradeRepository.deleteById(id);
    }
}
