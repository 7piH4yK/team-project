package app;

import java.awt.*;

import javax.swing.*;

import dataaccess.InMemoryGameDataAccessObject;
import entity.PlayerFactory;
import entity.SceneFactory;
import interface_adapter.AppContext;
import interface_adapter.ViewManagerModel;
import interface_adapter.game.GameViewModel;
import interface_adapter.load.LoadPresenter;
import interface_adapter.main_menu.MainMenuController;
import interface_adapter.main_menu.MainMenuPresenter;
import interface_adapter.main_menu.MainMenuViewModel;
import interface_adapter.save.SaveController;
import interface_adapter.save.SavePresenter;
import use_case.load.LoadInputBoundary;
import use_case.load.LoadInteractor;
import use_case.load.LoadOutputBoundary;
import use_case.save.SaveInputBoundary;
import use_case.save.SaveInteractor;
import use_case.save.SaveOutputBoundary;
import use_case.switch_to_game.SwitchToGameViewInputBoundary;
import use_case.switch_to_game.SwitchToGameViewInteractor;
import use_case.switch_to_game.SwitchToGameViewOutputBoundary;
import view.GameView;
import view.MainMenuView;
import view.ViewManager;
import interface_adapter.pause_menu.PauseController;
import view.PauseMenuView;
import use_case.resume.ResumeInputBoundary;
import use_case.resume.ResumeInteractor;
import use_case.resume.ResumeOutputBoundary;
import use_case.pause.PauseInputBoundary;
import use_case.pause.PauseInteractor;
import interface_adapter.pause_menu.PauseMenuPresenter;
import interface_adapter.pause_menu.PauseMenuViewModel;
import interface_adapter.pause_menu.ResumeController;
import interface_adapter.pause_menu.ResumePresenter;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // Create initial game data
    final SceneFactory sceneFactory = new SceneFactory();
    final PlayerFactory playerFactory = new PlayerFactory();
    final InMemoryGameDataAccessObject gameDataAccessObject = new InMemoryGameDataAccessObject();

    private MainMenuView mainMenuView;
    private MainMenuViewModel mainMenuViewModel;
    private GameViewModel gameViewModel;
    private GameView gameView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addMainMenuView() {
        mainMenuViewModel = new MainMenuViewModel();
        mainMenuView = new MainMenuView(mainMenuViewModel);
        cardPanel.add(mainMenuView, mainMenuView.getViewName());
        return this;
    }

    public AppBuilder addGameView() {
        gameViewModel = new GameViewModel();
        gameView = new GameView(gameViewModel);
        cardPanel.add(gameView, gameView.getViewName());
        return this;
    }

    public AppBuilder addSwitchToGameUseCase() {
        final SwitchToGameViewOutputBoundary switchOutputBoundary =
                new MainMenuPresenter(viewManagerModel, gameViewModel);
        final SwitchToGameViewInputBoundary switchInteractor =
                new SwitchToGameViewInteractor(gameDataAccessObject, switchOutputBoundary);

        final LoadOutputBoundary loadPresenter = new LoadPresenter(mainMenuViewModel, viewManagerModel, gameViewModel);
        final LoadInputBoundary loadInteractor = new LoadInteractor(gameDataAccessObject, loadPresenter);

        MainMenuController controller = new MainMenuController(switchInteractor, loadInteractor, gameDataAccessObject);
        mainMenuView.setMainMenuController(controller);
        return this;
    }

    public AppBuilder addClickButtonUseCase() {
        // 1) Build click rules for objects by name
        java.util.Map<String, use_case.game.ClickRule> rules = new java.util.HashMap<>();

        // Example mappings (adjust names to your real objects/scenes)
        rules.put("Object1", new use_case.game.ClickRule.Builder()
                .type(use_case.game.ClickActionType.CHANGE_SCENE)
                .targetScene("Scene1")
                .build());

        rules.put("Object2", new use_case.game.ClickRule.Builder()
                .type(use_case.game.ClickActionType.CHANGE_SCENE)
                .targetScene("Scene2")
                .build());

        // Collect and then change to Scene2, with a message
        rules.put("Object3", new use_case.game.ClickRule.Builder()
                .type(use_case.game.ClickActionType.COLLECT)             // only collect
                .message("You collected Object 3!")        // optional message
                .removeOnCollect(true)                     // remove from scene
                .build());

        // 2) Create GameManager
        use_case.game.GameManager manager = new use_case.game.GameManager(rules);

        // 3) Standard presenter & interactor, but pass manager
        final use_case.game.GameOutputBoundary gameOutputBoundary =
                new interface_adapter.game.GamePresenter(gameViewModel);
        final use_case.game.GameInputBoundary clickButtonInteractor =
                new use_case.game.GameInteractor(gameDataAccessObject, gameOutputBoundary);

        // 4) Controller wiring
        interface_adapter.game.GameController gameController =
                new interface_adapter.game.GameController(clickButtonInteractor);
        gameView.setGameController(gameController);
        return this;
    }

    public AppBuilder addSaveUseCase() {
        SaveOutputBoundary savePresenter = new SavePresenter(viewManagerModel, mainMenuViewModel);
        SaveInputBoundary saveInteractor = new SaveInteractor(gameDataAccessObject, savePresenter);
        SaveController saveController = new SaveController(saveInteractor);
        gameView.setSaveController(saveController);
        return this;
    }

    public AppBuilder addPauseMenu() {

        // ---- Pause setup ----
        PauseMenuViewModel pauseVM = new PauseMenuViewModel();
        PauseMenuPresenter pausePresenter =
                new PauseMenuPresenter(viewManagerModel, pauseVM);
        PauseInputBoundary pauseInteractor =
                new PauseInteractor(pausePresenter);
        PauseController pauseController =
                new PauseController(pauseInteractor);

        // ---- Resume setup ----
        ResumePresenter resumePresenter =
                new ResumePresenter(viewManagerModel);
        ResumeInputBoundary resumeInteractor =
                new ResumeInteractor(resumePresenter);
        ResumeController resumeController =
                new ResumeController(resumeInteractor);

        // ---- Create SaveController for Pause Menu ----
        SaveOutputBoundary savePresenter =
                new SavePresenter(viewManagerModel, mainMenuViewModel);
        SaveInputBoundary saveInteractor =
                new SaveInteractor(gameDataAccessObject, savePresenter);
        SaveController saveController =
                new SaveController(saveInteractor);

        // ---- Build Pause Menu view ----
        PauseMenuView pauseView =
                new PauseMenuView(resumeController, saveController);

        // ---- Register Pause Menu with CardLayout ----
        cardPanel.add(pauseView, "pause_menu");

        // ---- Inject pause controller into GameView ----
        gameView.setPauseController(pauseController);

        return this;
    }


    public JFrame build() {
        final JFrame application = new JFrame("Point and Click Game");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);
        application.setPreferredSize(new Dimension(800, 600));
        application.setResizable(false);

        viewManagerModel.setState(mainMenuView.getViewName());
        viewManagerModel.firePropertyChange();
        AppContext.setGameDAO(gameDataAccessObject);

        return application;
    }
    // In AppBuilder.java
    public InMemoryGameDataAccessObject getGameDataAccessObject() {
        return gameDataAccessObject;
    }

}
