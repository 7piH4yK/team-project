package use_case.game;

import entity.*;

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

        // --- Removed collectItem(clicked, cur); ---

        // Game logic
        if (clicked instanceof NonPlayableCharacter) {
            gameDataAccessInterface.setCurrentDialogue(((NonPlayableCharacter) clicked).getDB());
        }
        else {
            switch (clicked.getName()) {
                case "Object1":
                    gameDataAccessInterface.setCurrentScene(
                            gameDataAccessInterface.getScenes().get("Scene1")
                    );
                    break;

                case "Object2":
                    gameDataAccessInterface.setCurrentScene(
                            gameDataAccessInterface.getScenes().get("Scene2")
                    );
                    break;

                case "Object3":
                    gameDataAccessInterface.setCurrentScene(
                            gameDataAccessInterface.getScenes().get("Scene3")
                    );
                    break;

                case "Door1":
                    Player player = gameDataAccessInterface.getPlayer();

                    // If already unlocked, just go to Scene4
                    if (gameDataAccessInterface.isDoorUnlocked("Door1")) {
                        Scene scene4 = gameDataAccessInterface.getScenes().get("Scene4");
                        if (scene4 != null) {
                            gameDataAccessInterface.setCurrentScene(scene4);
                        }
                    }
                    else {
                        if (player.hasItemNamed("Key1")) {
                            // consume key
                            player.removeItemNamed("Key1");

                            // unlock door permanently
                            gameDataAccessInterface.unlockDoor("Door1");

                            // move to Scene4
                            Scene scene4 = gameDataAccessInterface.getScenes().get("Scene4");
                            if (scene4 != null) {
                                gameDataAccessInterface.setCurrentScene(scene4);
                            }

                            javax.swing.SwingUtilities.invokeLater(() ->
                                    javax.swing.JOptionPane.showMessageDialog(
                                            null,
                                            "Door unlocked with Key1. It will stay open.",
                                            "Door Unlocked",
                                            javax.swing.JOptionPane.INFORMATION_MESSAGE
                                    )
                            );
                        }
                        else {
                            javax.swing.SwingUtilities.invokeLater(() ->
                                    javax.swing.JOptionPane.showMessageDialog(
                                            null,
                                            "It's locked. You need Key1.",
                                            "Locked",
                                            javax.swing.JOptionPane.WARNING_MESSAGE
                                    )
                            );
                        }
                    }
                    break;
            }
        }

        // Update game UI
        updateView();
    }

    // --- ENTIRE collectItem(...) DELETED ---

    @Override
    public void executeDialogueOption(DialogueOption dialogueOption) {
        if (dialogueOption.leadsToScene()) {
            gameDataAccessInterface.setCurrentDialogue(null);
            gameDataAccessInterface.setCurrentScene(dialogueOption.getTargetScene());
        }
        else if (dialogueOption.leadsToDialogue()) {
            gameDataAccessInterface.setCurrentDialogue(dialogueOption.getTargetDialogue());
        }

        updateView();
    }

    private void updateView() {
        Scene currentScene = gameDataAccessInterface.getCurrentScene();

        GameOutputData output = new GameOutputData();
        output.setBackgroundImage(currentScene.getImage());
        output.setClickableObjects(currentScene.getObjects());
        output.setCurrentDialogue(gameDataAccessInterface.getCurrentDialogue());
        output.setInventory(gameDataAccessInterface.getPlayer().getInventory());

        presenter.prepareView(output);
    }
}
