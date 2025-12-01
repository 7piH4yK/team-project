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
    private QuestionController questionController;

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

        final Dimension buttonSize = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);

        startGameButton = createButton(MainMenuViewModel.START_GAME_BUTTON_LABEL, buttonSize);
        loadGameButton = createButton(MainMenuViewModel.LOAD_GAME_BUTTON_LABEL, buttonSize);
        testQuestionButton = createButton(TEST_QUESTION_TEXT, buttonSize);
        exitGameButton = createButton(MainMenuViewModel.EXIT_GAME_BUTTON_LABEL, buttonSize);

        add(title);
        add(Box.createVerticalStrut(TOP_SPACER));
        add(errorLabel);
        add(Box.createVerticalStrut(BOTTOM_SPACER));
        add(buttons);

        addButtonToPanel(buttons, startGameButton);
        addButtonToPanel(buttons, loadGameButton);
        addButtonToPanel(buttons, testQuestionButton);
        addButtonToPanel(buttons, exitGameButton);

        attachListeners();
    }

    private JButton createButton(final String label, final Dimension size) {
        final JButton button = new JButton(label);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private void addButtonToPanel(final JPanel panel, final JButton button) {
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, GAP_HEIGHT)));
    }

    private void attachListeners() {

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                mainMenuController.switchToGameView();
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                mainMenuController.loadGame();
            }
        });

        testQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                if (questionController != null) {
                    questionController.loadQuestions();
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

    public void setQuestionController(final QuestionController questionController) {
        this.questionController = questionController;
    }
}
