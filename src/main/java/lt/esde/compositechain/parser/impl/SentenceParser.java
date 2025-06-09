package lt.esde.compositechain.parser.impl;

import lt.esde.compositechain.component.ComponentType;
import lt.esde.compositechain.component.TextElement;
import lt.esde.compositechain.component.impl.CompositeTextElement;
import lt.esde.compositechain.parser.TextHandler;

public class SentenceParser extends TextHandler {
    private static final String LEXEME_DELIMITER = "\\s+";

    @Override
    public TextElement handle(String data) {
        CompositeTextElement sentence = new CompositeTextElement(ComponentType.SENTENCE);
        String[] lexemes = data.split(LEXEME_DELIMITER);

        for (String lexeme : lexemes) {
            if (next != null) {
                sentence.add(next.handle(lexeme));
            }
        }

        return sentence;
    }
}
