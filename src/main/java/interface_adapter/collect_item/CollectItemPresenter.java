package interface_adapter.collect_item;

import interface_adapter.AppContext;
import interface_adapter.game.GameState;
import interface_adapter.game.GameViewModel;

import use_case.collect_item.CollectItemOutputBoundary;
import use_case.collect_item.CollectItemOutputData;

import javax.swing.*;

public class CollectItemPresenter implements CollectItemOutputBoundary {

    private final GameViewModel gameViewModel;

    public CollectItemPresenter(GameViewModel gameViewModel) {
        this.gameViewModel = gameViewModel;
    }


    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("[CollectItem] Failed: " + errorMessage);
    }
    @Override
    public void prepareSuccessView(CollectItemOutputData outputData) {
        GameState state = gameViewModel.getState();

        // Update background + clickables
        state.setBackgroundImage(outputData.getUpdatedScene().getImage());
        state.setClickableObjects(outputData.getUpdatedScene().getObjects());

        // Update inventory
        state.setInventoryItems(
                AppContext.getGameDAO().getPlayer().getInventory()
        );

        // 🔥 SHOW POPUP MESSAGE HERE
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(
                        null,
                        "You picked up " + outputData.getCollectedItemName() + "!",
                        "Item Collected",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        gameViewModel.firePropertyChange();
    }

}
