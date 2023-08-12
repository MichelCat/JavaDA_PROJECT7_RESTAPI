insert into user(fullname, username, password, role
                 , expired, locked, credentials_expired, enabled, email_validation_key, valid_email_end_date)
values("Administrator", "admin@gmail.com", "$2a$10$55oEaTtOLOQZqJcu/ytTlO7bzdoo2tbRKNUsrJpU4W1wfLKw/opD.", "ADMIN"
      , false, false, false, true, 'cf0551e9-1c63-4b93-ae6e-bb3966c83e84'
      , str_to_date('29/07/2023', '%d/%m/%Y'));

insert into user(fullname, username, password, role
                 , expired, locked, credentials_expired, enabled, email_validation_key, valid_email_end_date)
values("User", "user@gmail.com", "$2a$10$55oEaTtOLOQZqJcu/ytTlO7bzdoo2tbRKNUsrJpU4W1wfLKw/opD.", "USER"
      , false, false, false, true, 'cf0551e9-1c63-4b93-ae6e-bb3966c83e83'
      , str_to_date('29/07/2023', '%d/%m/%Y'));

insert into user(fullname, username, password, role
                 , expired, locked, credentials_expired, enabled, email_validation_key, valid_email_end_date)
values("Alex", "alex@gmail.com", "$2a$10$55oEaTtOLOQZqJcu/ytTlO7bzdoo2tbRKNUsrJpU4W1wfLKw/opD.", "USER"
      , false, false, false, false, 'cf0551e9-1c63-4b93-ae6e-bb3966c83e85'
      , str_to_date('29/07/2100', '%d/%m/%Y'));
