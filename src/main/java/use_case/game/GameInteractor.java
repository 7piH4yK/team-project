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
        boolean isExitDoor = "Door Exit".equals(doorName);

        // If already unlocked → just enter
        if (gameDataAccessInterface.isDoorUnlocked(doorName)) {
            gameDataAccessInterface.setCurrentScene(
                    gameDataAccessInterface.getScenes().get(newScene)
            );

            if (isExitDoor) {
                presenter.prepareGameWonView();
            }

            return;
        }

        // Door is locked → but player has the key
        if (player.hasItemNamed(keyName)) {

            player.removeItemNamed(keyName);          // consume key
            gameDataAccessInterface.unlockDoor(doorName);

            gameDataAccessInterface.setCurrentScene(
                    gameDataAccessInterface.getScenes().get(newScene)
            );

            // Show normal unlocked popup
            presenter.prepareDoorUnlockedView("Door unlocked with " + keyName + "!");

            // After entering the exit scene
            if (isExitDoor) {
                presenter.prepareGameWonView();
            }


            return;
        }

        // Locked and no key
        presenter.prepareDoorUnlockedView("It's locked. You need " + keyName + ".");
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
