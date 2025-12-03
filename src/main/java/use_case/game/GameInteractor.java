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

        Scene target = gameDataAccessInterface.getScenes().get(newScene);

        // ⭐ If the scene does not exist → do nothing (unit test expects this)
        if (target == null) {
            return;
        }

        // Already unlocked
        if (gameDataAccessInterface.isDoorUnlocked(doorName)) {
            gameDataAccessInterface.setCurrentScene(target);

            if (isExitDoor) {
                presenter.prepareGameWonView();
            }
            return;
        }

        // Has key
        if (player.hasItemNamed(keyName)) {
            player.removeItemNamed(keyName);
            gameDataAccessInterface.unlockDoor(doorName);
            gameDataAccessInterface.setCurrentScene(target);

            presenter.prepareDoorUnlockedView("Door unlocked with " + keyName + "!");

            if (isExitDoor) {
                presenter.prepareGameWonView();
            }
            return;
        }

        // No key
        presenter.prepareDoorLockedView("It's locked. You need " + keyName + ".");
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
