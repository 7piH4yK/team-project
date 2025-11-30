package use_case.dialogue;

import entity.DialogueBox;
import entity.Scene;

import java.util.Map;

public interface DialogueDataAccessInterface {

    /**
     * Get the current dialogue box (null if no dialogue is active).
     */
    DialogueBox getCurrentDialogue();

    /**
     * Set the current dialogue box (null to close dialogue).
     */
    void setCurrentDialogue(DialogueBox dialogue);

    /**
     * Returns the current scene.
     *
     * @return the current scene
     */
    Scene getCurrentScene();

    /**
     * Set current scene
     */
    void setCurrentScene(Scene scene);

    /**
     * Returns all scenes.
     */
    Map<String, Scene> getScenes();
}
