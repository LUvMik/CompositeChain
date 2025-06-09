package lt.esde.compositechain.converter.impl;

import lt.esde.compositechain.converter.Converter;
import lt.esde.compositechain.interpreter.ArithmeticEvaluator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArithmeticConverter implements Converter {
    private static final Logger logger = LogManager.getLogger(ArithmeticConverter.class);

    @Override
    public String tryConvert(String input) {
        if (!input.matches("[\\d+\\-*/().\\s]+")) return null;

        try {
            double result = ArithmeticEvaluator.evaluate(input);
            return String.valueOf(result);
        } catch (Exception e) {
            logger.warn("Failed to evaluate Arithmetic expression: '{}'", input, e);
            return null;
        }
    }
}