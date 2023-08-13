insert into trade(account, type, buy_quantity, sell_quantity, buy_price, sell_price
                 , trade_date, security, status, trader, benchmark, book, creation_name
                 , creation_date, revision_name, revision_date, deal_name, deal_type
                 , source_list_id, side
) values
    ('Trade Account', 'Type', 10.0, 11.0, 12.0, 13.0, '2020-01-01 01:02:03'
    , 'Security', 'Status', 'Trader', 'Benchmark', 'Book', 'CreationName', '2020-01-01 01:02:03'
    , 'RevisionName', '2020-01-01 01:02:03', 'DealName', 'DealType', 'SourceListId'
    , 'Side');
