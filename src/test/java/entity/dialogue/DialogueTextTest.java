package entity.dialogue;

import entity.DialogueText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DialogueTextTest {

    @Test
    public void testGetText() {
        DialogueText text = new DialogueText("text", 1, 2);
        assertEquals("text", text.getText());
    }

    @Test
    public void testCoordinateX() {
        DialogueText text = new DialogueText("text", 1, 2);
        assertEquals(1, text.getCoordinateX());
    }

    @Test
    public void testCoordinateY() {
        DialogueText text = new DialogueText("text", 1, 2);
        assertEquals(2, text.getCoordinateY());
    }
}
