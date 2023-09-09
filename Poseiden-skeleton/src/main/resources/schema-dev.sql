-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
-- Setting up PoseidonDev DB
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------

drop database if exists PoseidonDev;

create database PoseidonDev;
use PoseidonDev;

CREATE TABLE bid (
    bid_list_id int NOT NULL AUTO_INCREMENT,
    account VARCHAR(30) NOT NULL,
    type VARCHAR(30) NOT NULL,
    bid_quantity DOUBLE,
    ask_quantity DOUBLE,
    bid DOUBLE ,
    ask DOUBLE,
    benchmark VARCHAR(125),
    bid_list_date TIMESTAMP,
    commentary VARCHAR(125),
    security VARCHAR(125),
    status VARCHAR(10),
    trader VARCHAR(125),
    book VARCHAR(125),
    creation_name VARCHAR(125),
    creation_date TIMESTAMP ,
    revision_name VARCHAR(125),
    revision_date TIMESTAMP ,
    deal_name VARCHAR(125),
    deal_type VARCHAR(125),
    source_list_id VARCHAR(125),
    side VARCHAR(125),

    constraint pk_bid_list PRIMARY KEY (bid_list_id)
);

CREATE TABLE trade (
    trade_id int NOT NULL AUTO_INCREMENT,
    account VARCHAR(30) NOT NULL,
    type VARCHAR(30) NOT NULL,
    buy_quantity DOUBLE,
    sell_quantity DOUBLE,
    buy_price DOUBLE ,
    sell_price DOUBLE,
    trade_date TIMESTAMP,
    security VARCHAR(125),
    status VARCHAR(10),
    trader VARCHAR(125),
    benchmark VARCHAR(125),
    book VARCHAR(125),
    creation_name VARCHAR(125),
    creation_date TIMESTAMP ,
    revision_name VARCHAR(125),
    revision_date TIMESTAMP ,
    deal_name VARCHAR(125),
    deal_type VARCHAR(125),
    source_list_id VARCHAR(125),
    side VARCHAR(125),

    constraint pk_trade PRIMARY KEY (trade_id)
);

CREATE TABLE curve_point (
    id int NOT NULL AUTO_INCREMENT,
    curve_id int,
    as_of_date TIMESTAMP,
    term DOUBLE ,
    value DOUBLE ,
    creation_date TIMESTAMP ,

    constraint pk_curve_point PRIMARY KEY (id)
);

CREATE TABLE rating (
    id int NOT NULL AUTO_INCREMENT,
    moodys_rating VARCHAR(125),
    sand_p_rating VARCHAR(125),
    fitch_rating VARCHAR(125),
    order_number int,

    constraint pk_rating PRIMARY KEY (id)
);

CREATE TABLE rule (
    id int NOT NULL AUTO_INCREMENT,
    name VARCHAR(125),
    description VARCHAR(125),
    json VARCHAR(125),
    template VARCHAR(512),
    sql_str VARCHAR(125),
    sql_part VARCHAR(125),

    constraint pk_rule PRIMARY KEY (id)
);

CREATE TABLE user (
    id int NOT NULL AUTO_INCREMENT,              -- User ID
    username VARCHAR(125),                       -- Email used to authenticate the user
    password VARCHAR(125),                       -- Password used to authenticate the user
    fullname VARCHAR(125),                       -- Full name
    role VARCHAR(125),                           -- User role
    expired boolean,                             -- User account expired
    locked boolean,                              -- User locked
    credentials_expired boolean,                 -- User credentials (password) expired
    enabled boolean,                             -- Activated user
    email_validation_key varchar(36),            -- Email validation key
    valid_email_end_date datetime,               -- Valid email end date

    constraint pk_user PRIMARY KEY (id),
    constraint uc_user_username UNIQUE KEY (username),
    constraint uc_user_email_validation_key UNIQUE KEY (email_validation_key)
);
