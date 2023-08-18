//package com.nnk.poseidon.validator;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class PasswordValidationBusiness {
//
////    @Autowired
////    private PasswordValidationBusiness passwordValidationBusiness;
//
////        String err = passwordValidationBusiness.validatePassword(register.getPassword());
////        if (!err.isEmpty()) {
////            ObjectError error = new ObjectError("password", err);
////            result.addError(error);
////        }
//
//
//    // The password (at least one capital letter, at least one number, at least one symbol, at least 8 characters)
//    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$";
//
//    public String validatePassword(String password) {
//        String message = "";
//        if (password == null
//                || password.matches(PASSWORD_PATTERN) == false) {
//            message = "The password must contain (at least one capital letter, at least 8 characters, at least one number and one symbol)";
//        }
//        return message;
//    }
//}
