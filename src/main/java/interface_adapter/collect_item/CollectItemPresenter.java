package interface_adapter.collect_item;

import java.util.List;

import javax.swing.JOptionPane;

import entity.Collectibles;
import interface_adapter.ViewManagerModel;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;
import use_case.collect_item.CollectItemOutputBoundary;
import use_case.collect_item.CollectItemOutputData;

public class CollectItemPresenter implements CollectItemOutputBoundary {

    private final GameViewModel gameViewModel;
    private final ViewManagerModel viewManagerModel;

    public CollectItemPresenter(GameViewModel gameViewModel,
                                ViewManagerModel viewManagerModel) {
        this.gameViewModel = gameViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(CollectItemOutputData outputData) {

        final GameState state = new GameState();

        // Scene update
        state.setSceneName(outputData.getUpdatedScene().getName());
        state.setBackgroundImage(outputData.getUpdatedScene().getImage());
        state.setClickableObjects(outputData.getUpdatedScene().getObjects());

        // ⭐ THE IMPORTANT FIX ⭐
        // Use the NEW updated inventory from the Player (DAO),
        // not the old GameState!
        final List<Collectibles> newInventory =
                outputData.getUpdatedInventory();

        state.setInventoryItems(newInventory);

        gameViewModel.setState(state);
        gameViewModel.firePropertyChange();

        JOptionPane.showMessageDialog(
                null,
                "You collected " + outputData.getCollectedItemName() + "!",
                "Item Collected",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void prepareFailView(String errorMessage) {
        JOptionPane.showMessageDialog(
                null,
                errorMessage,
                "Cannot Collect Item",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
