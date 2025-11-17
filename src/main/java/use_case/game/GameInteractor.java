package use_case.game;

import entity.ClickableObject;
import entity.DialogueOption;
import entity.NonPlayableCharacter;
import entity.Scene;
import use_case.switch_to_game.SwitchToGameOutputData;
import use_case.switch_to_game.SwitchToGameViewDataAccessInterface;

/**
 * The Click Button Interactor.
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

        // ✅ Handle collectable objects
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

        // ✅ Optional: handle non-collectable objects (scene switching, etc.)
        else if (clicked.getName().equals("Object1")) {
            gameDataAccessInterface.setCurrentScene(gameDataAccessInterface.getScenes().get("Scene1"));
        } else if (clicked.getName().equals("Object2")) {
            entity.Player player = gameDataAccessInterface.getPlayer();
            if (player == null) {
                player = new entity.Player();
                gameDataAccessInterface.setPlayer(player);
            }

            // If already unlocked, just go to Scene2
            if (gameDataAccessInterface.isDoorUnlocked("Object2")) {
                entity.Scene scene2 = gameDataAccessInterface.getScenes().get("Scene2");
                if (scene2 != null) {
                    gameDataAccessInterface.setCurrentScene(scene2);
                }
            } else {
                // Not unlocked yet: require Object3 (the key)
                if (player.hasItemNamed("Object3")) {
                    // consume the key
                    // (use your helper removeItemNamed from earlier)
                    // or do the manual loop if you didn't add the helper
                    java.util.List<entity.ClickableObject> inv = player.getInventory();
                    for (int i = 0; i < inv.size(); i++) {
                        if (inv.get(i).getName().equals("Object3")) {
                            player.removeFromInventory(inv.get(i));
                            break;
                        }
                    }

                    // mark door as unlocked forever
                    gameDataAccessInterface.unlockDoor("Object2");

                    // go to Scene2
                    entity.Scene scene2 = gameDataAccessInterface.getScenes().get("Scene2");
                    if (scene2 != null) {
                        gameDataAccessInterface.setCurrentScene(scene2);
                    }

                    // optional UX feedback
                    javax.swing.SwingUtilities.invokeLater(() ->
                            javax.swing.JOptionPane.showMessageDialog(
                                    null, "Door unlocked with Object3. It will stay open.",
                                    "Door Unlocked", javax.swing.JOptionPane.INFORMATION_MESSAGE));
                } else {
                    // still locked
                    javax.swing.SwingUtilities.invokeLater(() ->
                            javax.swing.JOptionPane.showMessageDialog(
                                    null, "It's locked. You need Object3.",
                                    "Locked", javax.swing.JOptionPane.WARNING_MESSAGE));
                }
            }
        }



        // ✅ Refresh UI
        // game logic
        if (clicked instanceof NonPlayableCharacter) {
            gameDataAccessInterface.setCurrentScene(((NonPlayableCharacter) clicked).getDB());
        }
        else if (clicked instanceof DialogueOption) {
            gameDataAccessInterface.setCurrentScene(((DialogueOption) clicked).getScene());
        }
        else {
            switch (clicked.getName()) {
                case "Object1":
                    gameDataAccessInterface.setCurrentScene(gameDataAccessInterface.getScenes().get("Scene1"));
                    break;
                case "Object2":
                    gameDataAccessInterface.setCurrentScene(gameDataAccessInterface.getScenes().get("Scene2"));
                    break;
            }
        }

        // update game ui
        Scene currentScene = gameDataAccessInterface.getCurrentScene();
        GameOutputData gameOutputData = new GameOutputData();
        gameOutputData.setBackgroundImage(currentScene.getImage());
        gameOutputData.setClickableObjects(currentScene.getObjects());
        presenter.prepareView(gameOutputData);

    }

}
