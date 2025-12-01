package interface_adapter.factories;

import entity.DialogueBox;
import entity.NonPlayableCharacter;

/**
 * Factory interface for creating NonPlayableCharacter objects.
 */
public interface NonPlayableCharacterFactoryInterface {

    /**
     * Creates a new NonPlayableCharacter instance.
     *
     * @param name        the name or identifier of the NPC
     * @param coordinateX the X-coordinate of the NPC within the scene
     * @param coordinateY the Y-coordinate of the NPC within the scene
     * @param image       the path to the NPC's image asset
     * @param box         the DialogueBox associated with this NPC
     * @return a new NonPlayableCharacter object
     */
    NonPlayableCharacter create(String name, int coordinateX, int coordinateY, String image, DialogueBox box);
}
