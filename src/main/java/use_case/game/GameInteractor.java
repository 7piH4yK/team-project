package use_case.game;

import entity.*;
import use_case.dialogue.DialogueOutputData;

/**
 * The Game Interactor.
 */
public class GameInteractor implements GameInputBoundary {
    private final GameOutputBoundary presenter;
    private final GameDataAccessInterface gameDataAccessInterface;

    public GameInteractor(GameDataAccessInterface gameDataAccessInterface, GameOutputBoundary gameOutputBoundary) {
        this.presenter = gameOutputBoundary;
        this.gameDataAccessInterface = gameDataAccessInterface;
    }


    @Override
    public void execute(GameInputData gameInputData) {
        ClickableObject clicked = gameInputData.getClickableObject();

        Scene cur = gameDataAccessInterface.getCurrentScene();

        // Game logic
        if (clicked instanceof NonPlayableCharacter) {
            gameDataAccessInterface.setCurrentDialogue(((NonPlayableCharacter) clicked).getDB());
        } else {
            switch (clicked.getName()) {
                case "Go Exit":
                    gameDataAccessInterface.setCurrentScene(gameDataAccessInterface.getScenes().get("Scene Exit"));
                    break;

                case "Go Table":
                case "Go Table From Classroom":
                    gameDataAccessInterface.setCurrentScene(gameDataAccessInterface.getScenes().get("Scene Table"));
                    break;

                case "Go Stairs From Exit":
                case "Go Stairs From Table":
                    gameDataAccessInterface.setCurrentScene(gameDataAccessInterface.getScenes().get("Scene Stairs"));
                    break;

                case "Object3":
                    gameDataAccessInterface.setCurrentScene(gameDataAccessInterface.getScenes().get("Scene3"));
                    break;

                case "Door Classroom":
                    attemptUseDoor("Door Classroom", "Key Classroom", "Scene Classroom");
                    break;

                // ⭐⭐⭐ NEW: Door Exit unlock logic ⭐⭐⭐
                case "Door Exit":
                    attemptUseDoor("Door Exit", "Key Exit", "Scene Exit");
                    break;
            }
        }

        // Update game UI
        updateView();
    }


    private void attemptUseDoor(String doorName, String keyName, String newScene) {
        Player player = gameDataAccessInterface.getPlayer();

        // If already unlocked, just go to the new scene
        if (gameDataAccessInterface.isDoorUnlocked(doorName)) {
            Scene target = gameDataAccessInterface.getScenes().get(newScene);
            if (target != null) {
                gameDataAccessInterface.setCurrentScene(target);
            }
        } else {
            // Door is locked
            if (player.hasItemNamed(keyName)) {

                // Consume the key
                player.removeItemNamed(keyName);

                // Mark door unlocked forever
                gameDataAccessInterface.unlockDoor(doorName);

                // Move to scene
                Scene target = gameDataAccessInterface.getScenes().get(newScene);
                if (target != null) {
                    gameDataAccessInterface.setCurrentScene(target);
                }

                // Popup
                javax.swing.SwingUtilities.invokeLater(() ->
                        javax.swing.JOptionPane.showMessageDialog(
                                null,
                                "Door unlocked with " + keyName + ". It will stay open.",
                                "Door Unlocked",
                                javax.swing.JOptionPane.INFORMATION_MESSAGE));
            } else {

                // Player doesn’t have the key
                javax.swing.SwingUtilities.invokeLater(() ->
                        javax.swing.JOptionPane.showMessageDialog(
                                null,
                                "It's locked. You need " + keyName + ".",
                                "Locked",
                                javax.swing.JOptionPane.WARNING_MESSAGE));
            }
        }
    }


    public void executeDialogueOption(DialogueOption dialogueOption) {
        if (dialogueOption.leadsToScene()) {
            // Close dialogue and navigate to scene
            gameDataAccessInterface.setCurrentDialogue(null);
            gameDataAccessInterface.setCurrentScene(dialogueOption.getTargetScene());
        } else if (dialogueOption.leadsToDialogue()) {
            // Show next dialogue
            gameDataAccessInterface.setCurrentDialogue(dialogueOption.getTargetDialogue());
        }

        // Update game UI
        updateView();
    }

    private void updateView() {
        Scene currentScene = gameDataAccessInterface.getCurrentScene();
        GameOutputData gameOutputData = new GameOutputData();
        DialogueOutputData dialogueOutputData = new DialogueOutputData();

        gameOutputData.setSceneName(currentScene.getName());
        gameOutputData.setBackgroundImage(currentScene.getImage());
        gameOutputData.setClickableObjects(currentScene.getObjects());
        dialogueOutputData.setCurrentDialogue(gameDataAccessInterface.getCurrentDialogue());
        gameOutputData.setInventory(gameDataAccessInterface.getPlayer().getInventory());

        presenter.prepareView(gameOutputData);
    }
}
