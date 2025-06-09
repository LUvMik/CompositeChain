package lt.esde.compositechain.service;

import lt.esde.compositechain.component.TextElement;

public class TextElementService {
    public static TextElement findSentenceWithLongestWord(TextElement root) {
        if (root == null || root.getChildren() == null) return null;

        TextElement maxSentence = null;
        int maxLength = 0;

        for (TextElement paragraph : root.getChildren()) {
            for (TextElement sentence : paragraph.getChildren()) {
                for (TextElement lexeme : sentence.getChildren()) {
                    String word = lexeme.toText().replaceAll("[^a-zA-Zа-яА-ЯёЁ]", "");
                    if (word.length() > maxLength) {
                        maxLength = word.length();
                        maxSentence = sentence;
                    }
                }
            }
        }
        return maxSentence;
    }
}
