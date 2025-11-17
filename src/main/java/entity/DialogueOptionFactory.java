package entity;

import java.util.List;

public class DialogueOptionFactory {
    public DialogueOption create(String name, int coordinateX, int coordinateY,Scene scene) {
        return new DialogueOption(name, coordinateX, coordinateY, scene);
    }
}