package entity;

import javafx.scene.image.ImageView;

/**
 * A clickable that triggers a scene change (or any navigation action) when clicked.
 * The concrete scene-change behavior is provided via the Runnable.
 */
public class SceneChangeObject implements ClickableObject {
    private final ImageView imageView;
    private final Runnable sceneChangeAction;
    private boolean active = true;

    public SceneChangeObject(ImageView imageView, Runnable sceneChangeAction) {
        this.imageView = imageView;
        this.sceneChangeAction = sceneChangeAction;

        // Wire click
        this.imageView.setOnMouseClicked(e -> {
            if (active) onClick();
        });
    }

    @Override
    public void onClick() {
        if (sceneChangeAction != null) {
            sceneChangeAction.run();
        }
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void deactivate() {
        active = false;
        imageView.setVisible(false);
        imageView.setMouseTransparent(true);
    }
}
