package lt.esde.compositechain.component.impl;

import lt.esde.compositechain.component.ComponentType;
import lt.esde.compositechain.component.TextElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompositeTextElement implements TextElement {
    private final ComponentType type;
    private final List<TextElement> children = new ArrayList<>();

    public CompositeTextElement(ComponentType type) {
        this.type = type;
    }

    @Override
    public void add(TextElement component) {
        children.add(component);
    }

    @Override
    public List<TextElement> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public String toText() {
        StringBuilder sb = new StringBuilder();
        for (TextElement child : children) {
            sb.append(child.toText());

            if (type == ComponentType.TEXT || type == ComponentType.PARAGRAPH) {
                sb.append("\n");
            } else if (type == ComponentType.SENTENCE) {
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        return toText();
    }
}
