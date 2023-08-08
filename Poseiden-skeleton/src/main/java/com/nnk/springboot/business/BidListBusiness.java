package com.nnk.springboot.business;

import com.nnk.springboot.exception.MyExceptionBadRequestException;
import com.nnk.springboot.exception.MyExceptionNotFoundException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.utils.MyDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private BidListRepository bidListRepository;

    /**
     * Find list of bids
     *
     * @return List of bids
     */
    public List<BidList> getBidsList() {
        return bidListRepository.findAll();
    }

    /**
     * Create new bid
     *
     * @param bid The bid object added
     *
     * @return Bid added
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public BidList createBid(final BidList bid)
                                throws MyExceptionBadRequestException {
        // Bid parameter is null
        if (bid == null) {
            log.debug("THROW, Bid is null.");
            throw new MyExceptionBadRequestException("throw.bid.nullBid");
        }
        // Bid exist
        Integer id = bid.getBidListId();
        if (id != null) {
            Optional<BidList> optBidEntity = bidListRepository.findById(id);
            if (optBidEntity.isPresent()) {
                log.debug("THROW, Bid exist ({}).", optBidEntity.get());
                throw new MyExceptionBadRequestException("throw.bid.bidExists");
            }
        }
        // Bid saved
        bid.setCreationDate(MyDateUtils.getcurrentTime());
        return bidListRepository.save(bid);
    }

    /**
     * Find bid
     *
     * @param id Bid ID founded
     *
     * @return Bid founded
     * @throws MyExceptionNotFoundException Exception not found
     */
    public BidList getBidById(final Integer id)
                                throws MyExceptionNotFoundException {
        // Bid does not exist
        Optional<BidList> optBidEntity = bidListRepository.findById(id);
        if (optBidEntity.isPresent() == false) {
            log.debug("THROW, Bid not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.bid.unknown", id);
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
     * @throws MyExceptionNotFoundException Exception not found
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public BidList updateBid(final Integer id
                            , final BidList bid)
                            throws MyExceptionNotFoundException {
        // Bid does not exist
        Optional<BidList> optBidEntity = bidListRepository.findById(id);
        if (optBidEntity.isPresent() == false) {
            log.debug("THROW, Bid not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.bid.unknown", id);
        }
        // Bid parameter is null
        if (bid == null) {
            log.debug("THROW, Bid is null.");
            throw new MyExceptionBadRequestException("throw.bid.nullBid");
        }
        // Bid updated
        BidList bidEntity = optBidEntity.get();
        bidEntity.setAccount(bid.getAccount());
        bidEntity.setType(bid.getType());
        bidEntity.setBidQuantity(bid.getBidQuantity());
        bidEntity.setRevisionDate(MyDateUtils.getcurrentTime());
        return bidListRepository.save(bidEntity);
    }

    /**
     * Deleted bid
     *
     * @param id Bid ID deleted
     *
     * @throws MyExceptionNotFoundException Exception not found
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBid(final Integer id)
                            throws MyExceptionNotFoundException {
        // Bid does not exist
        Optional<BidList> optBidEntity = bidListRepository.findById(id);
        if (optBidEntity.isPresent() == false) {
            log.debug("THROW, Bid not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.bid.unknown", id);
        }
        // Bid deleted
        bidListRepository.deleteById(id);
    }
}
