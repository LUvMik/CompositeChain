package lt.esde.compositechain.component.impl;

import lt.esde.compositechain.component.ComponentType;
import lt.esde.compositechain.component.TextElement;

import java.util.Collections;
import java.util.List;

public class LeafTextElement implements TextElement {
    private final char symbol;
    private final ComponentType type;

    public LeafTextElement(char symbol, ComponentType type) {
        this.symbol = symbol;
        this.type = type;
    }

    @Override
    public void add(TextElement component) {
        throw new UnsupportedOperationException("Leaf component doesn't support add().");
    }

    @Override
    public List<TextElement> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String toText() {
        return String.valueOf(symbol);
    }

    @Override
    public String toString() {
        return toText();
    }
}
