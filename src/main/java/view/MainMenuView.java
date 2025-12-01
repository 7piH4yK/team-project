package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interface_adapter.main_menu.MainMenuController;
import interface_adapter.main_menu.MainMenuViewModel;
import interface_adapter.question.QuestionController;

/**
 * The View for the Main Menu.
 */
public class MainMenuView extends JPanel implements ActionListener, PropertyChangeListener {

    // Constants (removed magic numbers)
    private static final int TITLE_FONT_SIZE = 48;
    private static final int ERROR_FONT_SIZE = 24;
    private static final int BUTTON_WIDTH = 600;
    private static final int BUTTON_HEIGHT = 60;
    private static final int GAP_HEIGHT = 20;
    private static final int TOP_SPACER = 40;
    private static final int BOTTOM_SPACER = 40;

    private static final String TEST_QUESTION_TEXT = "Test Trivia Question";

    private final String viewName = "main menu";
    private final MainMenuViewModel mainMenuViewModel;

    private final JButton startGameButton;
    private final JButton loadGameButton;
    private final JButton exitGameButton;
    private final JButton testQuestionButton;

    private final JLabel errorLabel;

    private MainMenuController mainMenuController;

    /**
     * Constructs the main menu view.
     *
     * @param mainMenuViewModel The view model for the main menu.
     */
    public MainMenuView(final MainMenuViewModel mainMenuViewModel) {
        this.mainMenuViewModel = mainMenuViewModel;
        this.mainMenuViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title = new JLabel(MainMenuViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, TITLE_FONT_SIZE));

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, ERROR_FONT_SIZE));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        Dimension buttonsSize = new Dimension(600, 60);

        startGameButton = new JButton(MainMenuViewModel.START_GAME_BUTTON_LABEL);
        startGameButton.setPreferredSize(buttonsSize);
        startGameButton.setMaximumSize(buttonsSize);
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(startGameButton);
        buttons.add(Box.createRigidArea(new Dimension(0, 20)));

        loadGameButton = new JButton(MainMenuViewModel.LOAD_GAME_BUTTON_LABEL);
        loadGameButton.setPreferredSize(buttonsSize);
        loadGameButton.setMaximumSize(buttonsSize);
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(loadGameButton);
        buttons.add(Box.createRigidArea(new Dimension(0, 20)));

        exitGameButton = new JButton(MainMenuViewModel.EXIT_GAME_BUTTON_LABEL);
        exitGameButton.setPreferredSize(buttonsSize);
        exitGameButton.setMaximumSize(buttonsSize);
        exitGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(exitGameButton);
        buttons.add(Box.createRigidArea(new Dimension(0, 20)));

        startGameButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(startGameButton)) {
                            mainMenuController.switchToGameView();
                        }
                    }
                }
        );

        loadGameButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(loadGameButton)) {
                            mainMenuController.loadGame();
                        }
                    }
                }
        );

        exitGameButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        System.exit(0);
                    }
                }
            }
        });

        exitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        // Not used
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getSource() == mainMenuViewModel) {
            final String error = mainMenuViewModel.getState().getErrorMessage();
            if (error == null) {
                errorLabel.setText("");
            }
            else {
                errorLabel.setText(error);
            }
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setMainMenuController(final MainMenuController controller) {
        this.mainMenuController = controller;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }
}
