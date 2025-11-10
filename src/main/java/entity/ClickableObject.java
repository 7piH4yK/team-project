package entity;

import javafx.scene.image.ImageView;

/**
 * Base contract for all clickable game objects.
 */
public interface ClickableObject {

    /** Called when the player clicks this object. */
    void onClick();

    /** The visual node for this object (so caller can add it to the scene graph). */
    ImageView getImageView();

    /** Whether this object still responds to clicks. */
    boolean isActive();

    /** Deactivate this object (e.g., after being collected). */
    void deactivate();
}
