-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
-- Default values for testing functionality
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
insert into Users(fullname, username, password, role
        , expired, locked, credentialsExpired, enabled)
    values("Administrator", "admin@gmail.com", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "ADMIN"
        , false, false, false, true);
insert into Users(fullname, username, password, role
        , expired, locked, credentialsExpired, enabled)
    values("User", "user@gmail.com", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "USER"
        , false, false, false, true);
