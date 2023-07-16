package com.nnk.springboot.business;

import com.nnk.springboot.Exception.MyExceptionBadRequestException;
import com.nnk.springboot.Exception.MyExceptionNotFoundException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.utils.MyDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListBusiness {

    @Autowired
    private BidListRepository bidListRepository;

    public List<BidList> getBidsList() {
        return bidListRepository.findAll();
    }

    public BidList createBid(final BidList bid) throws MyExceptionBadRequestException {
        // Bid is null
        if (bid == null) {
            throw new MyExceptionBadRequestException("throw.bid.nullBid");
        }
        // Bid exist
        Integer id = bid.getBidListId();
        if (id != null) {
            Optional<BidList> optBidEntity = bidListRepository.findById(id);
            if (optBidEntity.isPresent()) {
                throw new MyExceptionBadRequestException("throw.bid.bidExists");
            }
        }
        // Bid saved
        bid.setCreationDate(MyDateUtils.getcurrentTime());
        return bidListRepository.save(bid);
    }

    public BidList getBidById(final Integer id) throws MyExceptionNotFoundException {
        // Bid does not exist
        BidList bidEntity = bidListRepository.findById(id)
                .orElseThrow(() -> new MyExceptionNotFoundException("throw.bid.unknown", id));
        // Bid found
        return bidEntity;
    }

    public BidList updateBid(final Integer id, final BidList bid)
                        throws MyExceptionNotFoundException, MyExceptionBadRequestException {
        // Bid ID parameter is null
        if (id == null) {
            throw new MyExceptionBadRequestException("throw.bid.nullId");
        }
        // Bid ID parameter is zero
        if (id == 0) {
            throw new MyExceptionBadRequestException("throw.bid.zeroId");
        }
        // Bid parameter is null
        if (bid == null) {
            throw new MyExceptionBadRequestException("throw.bid.nullBid");
        }
        // Bid does not exist
        BidList bidEntity = bidListRepository.findById(id)
                .orElseThrow(() -> new MyExceptionNotFoundException("throw.bid.unknown", id));
        // Bid updated
        bidEntity.setAccount(bid.getAccount());
        bidEntity.setType(bid.getType());
        bidEntity.setBidQuantity(bid.getBidQuantity());
        bidEntity.setRevisionDate(MyDateUtils.getcurrentTime());
        return bidListRepository.save(bidEntity);
    }

    public void deleteBid(final Integer id)
                    throws MyExceptionNotFoundException, MyExceptionBadRequestException {
        // Bid ID parameter is null
        if (id == null) {
            throw new MyExceptionBadRequestException("throw.bid.nullId");
        }
        // Bid ID parameter is zero
        if (id == 0) {
            throw new MyExceptionBadRequestException("throw.bid.zeroId");
        }
        // Bid does not exist
        BidList bidEntity = bidListRepository.findById(id)
                .orElseThrow(() -> new MyExceptionNotFoundException("throw.bid.unknown", id));
        // Bid deleted
        bidListRepository.deleteById(id);
    }
}
