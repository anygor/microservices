package com.epam.productservice.validators;

import com.epam.productservice.models.Product;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "name.required");

        Product product = (Product) o;

        if( product.getGroup() == null ){
            errors.rejectValue("group", "group.required");
        }

        if( product.getUserId() == null ){
            errors.rejectValue("userId", "user.required");
        }

    }
}
