package use_case.load;

import entity.ClickableObject;

import java.util.List;

public class LoadOutputData {
    private String backgroundImage;
    private List<ClickableObject> collectibles;

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public List<ClickableObject> getClickableObjects() {
        return collectibles;
    }

    public void setClickableObjects(List<ClickableObject> collectibles) {
        this.collectibles = collectibles;
    }
}
