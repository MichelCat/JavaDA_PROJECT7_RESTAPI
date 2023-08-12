insert into bid (account, type, bid_quantity, ask_quantity, bid, ask, benchmark, bid_list_date
                    , commentary, security, status, trader, book, creation_name, creation_date, revision_name
                    , revision_date, deal_name, deal_type, source_list_id, side
) values
    ('Account Test', 'Type Test', 10.0, 11.0, 12.0, 13.0, 'Benchmark Test', '2020-01-01 01:02:03', 'Commentary Test'
    , 'Security Test', 'Status', 'Trader Test', 'Book Test', 'CreationName Test', '2020-01-01 01:02:03'
    , 'RevisionName Test', '2020-01-01 01:02:03', 'DealName Test', 'DealType Test'
    , 'SourceListId Test', 'Side Test');
