package use_case.dialogue;

import entity.DialogueBox;
import entity.DialogueText;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class DialogueOutputDataTest {
    @Test
    public void testGetCurrentDialogue(){
        DialogueBox NextBox = new DialogueBox(new DialogueText("NextBox",1,2), new ArrayList<>(),"bag.png");

        DialogueOutputData DialogueOutputData = new DialogueOutputData();
        assertNotSame(NextBox, DialogueOutputData.getCurrentDialogue());

        DialogueOutputData.setCurrentDialogue(NextBox);
        assertSame(NextBox, DialogueOutputData.getCurrentDialogue());
    }
}
