package com.nnk.springboot.data;

import com.nnk.springboot.domain.BidList;

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
        return BidList.builder()
                .BidListId(1)
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

    public final static String scriptClearDataBase = "/data-test/clearDataBase.sql";
    public final static String scriptCreateBid = "/data-test/createBid.sql";
}
