package com.nnk.springboot.data;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.mapper.MultiValueMapMapper;
import org.springframework.util.MultiValueMap;

/**
 * BidData is the class containing the bid test data
 *
 * @author MC
 * @version 1.0
 */
public class BidData {

    public static BidList getBidSource() {
        return BidList.builder()
                .account("Account Test")
                .type("Type Test")
                .bidQuantity(10d)
                .askQuantity(11d)
                .bid(12d)
                .ask(13d)
                .benchmark("Benchmark Test")
                .bidListDate(GlobalData.CURRENT_TIMESTAMP)
                .commentary("Commentary Test")
                .security("Security Test")
                .status("Status")
                .trader("Trader Test")
                .book("Book Test")
                .creationName("CreationName Test")
                .creationDate(GlobalData.CURRENT_TIMESTAMP)
                .revisionName("RevisionName Test")
                .revisionDate(GlobalData.CURRENT_TIMESTAMP)
                .dealName("DealName Test")
                .dealType("DealType Test")
                .sourceListId("SourceListId Test")
                .side("Side Test")
                .build();
    }

    public static BidList getBidSave() {
        BidList bid = getBidSource();
        bid.setBidListId(1);
        return bid;
    }

    public static MultiValueMap<String, String> getBidSourceController() {
        BidList bid = BidList.builder()
                .account("Account Test")
                .type("Type Test")
                .bidQuantity(10d)
                .build();
        return MultiValueMapMapper.convert(bid);
    }

    public static MultiValueMap<String, String> getBidSaveController() {
        BidList bid = BidList.builder()
                .BidListId(1)
                .account("Account Test")
                .type("Type Test")
                .bidQuantity(10d)
                .build();
        return MultiValueMapMapper.convert(bid);
    }

    public final static String scriptCreateBid = "/data/createBid.sql";
}
