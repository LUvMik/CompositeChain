package lt.esde.compositechain.component;

import java.util.List;

public interface TextElement {
    void add(TextElement component);

    List<TextElement> getChildren();

    String toText();
}