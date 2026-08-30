package com.changepilot.payment.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Currency;

public class ISO4217CurrencyValidator implements ConstraintValidator<ISO4217Currency, String> {

    @Override
    public void initialize(ISO4217Currency constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String currencyCode, ConstraintValidatorContext context) {
        if (currencyCode == null || currencyCode.isBlank()) {
            return false; // @NotBlank should handle this, but good for robustness
        }
        try {
            Currency.getInstance(currencyCode);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
