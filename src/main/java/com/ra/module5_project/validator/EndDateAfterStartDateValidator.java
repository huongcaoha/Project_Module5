//package com.ra.module5_project.validator;
//
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//import java.util.Date;
//
//public class EndDateAfterStartDateValidator implements ConstraintValidator<EndDateAfterStartDate, CouponRequest> {
//    @Override
//    public boolean isValid(CouponRequest couponRequest, ConstraintValidatorContext constraintValidatorContext) {
//        if (couponRequest == null) {
//            return true;
//        }
//        Date startDate = couponRequest.getStart_date();
//        Date endDate = couponRequest.getEnd_date();
//        return endDate != null && startDate != null && endDate.after(startDate);
//    }
//}
