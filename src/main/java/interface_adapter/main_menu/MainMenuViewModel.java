package interface_adapter.main_menu;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Main Menu View.
 */
public class MainMenuViewModel extends ViewModel<MainMenuState> {

    public static final String TITLE_LABEL = "Main Menu";
    public static final String START_GAME_BUTTON_LABEL = "Start Game";
    public static final String LAOD_GAME_BUTTON_LABEL = "Laod Game";
    public static final String EXIT_GAME_BUTTON_LABEL = "Exit Game";

    public MainMenuViewModel() {
        super("main menu");
        setState(new MainMenuState());
    }
}
