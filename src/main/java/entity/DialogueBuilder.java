package entity;

import java.util.ArrayList;
import java.util.List;

public class DialogueBuilder {


    private int option_xpos = 50;
    private int option_ypos = 350;
    private int text_xpos = 400;
    private int text_ypos = 350;

    private final String image;

    public String text = "";
    public List<ClickableObject> options = new ArrayList<>();

    public DialogueBuilder(String image) {
        this.image = image;
    }

    public DialogueBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public DialogueBuilder addOption(String name, Scene nextScene) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("null or empty name");
        if (nextScene == null) throw new IllegalArgumentException("null nextScene");
        DialogueOption o = new DialogueOptionFactory().create(name, option_xpos, option_ypos, nextScene);
        this.options.add(o);
        option_ypos += 50;
        return this;
    }

    public DialogueBox build() {
        DialogueText dialogueText = new DialogueTextFactory().create(text, text_xpos, text_ypos);
        options.add(dialogueText);
        return new DialogueBox(options, image);
    }
}
