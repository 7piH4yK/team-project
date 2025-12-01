package entity;

import interface_adapter.factories.NonPlayableCharacterFactoryInterface;

/**
 * Factory for creating NonPlayableCharacter objects.
 */
public class NonPlayableCharacterFactory implements NonPlayableCharacterFactoryInterface {

    public NonPlayableCharacter create(String name, int coordinateX, int coordinateY, String image, DialogueBox box) {
        return new NonPlayableCharacter(name, coordinateX, coordinateY, image, box);
    }
}