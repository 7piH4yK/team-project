package interface_adapter.game;

import use_case.game.GameOutputBoundary;
import use_case.game.GameOutputData;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.util.ArrayList;

/**
 * The Presenter for the Game View.
 */
public class GamePresenter implements GameOutputBoundary {

    private final GameViewModel gameViewModel;
    private final ViewManagerModel viewManagerModel;
    public GamePresenter(GameViewModel gameViewModel, ViewManagerModel viewManagerModel) {
        this.gameViewModel = gameViewModel;
        this.viewManagerModel = viewManagerModel;

    }

    @Override
    public void prepareView(GameOutputData outputData) {

        GameState state = new GameState();

        // Presenter should ONLY copy values from outputData
        state.setBackgroundImage(outputData.getBackgroundImage());
        state.setClickableObjects(outputData.getClickableObjects());
        state.setInventoryItems(
                outputData.getInventory() == null ? new ArrayList<>() : outputData.getInventory()
        );

        state.setSceneName(outputData.getSceneName());

        gameViewModel.setState(state);
        gameViewModel.firePropertyChange();
    }
    @Override
    public void prepareDoorUnlockedView(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Door Unlocked",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void prepareGameWonView() {
        JOptionPane.showMessageDialog(
                null,
                "🎉 Congratulations! You escaped the building! 🎉",
                "You Win!",
                JOptionPane.INFORMATION_MESSAGE
        );

        viewManagerModel.setState("main menu");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareDoorLockedView(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Locked Door",
                JOptionPane.WARNING_MESSAGE
        );
    }


}
