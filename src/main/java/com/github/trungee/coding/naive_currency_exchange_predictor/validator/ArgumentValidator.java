package com.github.trungee.coding.naive_currency_exchange_predictor.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ArgumentValidator {

    private static final Pattern PATTERN = Pattern.compile("\\w*=\\w{3}");
    
    public boolean validate(String... args) {
        boolean isValid = false;
        if (args.length >= 2 && validateArgument(args[0]) && validateArgument(args[1])) {
            isValid = true;
        }
        if (!isValid) {
            throw new IllegalArgumentException("Invalid arguments. Please input argument follow pattern: from={currency} to={currency}");
        }
        return isValid;
    }
    
    private boolean validateArgument(String argument) {
        return PATTERN.matcher(argument).matches();
    }
}
