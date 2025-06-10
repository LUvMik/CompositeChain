package lt.esde.compositechain;

import lt.esde.compositechain.component.TextElement;
import lt.esde.compositechain.parser.TextParserFacade;
import lt.esde.compositechain.service.TextElementService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextParserFacadeTest {

    @Test
    public void testParsingTextStructure() {
        String text = "    This is a sentence. Another sentence follows!";
        TextParserFacade facade = new TextParserFacade();
        TextElement parsed = facade.parse(text);
        assertNotNull(parsed);
        String restoredText = parsed.toText();
        assertTrue(restoredText.contains("This is a sentence."));
    }

    @Test
    public void testFindSentenceWithLongestWord() {
        String text = "    Short. This sentence has thelongestwordever!";
        TextParserFacade facade = new TextParserFacade();
        TextElement parsed = facade.parse(text);
        TextElement result = TextElementService.findSentenceWithLongestWord(parsed);

        String sentenceText = result.toText();
        assertTrue(sentenceText.contains("thelongestwordever"));
    }

    @Test
    public void testFindSentenceWithLongestWord_EmptyInput() {
        TextParserFacade facade = new TextParserFacade();
        TextElement parse = facade.parse("");

        TextElement result = TextElementService.findSentenceWithLongestWord(parse);
        assertNull(result);
    }
}
