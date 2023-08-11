-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
-- Setting up PoseidonDev DB
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------

drop database if exists PoseidonDev;

create database PoseidonDev;
use PoseidonDev;

CREATE TABLE BidList (
    bidListId int NOT NULL AUTO_INCREMENT,
    account VARCHAR(30) NOT NULL,
    type VARCHAR(30) NOT NULL,
    bidQuantity DOUBLE,
    askQuantity DOUBLE,
    bid DOUBLE ,
    ask DOUBLE,
    benchmark VARCHAR(125),
    bidListDate TIMESTAMP,
    commentary VARCHAR(125),
    security VARCHAR(125),
    status VARCHAR(10),
    trader VARCHAR(125),
    book VARCHAR(125),
    creationName VARCHAR(125),
    creationDate TIMESTAMP ,
    revisionName VARCHAR(125),
    revisionDate TIMESTAMP ,
    dealName VARCHAR(125),
    dealType VARCHAR(125),
    sourceListId VARCHAR(125),
    side VARCHAR(125),

    constraint pk_bidlist PRIMARY KEY (bidListId)
);

CREATE TABLE Trade (
    tradeId int NOT NULL AUTO_INCREMENT,
    account VARCHAR(30) NOT NULL,
    type VARCHAR(30) NOT NULL,
    buyQuantity DOUBLE,
    sellQuantity DOUBLE,
    buyPrice DOUBLE ,
    sellPrice DOUBLE,
    tradeDate TIMESTAMP,
    security VARCHAR(125),
    status VARCHAR(10),
    trader VARCHAR(125),
    benchmark VARCHAR(125),
    book VARCHAR(125),
    creationName VARCHAR(125),
    creationDate TIMESTAMP ,
    revisionName VARCHAR(125),
    revisionDate TIMESTAMP ,
    dealName VARCHAR(125),
    dealType VARCHAR(125),
    sourceListId VARCHAR(125),
    side VARCHAR(125),

    constraint pk_trade PRIMARY KEY (tradeId)
);

CREATE TABLE CurvePoint (
    id int NOT NULL AUTO_INCREMENT,
    curveId int,
    asOfDate TIMESTAMP,
    term DOUBLE ,
    value DOUBLE ,
    creationDate TIMESTAMP ,

    constraint pk_curvePoint PRIMARY KEY (id)
);

CREATE TABLE Rating (
    id int NOT NULL AUTO_INCREMENT,
    moodysRating VARCHAR(125),
    sandPRating VARCHAR(125),
    fitchRating VARCHAR(125),
    orderNumber int,

    constraint pk_rating PRIMARY KEY (id)
);

CREATE TABLE RuleName (
    id int NOT NULL AUTO_INCREMENT,
    name VARCHAR(125),
    description VARCHAR(125),
    json VARCHAR(125),
    template VARCHAR(512),
    sqlStr VARCHAR(125),
    sqlPart VARCHAR(125),

    constraint pk_rulename PRIMARY KEY (id)
);

CREATE TABLE User (
   id int NOT NULL AUTO_INCREMENT,              -- User ID
   username VARCHAR(125),                       -- Email used to authenticate the user
   password VARCHAR(125),                       -- Password used to authenticate the user
   fullname VARCHAR(125),                       -- Full name
   role VARCHAR(125),                           -- User role
   expired boolean,                             -- User account expired
   locked boolean,                              -- User locked
   credentialsExpired boolean,                  -- User credentials (password) expired
   enabled boolean,                             -- Activated user
   emailValidationKey varchar(36),              -- Email validation key
   validEmailEndDate datetime,                  -- Valid email end date

   constraint pk_users PRIMARY KEY (id),
   constraint uc_users_username UNIQUE KEY (username),
   constraint uc_users_emailValidationKey UNIQUE KEY (emailValidationKey)
);
