package lt.esde.compositechain.parser.impl;

import lt.esde.compositechain.component.ComponentType;
import lt.esde.compositechain.component.TextElement;
import lt.esde.compositechain.component.impl.CompositeTextElement;
import lt.esde.compositechain.parser.TextHandler;

public class TextParser extends TextHandler {
    private static final String PARAGRAPH_DELIMITER = "(?m)(?=^\\s{4}|^\\t)"; // 4 пробела или таб

    @Override
    public TextElement handle(String data) {
        CompositeTextElement text = new CompositeTextElement(ComponentType.TEXT);
        String[] paragraphs = data.split(PARAGRAPH_DELIMITER);

        for (String paragraph : paragraphs) {
            if (next != null) {
                text.add(next.handle(paragraph.trim()));
            }
        }

        return text;
    }
}