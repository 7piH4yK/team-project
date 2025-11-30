package app.factories;

import data_access.InMemoryGameDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.collect_item.CollectItemController;
import interface_adapter.collect_item.CollectItemPresenter;
import interface_adapter.game.GameViewModel;
import use_case.collect_item.CollectItemInputBoundary;
import use_case.collect_item.CollectItemInteractor;
import use_case.collect_item.CollectItemOutputBoundary;

public class CollectItemUseCaseFactory {

    public static CollectItemController create(
            InMemoryGameDataAccessObject dao,
            GameViewModel gameViewModel,
            ViewManagerModel viewManagerModel
    ) {

        CollectItemOutputBoundary presenter =
                new CollectItemPresenter(gameViewModel, viewManagerModel);

        CollectItemInputBoundary interactor =
                new CollectItemInteractor(dao, presenter);

        return new CollectItemController(interactor);
    }
}
