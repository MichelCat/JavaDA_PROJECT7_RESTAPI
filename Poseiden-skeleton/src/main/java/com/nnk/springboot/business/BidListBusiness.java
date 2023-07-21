package com.nnk.springboot.business;

import com.nnk.springboot.Exception.MyExceptionBadRequestException;
import com.nnk.springboot.Exception.MyExceptionNotFoundException;
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
 * BidListBusiness is the home page processing service
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
    public BidList createBid(final BidList bid) throws MyExceptionBadRequestException {
        //=>coucou
//        // Bid is null
//        if (bid == null) {
//            throw new MyExceptionBadRequestException("throw.bid.nullBid");
//        }
        //<=coucou
        // Bid exist
        Integer id = bid.getBidListId();
        if (id != null) {
            Optional<BidList> optBidEntity = bidListRepository.findById(id);
            if (optBidEntity.isPresent()) {
                log.info("THROW, Bid exist ({}).", optBidEntity.get());
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
    public BidList getBidById(final Integer id) throws MyExceptionNotFoundException {
        // Bid does not exist
//        BidList bidEntity = bidListRepository.findById(id)
//                .orElseThrow(() -> new MyExceptionNotFoundException("throw.bid.unknown", id));
        Optional<BidList> optBidEntity = bidListRepository.findById(id);
        if (optBidEntity.isPresent() == false) {
            log.info("THROW, Bid not exist ({}).", id);
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
        //=>coucou
//        // Bid ID parameter is null
//        if (id == null) {
//            throw new MyExceptionBadRequestException("throw.bid.nullId");
//        }
//        // Bid ID parameter is zero
//        if (id == 0) {
//            throw new MyExceptionBadRequestException("throw.bid.zeroId");
//        }
//        // Bid parameter is null
//        if (bid == null) {
//            throw new MyExceptionBadRequestException("throw.bid.nullBid");
//        }
        //<=coucou
        // Bid does not exist
//        BidList bidEntity = bidListRepository.findById(id)
//                .orElseThrow(() -> new MyExceptionNotFoundException("throw.bid.unknown", id));
        Optional<BidList> optBidEntity = bidListRepository.findById(id);
        if (optBidEntity.isPresent() == false) {
            log.info("THROW, Bid not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.bid.unknown", id);
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
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteBid(final Integer id)
                    throws MyExceptionNotFoundException {
        //=>coucou
//        // Bid ID parameter is null
//        if (id == null) {
//            throw new MyExceptionBadRequestException("throw.bid.nullId");
//        }
//        // Bid ID parameter is zero
//        if (id == 0) {
//            throw new MyExceptionBadRequestException("throw.bid.zeroId");
//        }
        //=<coucou
        // Bid does not exist
//        BidList bidEntity = bidListRepository.findById(id)
//                .orElseThrow(() -> new MyExceptionNotFoundException("throw.bid.unknown", id));
        Optional<BidList> optBidEntity = bidListRepository.findById(id);
        if (optBidEntity.isPresent() == false) {
            log.info("THROW, Bid not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.bid.unknown", id);
        }
        // Bid deleted
        bidListRepository.deleteById(id);
    }
}
