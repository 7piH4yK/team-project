package use_case.load;

import entity.ClickableObject;

import java.util.List;

public class LoadOutputData {
    private String backgroundImage;
    private List<ClickableObject> clickableObjects;

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public List<ClickableObject> getClickableObjects() {
        return clickableObjects;
    }

    public void setClickableObjects(List<ClickableObject> clickableObjects) {
        this.clickableObjects = clickableObjects;
    }
}
