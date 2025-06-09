package lt.esde.compositechain;

import lt.esde.compositechain.component.TextElement;
import lt.esde.compositechain.parser.TextParserFacade;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("src/main/resources/bit.txt"));
        TextParserFacade textParserFacade = new TextParserFacade();
        TextElement parse = textParserFacade.parse(text);
        System.out.println(parse.toText());
    }
}