package com.nnk.poseidon.business;

import com.nnk.poseidon.model.Trade;
import com.nnk.poseidon.exception.MyExceptionBadRequestException;
import com.nnk.poseidon.exception.MyExceptionNotFoundException;
import com.nnk.poseidon.repository.TradeRepository;
import com.nnk.poseidon.utils.MyDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public Trade createTrade(final Trade trade)
                                throws MyExceptionBadRequestException {
        // Trade parameter is null
        if (trade == null) {
            log.debug("THROW, Trade is null.");
            throw new MyExceptionBadRequestException("throw.trade.nullTrade");
        }
        // Trade exist
        Integer id = trade.getTradeId();
        if (id != null) {
            Optional<Trade> optTradeEntity = tradeRepository.findById(id);
            if (optTradeEntity.isPresent()) {
                log.debug("THROW, Trade exist ({}).", optTradeEntity.get());
                throw new MyExceptionBadRequestException("throw.trade.tradeExists");
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
     * @throws MyExceptionNotFoundException Exception not found
     */
    public Trade getTradeById(final Integer id)
                                throws MyExceptionNotFoundException {
        // Trade does not exist
        Optional<Trade> optTradeEntity = tradeRepository.findById(id);
        if (optTradeEntity.isPresent() == false) {
            log.debug("THROW, Trade not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.trade.unknown", id);
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
     * @throws MyExceptionNotFoundException Exception not found
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public Trade updateTrade(final Integer id
                                , final Trade trade)
                                throws MyExceptionNotFoundException {
        // Trade does not exist
        Optional<Trade> optTradeEntity = tradeRepository.findById(id);
        if (optTradeEntity.isPresent() == false) {
            log.debug("THROW, Trade not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.trade.unknown", id);
        }
        // Trade parameter is null
        if (trade == null) {
            log.debug("THROW, Trade is null.");
            throw new MyExceptionBadRequestException("throw.trade.nullTrade");
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
     * @throws MyExceptionNotFoundException Exception not found
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteTrade(final Integer id)
                            throws MyExceptionNotFoundException {
        // Trade does not exist
        if (tradeRepository.existsById(id) == false) {
            log.debug("THROW, Trade not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.trade.unknown", id);
        }
        // Trade deleted
        tradeRepository.deleteById(id);
    }
}
