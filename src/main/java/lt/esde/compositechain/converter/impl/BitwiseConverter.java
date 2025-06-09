package lt.esde.compositechain.converter.impl;

import lt.esde.compositechain.converter.Converter;
import lt.esde.compositechain.interpreter.BitwiseEvaluator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BitwiseConverter implements Converter {
    private static final Logger logger = LogManager.getLogger(BitwiseConverter.class);

    private static final String VALID_PATTERN = "^[\\d()~&|^<>]+$";
    private static final String NOISE_PATTERN = ".*(\\+\\+|--|\\?|toString).*";

    @Override
    public String tryConvert(String input) {
        String expr = input.replaceAll("\\s+", "");

        if (expr.matches(NOISE_PATTERN)) {
            return null;
        }

        if (!expr.matches(VALID_PATTERN)) {
            return null;
        }

        if (!isBalanced(expr)) {
            return null;
        }

        try {
            long result = BitwiseEvaluator.evaluate(expr);
            return String.valueOf(result);
        } catch (Exception e) {
            logger.warn("Failed to evaluate bitwise expression: '{}'", expr, e);
            return null;
        }
    }

    private boolean isBalanced(String expr) {
        int balance = 0;
        for (char c : expr.toCharArray()) {
            if (c == '(') balance++;
            if (c == ')') balance--;
            if (balance < 0) return false;
        }
        return balance == 0;
    }
}
