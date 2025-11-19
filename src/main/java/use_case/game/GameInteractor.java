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

        // Handle collectable objects
        if (clicked.isCollectable()) {
            // 1. Add to inventory
            gameDataAccessInterface.getPlayer().addToInventory(clicked);

            // 2. Remove from scene (rebuild because Scene is immutable)
            java.util.List<ClickableObject> updated = new java.util.ArrayList<>(cur.getObjects());
            updated.removeIf(o -> o.getName().equals(clicked.getName()));
            Scene updatedScene = new Scene(cur.getName(), updated, cur.getImage());

            // 3. Save new scene state
            gameDataAccessInterface.getScenes().put(updatedScene.getName(), updatedScene);
            gameDataAccessInterface.setCurrentScene(updatedScene);

            // 4. Optional: show popup or in-game message
            javax.swing.SwingUtilities.invokeLater(() ->
                    javax.swing.JOptionPane.showMessageDialog(null,
                            "You collected " + clicked.getName() + "!",
                            "Item Collected",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE)
            );
        }

        // Game logic
        if (clicked instanceof NonPlayableCharacter) {
            // Open dialogue overlay instead of changing scene
            gameDataAccessInterface.setCurrentDialogue(((NonPlayableCharacter) clicked).getDB());
        }
        else {
            switch (clicked.getName()) {
                case "Object1":
                    gameDataAccessInterface.setCurrentScene(gameDataAccessInterface.getScenes().get("Scene1"));
                    break;
                case "Object2":
                    gameDataAccessInterface.setCurrentScene(gameDataAccessInterface.getScenes().get("Scene2"));
                    break;
                case "Object3":
                    gameDataAccessInterface.setCurrentScene(gameDataAccessInterface.getScenes().get("Scene3"));
                    break;
                case "Door1":
                    Player player = gameDataAccessInterface.getPlayer();

                    // If already unlocked, just go to Scene2
                    if (gameDataAccessInterface.isDoorUnlocked("Door1")) {
                        Scene scene2 = gameDataAccessInterface.getScenes().get("Scene4");
                        if (scene2 != null) {
                            gameDataAccessInterface.setCurrentScene(scene2);
                        }
                    } else {
                        // Not unlocked yet: require Object3 (the key)
                        if (player.hasItemNamed("Key1")) {
                            // consume the key
                            java.util.List<entity.ClickableObject> inv = player.getInventory();
                            for (ClickableObject clickableObject : inv) {
                                if (clickableObject.getName().equals("Object3")) {
                                    player.removeFromInventory(clickableObject);
                                    break;
                                }
                            }

                            // mark door as unlocked forever
                            gameDataAccessInterface.unlockDoor("Door1");

                            // go to Scene2
                            entity.Scene scene2 = gameDataAccessInterface.getScenes().get("Scene4");
                            if (scene2 != null) {
                                gameDataAccessInterface.setCurrentScene(scene2);
                            }

                            // optional UX feedback
                            javax.swing.SwingUtilities.invokeLater(() ->
                                    javax.swing.JOptionPane.showMessageDialog(
                                            null, "Door unlocked with Key1. It will stay open.",
                                            "Door Unlocked", javax.swing.JOptionPane.INFORMATION_MESSAGE));
                        } else {
                            // still locked
                            javax.swing.SwingUtilities.invokeLater(() ->
                                    javax.swing.JOptionPane.showMessageDialog(
                                            null, "It's locked. You need Key1.",
                                            "Locked", javax.swing.JOptionPane.WARNING_MESSAGE));
                        }
                    }
                    break;
            }
        }

        // Update game UI
        updateView();
    }

    @Override
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
        gameOutputData.setBackgroundImage(currentScene.getImage());
        gameOutputData.setClickableObjects(currentScene.getObjects());
        gameOutputData.setCurrentDialogue(gameDataAccessInterface.getCurrentDialogue());
        presenter.prepareView(gameOutputData);
    }
}
