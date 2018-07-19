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
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                builder.append(arg).append(',');
            }
            
            throw new IllegalArgumentException(String.format("Invalid arguments %s. Please input argument follow pattern: from={currency} to={currency}", builder.toString()));
        }
        return isValid;
    }
    
    private boolean validateArgument(String argument) {
        System.out.println("arg: " + argument);
        return PATTERN.matcher(argument).matches();
    }
}
