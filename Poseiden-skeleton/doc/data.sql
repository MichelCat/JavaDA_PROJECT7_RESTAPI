-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
-- Setting up PoseidonProd DB
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------

drop database if exists PoseidonProd;

create database PoseidonProd;
use PoseidonProd;


-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
-- Default values for testing functionality
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
insert into User(fullname, username, password, role)
    values("Administrator", "admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "ADMIN");
insert into User(fullname, username, password, role)
    values("User", "user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "USER");
