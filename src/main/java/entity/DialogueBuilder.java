package entity;

import java.util.ArrayList;
import java.util.List;

public class DialogueBuilder {
    private final int option_xpos = 50;
    private final int text_xpos = 380;
    private final int text_ypos = 350;
    private final String image;
    public String text = "";
    public List<DialogueOption> options = new ArrayList<>();
    private int option_ypos = 350;

    public DialogueBuilder(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public DialogueBuilder setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Adds an option that leads to a scene (closes dialogue).
     */
    public DialogueBuilder addOption(String name, Scene nextScene) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("null or empty name");
        this.options.add(new DialogueOptionFactory().createWithScene(name, option_xpos, option_ypos, nextScene));
        option_ypos += 50;

        return this;
    }

    /**
     * Adds an option that leads to another dialogue.
     */
    public DialogueBuilder addOption(String name, DialogueBox nextDialogue) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("null or empty name");
        this.options.add(new DialogueOptionFactory().createWithDialogue(name, option_xpos, option_ypos, nextDialogue));
        option_ypos += 50;
        return this;
    }

    public DialogueBox build() {
        return new DialogueBox(new DialogueTextFactory().create(text, text_xpos, text_ypos), options, image);
    }
}
