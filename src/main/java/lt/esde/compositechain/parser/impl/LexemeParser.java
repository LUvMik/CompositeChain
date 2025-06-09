package lt.esde.compositechain.parser.impl;

import lt.esde.compositechain.component.ComponentType;
import lt.esde.compositechain.component.TextElement;
import lt.esde.compositechain.component.impl.CompositeTextElement;
import lt.esde.compositechain.component.impl.LeafTextElement;
import lt.esde.compositechain.parser.TextHandler;
import lt.esde.compositechain.util.ConverterManager;

public class LexemeParser extends TextHandler {

    @Override
    public TextElement handle(String data) {
        CompositeTextElement lexeme = new CompositeTextElement(ComponentType.LEXEME);

        String converted = ConverterManager.tryConvert(data);
        for (char c : converted.toCharArray()) {
            lexeme.add(new LeafTextElement(c));
        }

        return lexeme;
    }
}
