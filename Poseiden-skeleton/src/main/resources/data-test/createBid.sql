insert into BidList (account, type, bidQuantity, askQuantity, bid, ask, benchmark, bidListDate
                    , commentary, security, status, trader, book, creationName, creationDate, revisionName
                    , revisionDate, dealName, dealType, sourceListId, side
) values
    ('Account Test', 'Type Test', 10.0, 11.0, 12.0, 13.0, 'Benchmark Test', '2020-01-01 01:02:03', 'Commentary Test'
    , 'Security Test', 'Status', 'Trader Test', 'Book Test', 'CreationName Test', '2020-01-01 01:02:03'
    , 'RevisionName Test', '2020-01-01 01:02:03', 'DealName Test', 'DealType Test'
    , 'SourceListId Test', 'Side Test');
