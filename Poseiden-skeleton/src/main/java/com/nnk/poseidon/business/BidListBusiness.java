package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyExceptionBadRequestException;
import com.nnk.poseidon.exception.MyExceptionNotFoundException;
import com.nnk.poseidon.domain.Bid;
import com.nnk.poseidon.repositories.BidRepository;
import com.nnk.poseidon.utils.MyDateUtils;
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
    private BidRepository bidRepository;

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
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public Bid createBid(final Bid bid)
                                throws MyExceptionBadRequestException {
        // Bid parameter is null
        if (bid == null) {
            log.debug("THROW, Bid is null.");
            throw new MyExceptionBadRequestException("throw.bid.nullBid");
        }
        // Bid exist
        Integer id = bid.getBidListId();
        if (id != null) {
            Optional<Bid> optBidEntity = bidRepository.findById(id);
            if (optBidEntity.isPresent()) {
                log.debug("THROW, Bid exist ({}).", optBidEntity.get());
                throw new MyExceptionBadRequestException("throw.bid.bidExists");
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
     * @throws MyExceptionNotFoundException Exception not found
     */
    public Bid getBidById(final Integer id)
                                throws MyExceptionNotFoundException {
        // Bid does not exist
        Optional<Bid> optBidEntity = bidRepository.findById(id);
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
    public Bid updateBid(final Integer id
                            , final Bid bid)
                            throws MyExceptionNotFoundException {
        // Bid does not exist
        Optional<Bid> optBidEntity = bidRepository.findById(id);
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
     * @return void
     * @throws MyExceptionNotFoundException Exception not found
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBid(final Integer id)
                            throws MyExceptionNotFoundException {
        // Bid does not exist
        Optional<Bid> optBidEntity = bidRepository.findById(id);
        if (optBidEntity.isPresent() == false) {
            log.debug("THROW, Bid not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.bid.unknown", id);
        }
        // Bid deleted
        bidRepository.deleteById(id);
    }
}
