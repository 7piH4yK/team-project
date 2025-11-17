package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a dialogue box that has dialogue options in it.
 */

public class DialogueBox extends Scene{


    private final List<ClickableObject> options;
    private final String image;

    public DialogueBox(List<ClickableObject> options, String image) {
        super("qq", new ArrayList<>(), "qq");
        this.options = new ArrayList<>(options);
        this.image = image;
    }


    @Override
    public List<ClickableObject> getObjects() {return options;}
    @Override
    public String getImage() {return image;}
    @Override
    public void addObject(ClickableObject object) {
        options.add(object);
    }
}
