package lt.esde.compositechain.util;

import lt.esde.compositechain.converter.impl.ArithmeticConverter;
import lt.esde.compositechain.converter.impl.BitwiseConverter;
import lt.esde.compositechain.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class ConverterManager {

    private static final List<Converter> converters = new ArrayList<>();

    static {
        converters.add(new ArithmeticConverter());
        converters.add(new BitwiseConverter());
//        converters.add(new NoisyConverter());
    }

    public static String tryConvert(String input) {
        for (Converter converter : converters) {
            String result = converter.tryConvert(input);
            if (result != null) {
                return result;
            }
        }
        return input;
    }
}
