package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.model.Bid;
import com.nnk.poseidon.repository.BidRepository;
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
 * BidListBusiness is the bids page processing service
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Service
public class BidListBusiness {

    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private MessageSource messageSource;

    /**
     * Find list of bids
     *
     * @return List of bids
     */
    public List<Bid> getBidsList() {
        return bidRepository.findAll();
    }

    /**
     * Create new bid
     *
     * @param bid The bid object added
     *
     * @return Bid added
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Bid createBid(final Bid bid)
                                throws MyException {
        // Bid parameter is null
        if (bid == null) {
            String msgSource = messageSource.getMessage("exception.bid.nullBid"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Bid exist
        Integer id = bid.getBidListId();
        if (id != null) {
            Optional<Bid> optBidEntity = bidRepository.findById(id);
            if (optBidEntity.isPresent()) {
                String msgSource = messageSource.getMessage("exception.bid.bidExists"
                                    , new Object[] { id }
                                    , LocaleContextHolder.getLocale());
                log.debug("Exception, " + msgSource);
                throw new MyException(msgSource);
            }
        }
        // Bid saved
        bid.setCreationDate(MyDateUtils.getcurrentTime());
        return bidRepository.save(bid);
    }

    /**
     * Find bid
     *
     * @param id Bid ID founded
     *
     * @return Bid founded
     * @throws MyException Exception
     */
    public Bid getBidById(final Integer id)
                                throws MyException {
        // Bid does not exist
        Optional<Bid> optBidEntity = bidRepository.findById(id);
        if (optBidEntity.isEmpty()) {
            String msgSource = messageSource.getMessage("exception.bid.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Bid found
        return optBidEntity.get();
    }

    /**
     * Updated bid
     *
     * @param id Bid ID updated
     * @param bid The bid object updated
     *
     * @return Bid updated
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Bid updateBid(final Integer id
                            , final Bid bid)
                            throws MyException {
        // Bid does not exist
        Optional<Bid> optBidEntity = bidRepository.findById(id);
        if (optBidEntity.isEmpty()) {
            String msgSource = messageSource.getMessage("exception.bid.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Bid parameter is null
        if (bid == null) {
            String msgSource = messageSource.getMessage("exception.bid.nullBid"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
       }
        // Bid updated
        Bid bidEntity = optBidEntity.get();
        bidEntity.setAccount(bid.getAccount());
        bidEntity.setType(bid.getType());
        bidEntity.setBidQuantity(bid.getBidQuantity());
        bidEntity.setRevisionDate(MyDateUtils.getcurrentTime());
        return bidRepository.save(bidEntity);
    }

    /**
     * Deleted bid
     *
     * @param id Bid ID deleted
     *
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBid(final Integer id)
                            throws MyException {
        // Bid does not exist
        if (bidRepository.existsById(id) == false) {
            String msgSource = messageSource.getMessage("exception.bid.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Bid deleted
        bidRepository.deleteById(id);
    }
}
