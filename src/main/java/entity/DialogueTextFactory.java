package entity;

import java.util.List;

public class DialogueTextFactory {
    public DialogueText create(String text, int coordinateX, int coordinateY) {
        return new DialogueText(text, coordinateX, coordinateY);
    }
}