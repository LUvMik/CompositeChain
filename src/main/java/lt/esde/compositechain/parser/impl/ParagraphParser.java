package lt.esde.compositechain.parser.impl;

import lt.esde.compositechain.component.ComponentType;
import lt.esde.compositechain.component.TextElement;
import lt.esde.compositechain.component.impl.CompositeTextElement;
import lt.esde.compositechain.parser.TextHandler;

public class ParagraphParser extends TextHandler {
    private static final String SENTENCE_DELIMITER = "(?<=[.!?…])\\s+";

    @Override
    public TextElement handle(String data) {
        CompositeTextElement paragraph = new CompositeTextElement(ComponentType.PARAGRAPH);
        String[] sentences = data.split(SENTENCE_DELIMITER);

        for (String sentence : sentences) {
            if (next != null) {
                paragraph.add(next.handle(sentence.trim()));
            }
        }

        return paragraph;
    }
}